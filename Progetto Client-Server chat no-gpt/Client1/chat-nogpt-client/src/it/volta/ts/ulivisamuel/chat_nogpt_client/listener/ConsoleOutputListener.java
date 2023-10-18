package it.volta.ts.ulivisamuel.chat_nogpt_client.listener;

import java.util.EventListener;

public interface ConsoleOutputListener extends EventListener
{
	public void mostraStringa(ClientEvent clientEvent);
}
