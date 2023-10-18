package it.volta.ts.ulivisamuel.nogpt_server.biz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.volta.ts.ulivisamuel.nogpt_server.Config;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerEvent;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerOutputListener;

public class GestoreRichieste extends Thread
{
	private ServerSocket          sso;
	private Socket                cso;
	private ServerOutputListener  serverOutputListener;
	private Config                config;
	
	//---------------------------------------------------------------------------------------------
	
	public GestoreRichieste(ServerOutputListener serverOutputListener)
	{
		this.serverOutputListener = serverOutputListener;
		sso         			  = null;
		cso          	          = null;
		config                    = Config.instance();
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void run()
	{
		gestisciConnessione();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void gestisciConnessione()
	{
		try {
			sso = new ServerSocket(config.getPort());
			serverOutputListener.mostraStringa(new ServerEvent("\nIn ascolto sulla porta '" + config.getPort() + "', attendo collegamento..."));		
			while(true)
			{
				config.incrementnUser();
				cso = sso.accept();
				new GestoreUtente(serverOutputListener, cso).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
