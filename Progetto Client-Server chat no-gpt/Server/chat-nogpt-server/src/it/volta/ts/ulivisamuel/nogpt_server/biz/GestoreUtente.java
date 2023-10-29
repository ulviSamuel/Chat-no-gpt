package it.volta.ts.ulivisamuel.nogpt_server.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import it.volta.ts.ulivisamuel.nogpt_server.Config;
import it.volta.ts.ulivisamuel.nogpt_server.bean.Client;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerEvent;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerOutputListener;
import it.volta.ts.ulivisamuel.nogpt_server.protocol_commands.ClientProtocolCommands;
import it.volta.ts.ulivisamuel.nogpt_server.protocol_commands.ServerProtocolCommands;

public class GestoreUtente extends Thread
{
	private Socket                cso;
	private PrintWriter           out;
	private	BufferedReader        in;
	private ServerOutputListener  serverOutputListener;
	private Client                client;
	private Config                config;
	private GestoreListaUtenti    gestoreListaUtenti;
	
	//---------------------------------------------------------------------------------------------
	
	public GestoreUtente(ServerOutputListener serverOutputListener, Socket cso)
	{
		this.serverOutputListener = serverOutputListener;
		this.cso                  = cso;
		gestoreListaUtenti        = new GestoreListaUtenti();
		out                       = null;
		in                        = null;
		config                    = Config.instance();
		client                    = null;
		client                    = new Client("User" + config.getnUser(), cso);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void run()
	{
		gestisciInputOutput();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciInputOutput()
	{
		try {
			serverOutputListener.mostraStringa(new ServerEvent("\nRichiesta di connessione ricevuta da " + client.getNomeUtente()));
			out    = new PrintWriter(cso.getOutputStream(),true);
			client.setOut(out);
			in     = new BufferedReader(new InputStreamReader(cso.getInputStream()));
			config.addClient(client);
			serverOutputListener.mostraStringa(new ServerEvent("\nCanali di input e output creati, pronto a ricevere da " + client.getNomeUtente() + "..."));
			String s = "";
			while(!s.equals(ClientProtocolCommands.EXIT.toString())) {
				s = in.readLine();
				decidiAzione(s);
			}
			serverOutputListener.mostraStringa(new ServerEvent("\nCollegamento interrotto con " + client.getNomeUtente()));
		} catch (SocketException e) {
		    if (e.getMessage().equals("Connection reset")) 
		    {
		    	gestoreListaUtenti.rimuoviClient(client);
		    	serverOutputListener.mostraErrore(new ServerEvent("\n" + client.getNomeUtente() + " si è sloggato"));
		    	mandaListaUtentiConn();
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decidiAzione(String s)
	{
		ClientProtocolCommands protocolCommand;
		String                 requestParts    = null;
		String                 command;
		if(s.length() >= 5)
		{
			command         = s.substring(0, 4);
			requestParts    = s.substring(5, s.length());
			protocolCommand = ClientProtocolCommands.valueOf(command);
		}
		else
		{
			if(!s.equals(ClientProtocolCommands.LIST.toString()))
			{
				out.println(ServerProtocolCommands.COME.toString());
				return;
			}
			else
			{
				command         = s.substring(0, 4);
				protocolCommand = ClientProtocolCommands.valueOf(command);
			}
		}
		switch (protocolCommand) {
		case LOGI:
			gestisciLogin(requestParts);
			break;
			
		case SEND:
			gestisciInvio(requestParts);
			break;
			
		case LIST:
			mandaListaUtentiConn();
			break;

		default:
			out.println(ServerProtocolCommands.COME.toString());
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciLogin(String userName)
	{
		serverOutputListener.mostraStringa(new ServerEvent("\n" + client.getNomeUtente() + " vuole loggarsi con il nome " + userName));
		boolean res = gestoreListaUtenti.accettazioneNome(userName);
		if(res)
		{
			serverOutputListener.mostraStringa(new ServerEvent("\nNome utente non registrato, " + client.getNomeUtente() + " si logga con il nome " + userName));
			client.setNomeUtente(userName);
			out.println(ServerProtocolCommands.LOGG.toString());
			mandaListaUtentiConn();
		}
		else
		{
			serverOutputListener.mostraStringa(new ServerEvent("\nNome utente già registrato/non accettato in quanto non risponde ai criteri per il nome utente, chiudo la connessione con un errore di login..."));
			gestoreListaUtenti.rimuoviClient(client);
			out.println(ServerProtocolCommands.LOGE.toString());
			try {cso.close();} catch (IOException e) {}
			Thread.interrupted();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciInvio(String requestParts)
	{
		if(requestParts != "")
		{
			String                 message         = "";
			String                 command         = requestParts.substring(requestParts.length() - 4, requestParts.length());
			ClientProtocolCommands protocolCommand = ClientProtocolCommands.valueOf(command);
			if(protocolCommand == ClientProtocolCommands.BROA)
			{
				message = requestParts.substring(0, requestParts.length() - 5);
				mandaMessBroadcast(message, client);
			}
			else
			{
				if(protocolCommand == ClientProtocolCommands.EETO)
				{
					String[] parti = requestParts.split(ClientProtocolCommands.TOEE.toString());
					if(parti.length > 1)
					{
						String people;
						message = parti[0].substring(0, parti[0].length() - 1);
						people  = parti[1].substring(1, parti[1].length() - 5);
						mandaMessNarrowcast(people, message, client);
					}
				}
			}
		}
		else
			out.println(ServerProtocolCommands.COME.toString());
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void mandaMessBroadcast(String message, Client mittente)
	{
		List<Client> clients = config.getClients();
		for(Client client : clients)
		{
			if(client != mittente)
				client.getOut().println(ServerProtocolCommands.RECE.toString() + " " + message + " " + ServerProtocolCommands.FROM.toString() + " " + mittente.getNomeUtente() + " " + ServerProtocolCommands.FROM.toString() + " ");
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void mandaMessNarrowcast(String people, String message, Client mittente)
	{
		String[] peopleSep = people.split(" ");
		for(int idx = 0; idx < peopleSep.length; ++idx)
		{
			Client client = gestoreListaUtenti.trovaPosizioneNomeUtente(peopleSep[idx]);
			if(client != null)
				client.getOut().println(ServerProtocolCommands.RECE.toString() + " " + message + " " + ServerProtocolCommands.FROM.toString() + " " + mittente.getNomeUtente() + " " + ServerProtocolCommands.FROM.toString() + " ");
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void mandaListaUtentiConn()
	{
		String utenti        = gestoreListaUtenti.getStringListaUtenti();
		List<Client> clients = config.getClients();
		for(Client client : clients)
			client.getOut().println(ServerProtocolCommands.LIST + " " + utenti);
	}
}
