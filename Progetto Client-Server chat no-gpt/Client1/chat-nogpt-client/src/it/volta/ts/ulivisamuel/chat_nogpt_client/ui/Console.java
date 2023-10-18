package it.volta.ts.ulivisamuel.chat_nogpt_client.ui;

import java.util.Scanner;

import it.volta.ts.ulivisamuel.chat_nogpt_client.business.BizClient;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.util.Util;

public class Console implements ConsoleInputListener, ConsoleOutputListener
{
	private BizClient bizClient;
	private Scanner   scanner;
	
	//---------------------------------------------------------------------------------------------
	
	public Console()
	{
		bizClient = new BizClient(this, this);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void esegui()
	{
		scanner = new Scanner(System.in);
		insDatiSocket();
		scanner.close();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void insDatiSocket()
	{
		boolean continua = true;
		while(continua)
		{
			String nomeUtente = Util.leggiString(scanner, "\nInserisci il tuo nome utente\nIl nome utente "
					                                    + "deve essere lungo massimo 20 caratteri, lungo minimo"
					                                    + " 4 caratteri e non deve contenere spazi (es. Mario543)\n"
					                                    + "Oppure premi invio per uscire dal programma"
					                                    , false, null);
			String[] nomeUtenteCut = nomeUtente.split(" ");
			if(nomeUtente != null)
			{
				if(nomeUtenteCut.length == 1)
				{
					bizClient.connettiServer(nomeUtente);
					continua = false;
				}
				else
					System.out.println("\n");
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------

	@Override
	public ClientEvent leggiStringa() 
	{
		return new ClientEvent(Util.leggiString(scanner, "\nMessaggio da inviare", true, null));
	}
	
	//---------------------------------------------------------------------------------------------

	@Override
	public void mostraStringa(ClientEvent clientEvent) 
	{
		System.out.println(clientEvent.getSource());
	}
}