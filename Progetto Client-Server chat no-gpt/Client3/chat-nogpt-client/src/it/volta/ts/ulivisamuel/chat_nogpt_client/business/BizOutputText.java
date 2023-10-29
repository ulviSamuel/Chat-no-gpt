package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

import java.io.PrintWriter;

import it.volta.ts.ulivisamuel.chat_nogpt_client.Config;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ClientEvent;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleInputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.listener.ConsoleOutputListener;
import it.volta.ts.ulivisamuel.chat_nogpt_client.protocol_commands.ClientProtocolCommands;

public class BizOutputText extends Thread
{
	private PrintWriter           out;
    private ConsoleInputListener  inputListener;
    private ConsoleOutputListener consoleOutputListener;
    private Config                config;
    private boolean               lockThread;
    private boolean               loginEseguito;
    private boolean               autoPing;
    
    //---------------------------------------------------------------------------------------------
    
    public BizOutputText()
    {
    	out                   = null;
    	inputListener         = null;
    	consoleOutputListener = null;
    	config                = Config.instance();
    	lockThread            = true;
    	loginEseguito         = false;
    	autoPing              = false;
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
    	out.println(ClientProtocolCommands.LOGI.toString() + " " + config.getNomeUtente());
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
    	consoleOutputListener.mostraStringa(new ClientEvent("\nUtente connesso\n"));
    	consoleOutputListener.mostraStringa(new ClientEvent("\nModalitï¿½ di messaggistica:\nPer inviare a tutti "
                + "gli utenti scrivi semplicemente il messaggio che vuoi"
                + " inviare\nPer inviare il messaggio solo ad un utente "
                + "specifico scrivi '@NomeUtente messaggio'. Per inviarlo"
                + " a piï¿½ utenti\nin privato concatena le @ come nell'esem"
                + "pio es. '@Mario @Andrea @Giovanni messaggio'\n"
                + "Il messaggio verrà inviato solamente ai destinatari registrati (messaggi con tag a nomi "
                + "sconosciuti al server non verranno inviati)\n"
                + "Per ottenere la lista degli utenti connessi scrivi LIST\n"
                + "Per disconnetterti dal server e uscire dal programma scrivi 'EXIT'\n\n"));
    	lockThread = false;
    	String mess = "";
        while(!mess.equals(ClientProtocolCommands.EXIT.toString()))
        {
        	if(!autoPing)
        		mess = (String) inputListener.leggiStringa("Tu: ").getSource();
        	else
        		mess = (String) inputListener.leggiStringa("").getSource();
        	autoPing = false;
        	out.println(creaMessDaInviare(mess));
        }
        consoleOutputListener.mostraStringa(new ClientEvent("\nConnessione interrotta"));
        System.exit(0);
    }
    
    //---------------------------------------------------------------------------------------------
    
    private String creaMessDaInviare(String mess)
    {
    	if(mess.equals(ClientProtocolCommands.LIST.toString()))
    	{
    		autoPing = true;
    		return mess;
    	}
    	else
    	{
    		String   messForm = ClientProtocolCommands.SEND + " ";
        	if(!mess.substring(0, 1).equals("@"))
        		messForm = messForm + mess + " " + ClientProtocolCommands.BROA;
        	else
        	{
        		String[] campi  = mess.split("@");
        		String fromPart = "";
        		for(int idx = 0; idx < campi.length; ++idx)
        		{
        			String[] campi2 = campi[idx].split(" ");
        			if(campi2.length == 1)
        			{
        				if(idx != 0)
        					fromPart = fromPart + " " + campi2[0];
        				else
        					fromPart = fromPart + campi2[0];
        			}
        			else
        			{
        				fromPart = fromPart + " " + campi2[0];
        				messForm = messForm + ricavaMess(campi2) + " " 
        			                        + ClientProtocolCommands.TOEE
        						            +  fromPart + " " 
        			                        + ClientProtocolCommands.EETO;
        				if(fromPart.contains(config.getNomeUtente()))
        					autoPing = true;
        				break;
        			}
        		}
        	}
        	return messForm;
    	}
    }
    
    //---------------------------------------------------------------------------------------------
    
    private String ricavaMess(String[] campi2)
    {
    	String mess = "";
    	for(int idx = 1; idx < campi2.length; ++idx)
    	{
    		if(idx == 1)
    			mess = mess + campi2[idx];
    		else
    			mess = mess + " " + campi2[idx];
    	}
    	return mess;
    }
}
