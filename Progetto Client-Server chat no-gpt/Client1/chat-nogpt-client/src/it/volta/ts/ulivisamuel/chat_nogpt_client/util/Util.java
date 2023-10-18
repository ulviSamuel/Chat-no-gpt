package it.volta.ts.ulivisamuel.chat_nogpt_client.util;

import java.util.Scanner;

public class Util 
{
	
	/**
	 * Inserimento valore int con controlli
	 * @param  scanner         � l'oggetto di classe Scanner utilizzato per la lettura di un input da tastiera
	 * @param  messaggio       � il messaggio di menu da stampare
	 * @param  valMin          � il valore minimo inseribile
	 * @param  valMax          � il valore massimo inseribile
	 * @param  insObbligatorio vale true se � obbligatorio inserire un dato
	 * @param  ritornoDefault  � il valore che la funzione torner� in caso venga premuto invio all'inserimento
	 * @return valore          � il valore inserito sottoposto a controlli
	 */

	public static int leggiInt(Scanner scanner, String messaggio, int valMin, int valMax, boolean insObbligatorio, int ritornoDefault)
	{
		int valore = valMin - 1;
		
		System.out.println(messaggio);
		
		while(valore < valMin || valore > valMax)
		{
			System.out.print("==> ");
			
			String sValue = scanner.nextLine();
			
			try {
				valore = Integer.parseInt(sValue);
			}
			catch(NumberFormatException e)
			{
				if(sValue.length() == 0 && !insObbligatorio)
					return ritornoDefault;
				else 
					valore = valMin - 1;
			}
		}
		
		return  valore;
	}
	
	/**
	 * Inserimento valore String con controlli
	 * @param  scanner         � l'oggetto di classe Scanner utilizzato per la lettura di un input da tastiera
	 * @param  messaggio       � il messaggio di menu da stampare
	 * @param  insObbligatorio vale true se � obbligatorio inserire un dato
	 * @param  ritornoDefault  � il valore che la funzione torner� in caso venga premuto invio all'inserimento
	 * @return valore          � il valore inserito sottoposto a controlli
	 */

	public static String leggiString(Scanner scanner, String messaggio, boolean insObbligatorio, String ritornoDefault)
	{
		String valore = "";
		
		System.out.println(messaggio);
		
		while(valore.length() == 0)
		{
			System.out.print("==> ");
			valore = scanner.nextLine().trim();
			
			if(valore.length() == 0 && !insObbligatorio)
				return ritornoDefault;
		}
		
		return  valore;
	}
	
	/**
	 * Inserimento valore float con controlli
	 * @param  scanner   � l'oggetto di classe Scanner utilizzato per la lettura di un input da tastiera
	 * @param  messaggio � il messaggio di menu da stampare
	 * @param  valMin    � il valore minimo inseribile
	 * @param  valMax    � il valore massimo inseribile
	 * @param  insObbligatorio vale true se � obbligatorio inserire un dato
	 * @param  ritornoDefault  � il valore che la funzione torner� in caso venga premuto invio all'inserimento
	 * @return valore    � il valore inserito sottoposto a controlli
	 */

	public static float leggiFloat(Scanner scanner, String messaggio, float valMin, float valMax, boolean insObbligatorio, float ritornoDefault)
	{
		float valore = valMin - 1;
		
		System.out.println(messaggio);
		
		while(valore < valMin || valore > valMax)
		{
			System.out.print("==> ");
			
			String sValue = scanner.nextLine();
			
			try {
				valore = Float.parseFloat(sValue);
			}
			catch(NumberFormatException e)
			{
				if(sValue.length() == 0 && !insObbligatorio)
					return ritornoDefault;
				else 
					valore = valMin - 1;
			}
		}
		
		return  valore;
	}
}
