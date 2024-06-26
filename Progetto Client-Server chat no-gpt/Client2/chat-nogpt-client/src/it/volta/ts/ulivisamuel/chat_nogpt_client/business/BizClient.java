package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import it.volta.ts.ulivisamuel.chat_nogpt_client.Config;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ServerProtocolCommands;

public class BizClient
{
	private Socket                client;
    private PrintWriter           out;
    private ConsoleInputListener  inputListener;
    private ConsoleOutputListener consoleOutputListener;
    private Config                config;
    private BizInputText          bizInputText;
    private BizOutputText         bizOutputText;
    private BufferedReader        in;
    
    //---------------------------------------------------------------------------------------------

    public BizClient(ConsoleInputListener inputListener, ConsoleOutputListener consoleOutputListener) 
    {
    	    config                     = Config.instance();
            client                     = null;
            out                        = null;
            bizInputText               = new BizInputText();
            bizOutputText              = new BizOutputText();
            this.inputListener         = inputListener;
            this.consoleOutputListener = consoleOutputListener;
    }
    
    //---------------------------------------------------------------------------------------------
    
    public void connettiServer()
    {
    	try {
            client = new Socket(config.getIpServer(), config.getPortaServer());
            out    = new PrintWriter(client.getOutputStream(),true);
            in     = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bizOutputText.setOut(out);
            bizOutputText.setInputListener(inputListener);
            bizOutputText.setConsoleOutputListener(consoleOutputListener);
            bizInputText.setConsoleOutputListener(consoleOutputListener);
            bizInputText.setConsoleInputListener(inputListener);
            bizInputText.setIn(in);
            bizInputText.setBizOutputText(bizOutputText);
            if(!bizInputText.isAlive())
            	bizInputText.start();
            if(!bizOutputText.isAlive())
            	bizOutputText.start();
            else
            	bizOutputText.setLockThread(false);
        } catch (IOException e) {
        	consoleOutputListener.mostraErrore(new ClientEvent("\nImpossibile connettersi al server"));
        }
    }
}
