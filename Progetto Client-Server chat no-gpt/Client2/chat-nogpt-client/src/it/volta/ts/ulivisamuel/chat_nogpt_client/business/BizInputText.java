package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.BufferedReader;
import java.io.IOException;

import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ServerProtocolCommands;

public class BizInputText extends Thread
{
	private BufferedReader        in;
	private ConsoleOutputListener consoleOutputListener;
	private ConsoleInputListener  consoleInputListener;
	private BizOutputText         bizOutputText;
	
	//---------------------------------------------------------------------------------------------
	
	public BizInputText()
	{
		in                    = null;
		consoleOutputListener = null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void setIn(BufferedReader in)
	{
		this.in = in;
	}

	public void setConsoleOutputListener(ConsoleOutputListener consoleOutputListener)
	{
		this.consoleOutputListener = consoleOutputListener;
	}

	public void setConsoleInputListener(ConsoleInputListener consoleInputListener) 
	{
		this.consoleInputListener = consoleInputListener;
	}

	public void setBizOutputText(BizOutputText bizOutputText)
	{
		this.bizOutputText = bizOutputText;
	}

	//---------------------------------------------------------------------------------------------

	public void run()
	{
		leggiInputEsterni();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void leggiInputEsterni()
	{
		try {
			String s = "";
			while(!s.equals(ServerProtocolCommands.CONNECTION_CLOSED.toString())) {
				s = in.readLine();
				decidiAzione(s);
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decidiAzione(String s)
	{
		String command                         = s.substring(0, 4);
		String contenutoRicevuto               = "";
		if(s.length() != 4)
			contenutoRicevuto = s.substring(5, s.length());
		ServerProtocolCommands protocolCommand = ServerProtocolCommands.valueOf(command);
		switch (protocolCommand) {
		case LOGG:
			bizOutputText.setLoginEseguito(true);
			bizOutputText.setLockThread(false);
			break;
		case LOGE:
			consoleOutputListener.mostraErrore(new ClientEvent("\nNome utente gi� registrato, scegline un altro"));
			consoleInputListener.insDatiSocket();
		case RECE:
			gestisciRicezione(contenutoRicevuto);
			break;
		default:
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciRicezione(String contenutoRicevuto)
	{
		String[] partiRicevute = contenutoRicevuto.split(" FROM ");
		consoleOutputListener.mostraStringa(new ClientEvent("\n\n" + partiRicevute[1] + ": " + partiRicevute[0] + "\n\nInserisci messaggio da inviare\n==> "));
	}
}
