package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

import it.volta.ts.ulivisamuel.chat_nogpt_client.Config;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ProtocolCommands;

public class BizOutputText extends Thread
{
	private PrintWriter           out;
    private ConsoleInputListener  inputListener;
    private ConsoleOutputListener consoleOutputListener;
    private Config                config;
    private boolean               lockThread;
    private boolean               loginEseguito;
    
    //---------------------------------------------------------------------------------------------
    
    public BizOutputText()
    {
    	out                   = null;
    	inputListener         = null;
    	consoleOutputListener = null;
    	config                = Config.instance();
    	lockThread            = true;
    	loginEseguito         = false;
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
	
	public void setLockThread(boolean lockThread)
	{
		this.lockThread = lockThread;
	}
	
	public void setLoginEseguito(boolean loginEseguito)
	{
		this.loginEseguito = loginEseguito;
	}

	//---------------------------------------------------------------------------------------------

	public void run()
    {
		while(!loginEseguito)
			eseguiLogin();
    	mandaMessaggioContinuo();
    }
	
	//---------------------------------------------------------------------------------------------
    
    private void eseguiLogin()
    {
    	lockThread = true;
    	out.println(ProtocolCommands.LOGIN.toString() + " " + config.getNomeUtente());
    	lockThread();
    }
    
    //-------------------------------------------------------------------------------------------
    
    private void lockThread()
    {
    	while(lockThread)
    	{
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
    	}
    }
    
    //---------------------------------------------------------------------------------------------

    public void mandaMessaggioContinuo() 
    {
    	lockThread = false;
    	String mess = "";
        while(!mess.equals(ProtocolCommands.EXIT.toString()))
        {
        	mess = (String) inputListener.leggiStringa().getSource();
        	out.println(ProtocolCommands.SEND + " " + mess);
        }
        consoleOutputListener.mostraStringa(new ClientEvent("\nConnessione interrotta"));
    }
}