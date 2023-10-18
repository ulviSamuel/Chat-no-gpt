package it.volta.ts.ulivisamuel.nogpt_server.ui;

import it.volta.ts.ulivisamuel.nogpt_server.biz.GestoreRichieste;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerEvent;
import it.volta.ts.ulivisamuel.nogpt_server.events.ServerOutputListener;

public class Console implements ServerOutputListener
{
	private GestoreRichieste gestoreRichieste;
	
	//---------------------------------------------------------------------------------------------
	
	public Console()
	{
		gestoreRichieste = new GestoreRichieste(this);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void esegui()
	{
		gestoreRichieste.run();
	}
	
	//---------------------------------------------------------------------------------------------

	@Override
	public void mostraStringa(ServerEvent event)
	{
		System.out.println(event.getSource());
	}
}
