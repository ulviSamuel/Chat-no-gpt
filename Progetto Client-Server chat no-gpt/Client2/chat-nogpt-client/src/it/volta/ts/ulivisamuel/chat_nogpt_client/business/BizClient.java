package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import it.volta.ts.ulivisamuel.chat_nogpt_client.Config;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ProtocolCommands;

public class BizClient
{
	private Socket                client;
    private PrintWriter           out;
    private ConsoleInputListener  inputListener;
    private ConsoleOutputListener consoleOutputListener;
    private Config                config;
    
    //---------------------------------------------------------------------------------------------

    public BizClient(ConsoleInputListener inputListener, ConsoleOutputListener consoleOutputListener) 
    {
    	    config                     = Config.instance();
            client                     = null;
            out                        = null;
            this.inputListener         = inputListener;
            this.consoleOutputListener = consoleOutputListener;
    }
    
    //---------------------------------------------------------------------------------------------
    
    public void connettiServer()
    {
    	try {
            client = new Socket(config.getIpServer(), config.getPortaServer());
            out = new PrintWriter(client.getOutputStream(),true);
            mandaMessaggioContinuo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //---------------------------------------------------------------------------------------------

    private void mandaMessaggioContinuo() 
    {
    	String mess = "";
        while(!mess.equals(ProtocolCommands.EXIT.toString()))
        {
        	mess = (String) inputListener.leggiStringa().getSource();
        	out.println(mess);
        }
        consoleOutputListener.mostraStringa(new ClientEvent("\nConnessione interrotta"));
    }
}