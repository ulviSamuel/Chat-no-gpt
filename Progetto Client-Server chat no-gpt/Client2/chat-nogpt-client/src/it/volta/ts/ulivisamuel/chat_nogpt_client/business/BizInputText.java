package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.BufferedReader;
import java.io.IOException;

import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ProtocolCommands;

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
			while(!s.equals(ProtocolCommands.CONNECTION_CLOSED.toString())) {
				s = in.readLine();
				consoleOutputListener.mostraStringa(new ClientEvent(s));
				decidiAzione(s);
			}
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
		case LOGGED:
			bizOutputText.setLoginEseguito(true);
			bizOutputText.setLockThread(false);
			break;
		case LOGIN_ERROR:
			consoleOutputListener.mostraErrore(new ClientEvent("\nNome utente già registrato, scegline un altro"));
			consoleInputListener.insDatiSocket();
		}
	}
}
