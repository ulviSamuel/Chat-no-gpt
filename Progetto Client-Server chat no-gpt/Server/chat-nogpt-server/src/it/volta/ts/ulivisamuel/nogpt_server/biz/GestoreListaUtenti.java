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
	
	public void ordinaListaUtenti()
	{
		List<Client> clients = config.getClients();
		Comparator<Client> comparator = new Comparator<Client>() 
		{
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getNomeUtente().compareTo(client2.getNomeUtente());
            }
        };
        Collections.sort(clients, comparator);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public boolean verificaDisponibilitàNome(String nomeUtente)
	{
		Comparator<Client> comparatore = Comparator.comparing(Client::getNomeUtente);
        int risultato = Collections.binarySearch(config.getClients(), new Client(nomeUtente, null), comparatore);
        if(risultato < 0)
        	return true;
        else
        	return false;
	}
}
