package it.volta.ts.ulivisamuel.nogpt_server.biz;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.volta.ts.ulivisamuel.nogpt_server.Config;
import it.volta.ts.ulivisamuel.nogpt_server.bean.Client;

public class GestoreListaUtenti 
{
	private Config config;
	
	//---------------------------------------------------------------------------------------------
	
	public GestoreListaUtenti()
	{
		config = Config.instance();
	}
	
	//---------------------------------------------------------------------------------------------
	
	public String getStringListaUtenti()
	{
		String utenti        = "";
		List<Client> clients = config.getClients();
		for(Client client : clients)
			utenti = utenti + client.getNomeUtente() + " ";
		return utenti;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public boolean accettazioneNome(String nomeUtente)
	{
		if(!verificaDisponibilitàNome(nomeUtente))
			return false;
		if(!verificaCredenzialiNome(nomeUtente))
			return false;
		return true;
	}
	
	//---------------------------------------------------------------------------------------------
	
	private boolean verificaDisponibilitàNome(String nomeUtente)
	{
		Comparator<Client> comparatore = Comparator.comparing(Client::getNomeUtente);
		List<Client> utenti            = config.getClients();
		Collections.sort(utenti, comparatore);
        int risultato = Collections.binarySearch(utenti, new Client(nomeUtente, null), comparatore);
        if(risultato < 0)
        	return true;
        else
        	return false;
	}
	
	//---------------------------------------------------------------------------------------------
	
	private boolean verificaCredenzialiNome(String nomeUtente)
	{
		if(nomeUtente.length() < 4 || nomeUtente.length() > 20)
			return false;
		if(nomeUtente.split(" ").length != 1)
			return false;
		if(nomeUtente.contains("@"))
			return false;
		return true;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void rimuoviClient(Client client)
	{
		List<Client> clients = config.getClients();
		clients.remove(client);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public Client trovaPosizioneNomeUtente(String nomeUtente)
	{
		Comparator<Client> comparatore = Comparator.comparing(Client::getNomeUtente);
		List<Client> utenti = config.getClients();
		Collections.sort(utenti, comparatore);
        int risultato = Collections.binarySearch(utenti, new Client(nomeUtente, null), comparatore);
        if(risultato < 0)
        	return null;
        else
        	return config.getClients().get(risultato);
	}
}
