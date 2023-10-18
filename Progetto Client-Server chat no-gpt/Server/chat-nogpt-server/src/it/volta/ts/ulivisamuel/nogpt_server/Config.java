package it.volta.ts.ulivisamuel.nogpt_server;

public class Config 
{
	private final static Config instance = new Config();
	private              int    nUser    = 0;
	private              int    port     = 3333;
	
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

	public void setnUser(int nUser)
	{
		this.nUser = nUser;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}

	public void incrementnUser()
	{
		if(nUser == Integer.MAX_VALUE)
			nUser = 0;
		else
			++nUser;
	}
}
