package it.volta.ts.ulivisamuel.chat_nogpt_client.listener;

import java.util.EventListener;

public interface ConsoleInputListener extends EventListener
{
	public ClientEvent leggiStringa();
	public void        closeScanner();
}
