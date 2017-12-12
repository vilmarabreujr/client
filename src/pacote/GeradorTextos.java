package pacote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.http.HTTPRequest.Method;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.openid.connect.sdk.OIDCAccessTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;

public class GeradorTextos {

	static 
	{
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) 
	        {
	            if (hostname.equals("domain-a") || hostname.equals("domain-b") || hostname.equals("domain-c")) 
	            {
	                return true;
	            }
	            return false;
	        }
	    });
	}
	
	public static void main(String[] args) throws IOException, ParseException 
	{
		// TODO Auto-generated method stub

		//java -jar AccessControl.jar 10 5 false
		
		/*for( int i = 1; i < 101; i++ )
		{
			String texto = "java -jar AccessControl.jar " + i + " 100 false";
			System.out.println(texto);
		}
		for( int i = 1; i < 101; i++ )
		{
			String texto = "java -jar AccessControl.jar " + i + " 100 true";
			System.out.println(texto);
		}*/
		/*
		List<Integer> ListaNumerica = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		int TotalClientes = 10;	

		while( ListaNumerica.size() != TotalClientes )
		{
		      int randomInt = randomGenerator.nextInt(10);
		      if( !ListaNumerica.contains(randomInt) )
		      {
		    	  System.out.println("nao existe " + randomInt);
		    	  ListaNumerica.add(randomInt);
		      }
		      else
		      {
		    	  System.out.println("-");
		      }
		}*/
		
		
		String t = "{\"application_type\":\"web\",\"redirect_uris\":[ \"https://domain-b:8443/teste/\" ], \"client_name\": \"testeB\", \"logo_uri\": \"https://client.example.org/logo.png\", \"token_endpoint_auth_method\": \"client_secret_basic\", \"contacts\": [ \"admin@example.org\" ]}";
		HTTPRequest httpRequest = new HTTPRequest(Method.POST, new URL("https://domain-c:8443/c2id/client-reg"));
		httpRequest.setAuthorization("Bearer " + "ztucZS1ZyFKgh0tUEruUtiSTXhnexmd6");
		httpRequest.setContentType("application/json");
		httpRequest.setQuery(t);
		HTTPResponse httpResponse = httpRequest.send();
		String content = httpResponse.getContent();
		System.out.println(content);
		
		
	}

}
