package it.volta.ts.ulivisamuel.nogpt_server.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import it.volta.ts.ulivisamuel.nogpt_server.Config;
import it.volta.ts.ulivisamuel.nogpt_server.bean.Client;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerEvent;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerOutputListener;
import it.volta.ts.ulivisamuel.nogpt_server.protocol_commands.ProtocolCommands;

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
		client                    = new Client("User" + config.getnUser(), cso);
		config.addClient(client);
		gestoreListaUtenti.ordinaListaUtenti();
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
			out = new PrintWriter(cso.getOutputStream(),true);
			in  = new BufferedReader(new InputStreamReader(cso.getInputStream()));
			serverOutputListener.mostraStringa(new ServerEvent("\nCanali di input e output creati, pronto a ricevere da " + client.getNomeUtente() + "..."));
			String s = "";
			while(!s.equals(ProtocolCommands.EXIT.toString())) {
				s = in.readLine();
				decidiAzione(s);
			}
			serverOutputListener.mostraStringa(new ServerEvent("\nCollegamento interrotto con " + client.getNomeUtente()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decidiAzione(String s)
	{
		String[] commands = s.split(" ");
		ProtocolCommands protocolCommand = ProtocolCommands.valueOf(commands[0]);
		switch (protocolCommand) {
		case LOGIN:
			gestisciLogin(commands);
			break;
			
		case SEND:
			serverOutputListener.mostraStringa(new ServerEvent("\n" + client.getNomeUtente() + ": " + commands[1]));
			break;

		default:
			out.println(ProtocolCommands.ERROR.toString());
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciLogin(String[] commands)
	{
		if(commands.length == 2)
		{
			serverOutputListener.mostraStringa(new ServerEvent("\n" + client.getNomeUtente() + " si logga con il nome " + commands[1]));
			System.out.println(gestoreListaUtenti.verificaDisponibilitàNome(commands[1]));
			client.setNomeUtente(commands[1]);
		}
		else
			out.println(ProtocolCommands.ERROR.toString());
	}
}
