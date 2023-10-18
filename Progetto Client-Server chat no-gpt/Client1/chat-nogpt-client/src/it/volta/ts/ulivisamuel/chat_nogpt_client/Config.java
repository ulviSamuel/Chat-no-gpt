package it.volta.ts.ulivisamuel.chat_nogpt_client;

public class Config 
{
	private final static Config instance    = new Config();
	private              String ipServer    = "127.0.0.1";
	private              int    portaServer = 3333;
	
	//---------------------------------------------------------------------------------------------
	
	public static Config instance() 
	{
		return instance;
	}
	
	//---------------------------------------------------------------------------------------------

	public String getIpServer() 
	{
		return ipServer;
	}

	public void setIpServer(String ipServer) 
	{
		this.ipServer = ipServer;
	}

	
	
	public int getPortaServer()
	{
		return portaServer;
	}

	public void setPortaServer(int portaServer) 
	{
		this.portaServer = portaServer;
	}
}
