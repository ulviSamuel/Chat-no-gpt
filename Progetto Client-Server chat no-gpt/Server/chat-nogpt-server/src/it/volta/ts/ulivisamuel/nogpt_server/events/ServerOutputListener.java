package it.volta.ts.ulivisamuel.nogpt_server.events;

import java.util.EventListener;

public interface ServerOutputListener extends EventListener 
{
	public void mostraStringa(ServerEvent event);
	public void mostraErrore(ServerEvent event);
}
