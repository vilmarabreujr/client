package Teste;

import java.net.URL;

import pacote.Util;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.http.HTTPRequest.Method;

public class StressOAuth {

	public static int Contador = 0;
	public static void main(String[] args) throws InterruptedException 
	{
		// TODO Auto-generated method stub

		long Quantidade = 5;
		boolean tokenON = true;
		
		long media = 0;
		/*for( int i = 0; i < Quantidade; i++ )
		{
			media += Requisicao(tokenON);
		}*/
		
		media = media / Quantidade;
		
		tokenON = false;
		
		long mediaSem = 0;
		for( int i = 0; i < Quantidade; i++ )
		{
			mediaSem += Requisicao(tokenON);
			Requisicao(!tokenON);
		}
		
		mediaSem = mediaSem / Quantidade;
		System.out.println("media com token: " + media);
		System.out.println("media sem token: " + mediaSem);
	}
	
	public static long Requisicao(boolean tokenON)
	{
		long tempoInicial = Util.getTempo();
		String url = "https://domain-a:8443/rbac-service/";
		String returnValue = null;
		String rbacURL = "";

		String acessToken = "";
		String resource = "disjuntor";
		String action = "read";
		
		if( tokenON )
		{
			acessToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJleHAiOjE0NTQ2MTQxNjYsInNpZCI6IjVoZHJHU1FnRUh3S1VONDRiRjhQY2tia0FrWGxTMEhPdnpYTDZTQUd3elUiLCJob21lIjoiZG9tYWluLWEiLCJzdWIiOiJ2aWxtYXIiLCJpcF9hZGRyZXNzIjoiMTAuMjAuMzAuNTAiLCJub25jZSI6IkdIT2ZOUEcyYUZzZ3FFSnV5WkNfTjI3Wk5qWHVXX3J3QW9NUnpZWGpTalUiLCJhdWQiOiIwMDAxMjMiLCJpc3MiOiJodHRwczpcL1wvbG9jYWxob3N0Ojg0NDNcL2MyaWQiLCJpYXQiOjE0NTQ2MTMyNjYsImFjciI6IjEiLCJjX2hhc2giOiJ6TElTQ0ZvTkZZa0lHTUp5aW1nVVl3IiwiYW1yIjpbImxkYXAiXX0.YRWSyWarU32Pc6eKd_bfHMaaBhttecZhtgkUfiJManoayUFaRggcztNlPqZ-mHb0cIbVEJYZUhI8wFOS4HVXJBIfnbCmjS_K_JFrTW9UkqvLNwRoIDREyH1GWp74pfzYRE0ye3yQ6cVZH1tVK0_akoiIan58JvxQ_hqcNQrtOso";
		}
		else
		{
			action += Integer.toString(Contador++);
		}
		
		try 
		{		
			rbacURL = url + "authorization";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "resource=" + resource;
			rbacURL += "&";
			rbacURL += "action=" + action;
			
			HTTPRequest httpRequest = new HTTPRequest(Method.GET, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnValue = "Erro: " + e.getMessage() + "Url: " + rbacURL;
		}		
		System.out.println(returnValue);
		long tempoFinal = Util.getDiferenca(tempoInicial);
		System.out.println(tempoFinal);
		return tempoFinal;
	}

}
