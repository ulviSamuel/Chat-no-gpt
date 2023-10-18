package it.volta.ts.ulivisamuel.nogpt_server.bean;

import java.net.Socket;

public class Client
{
	private String nomeUtente;
	private Socket cso;
	
	//---------------------------------------------------------------------------------------------
	
	public Client(String nomeUtente, Socket cso) 
	{
		this.nomeUtente = nomeUtente;
		this.cso        = cso;
	}
	
	//---------------------------------------------------------------------------------------------

	public String getNomeUtente() 
	{
		return nomeUtente;
	}

	public Socket getCso() 
	{
		return cso;
	}
	
	
	public void setNomeUtente(String nomeUtente) 
	{
		this.nomeUtente = nomeUtente;
	}
	
	public void setCso(Socket cso) 
	{
		this.cso = cso;
	}
}
