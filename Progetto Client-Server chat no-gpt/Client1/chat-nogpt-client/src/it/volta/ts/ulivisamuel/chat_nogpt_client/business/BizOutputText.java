package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.PrintWriter;

import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ProtocolCommands;

public class BizOutputText extends Thread
{
	private PrintWriter           out;
    private ConsoleInputListener  inputListener;
    private ConsoleOutputListener consoleOutputListener;
    
    //---------------------------------------------------------------------------------------------
    
    public BizOutputText()
    {
    	out                   = null;
    	inputListener         = null;
    	consoleOutputListener = null;
    }
    
    //---------------------------------------------------------------------------------------------
 
    public void setOut(PrintWriter out)
    {
		this.out = out;
	}
    
	public void setInputListener(ConsoleInputListener inputListener)
	{
		this.inputListener = inputListener;
	}

	public void setConsoleOutputListener(ConsoleOutputListener consoleOutputListener) 
	{
		this.consoleOutputListener = consoleOutputListener;
	}

	//---------------------------------------------------------------------------------------------

	public void run()
    {
    	eseguiLogin
    }
	
	//---------------------------------------------------------------------------------------------
    
    private void eseguiLogin(String nomeUtente)
    {
    	inputListener.leggiStringa()
    	out.println(ProtocolCommands.LOGIN.toString() + " " + nomeUtente);
    }
    
    //---------------------------------------------------------------------------------------------

    private void mandaMessaggioContinuo() 
    {
    	String mess = "";
        while(!mess.equals(ProtocolCommands.EXIT.toString()))
        {
        	mess = (String) inputListener.leggiStringa().getSource();
        	out.println(ProtocolCommands.SEND + " " + mess);
        }
        consoleOutputListener.mostraStringa(new ClientEvent("\nConnessione interrotta"));
    }
}
