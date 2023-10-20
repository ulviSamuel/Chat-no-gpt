package it.volta.ts.ulivisamuel.nogpt_server.bean;

import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
	private String      nomeUtente;
	private Socket      cso;
	private PrintWriter out;
	
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
	
	public PrintWriter getOut() 
	{
		return out;
	}
	
	

	public void setNomeUtente(String nomeUtente) 
	{
		this.nomeUtente = nomeUtente;
	}
	
	public void setCso(Socket cso) 
	{
		this.cso = cso;
	}
	
	public void setOut(PrintWriter out) 
	{
		this.out = out;
	}
}
