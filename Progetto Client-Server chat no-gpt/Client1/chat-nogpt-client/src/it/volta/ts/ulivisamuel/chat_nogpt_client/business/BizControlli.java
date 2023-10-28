package it.volta.ts.ulivisamuel.chat_nogpt_client.business;

public class BizControlli 
{
	public boolean controllaNomeUtente(String nome)
	{
		if(nome.length() < 4 || nome.length() > 20)
			return false;
		if(nome.split(" ").length != 1)
			return false;
		if(nome.contains("@"))
			return false;
		return true;
	}
}
