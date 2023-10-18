package it.volta.ts.ulivisamuel.nogpt_server;

import java.util.ArrayList;
import java.util.List;

import it.volta.ts.ulivisamuel.nogpt_server.bean.Client;

public class Config 
{
	private final static Config       instance = new Config();
	private              int          nUser    = 0;
	private              int          port     = 3333;
	private              List<Client> clients  = new ArrayList<Client>();
	
	//---------------------------------------------------------------------------------------------
	
	public static Config instance() 
	{
		return instance;
	}
	
	//---------------------------------------------------------------------------------------------

	public int getnUser() 
	{
		return nUser;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public List<Client> getClients()
	{
		return clients;
	}

	
	
	public void setnUser(int nUser)
	{
		this.nUser = nUser;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}
	
	public void setClients(List<Client> clients)
	{
		this.clients = clients;
	}
	
	//---------------------------------------------------------------------------------------------

	public void incrementnUser()
	{
		if(nUser == Integer.MAX_VALUE)
			nUser = 0;
		else
			++nUser;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void addClient(Client newClient)
	{
		clients.add(newClient);
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void removeClient(Client client)
	{
		clients.remove(client);
	}
}
