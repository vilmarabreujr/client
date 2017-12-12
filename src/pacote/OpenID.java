package pacote;

import java.net.URL;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPRequest.Method;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

public class OpenID 
{
	public static String authzSessionURL = "https://domain-c:8443/c2id/authz-sessions/rest/v1";
	public static String subjectSessionURL = "https://domain-c:8443/c2id/session-store/rest/v1";
	public static String tokenEndPoint = "https://domain-c:8443/c2id/token";
	
	public static String authorizationCode = "Bearer ztucZS1ZyFKgh0tUEruUtiSTXhnexmd6";
	
	public static final String clientID = "npaygst6jsfle";
	public static final String clientSecret = "nDg2Fil1bpSxIDaykq4pbgw_HmsqhkxXQnnzqlZknD4";
	public static final String redirectURI = "https://domain-a:8443/admin-client/";
	

	public static final String clientID_REMOTO = "000123";
	public static final String clientSecret_REMOTO = "7wKJNYFaKKg4FxUdi8_R75GGYsiWezvAbcdN1uSumE4";
	public static final String redirectURI_REMOTO = "https://domain-a:8443/scada-client/";	
	
	public static String RequisicaoHTTP(String url, Method method, String conteudo)
	{
		try
		{
			System.out.println(url);
			HTTPRequest httpRequest = new HTTPRequest(method, new URL(url));
			httpRequest.setAuthorization(authorizationCode);		
			if( conteudo != null )
			{
				httpRequest.setContentType("application/json;charset=UTF-8");
				httpRequest.setQuery(conteudo);
			}
			HTTPResponse httpResponse = httpRequest.send();
			String content = httpResponse.getContent();
			return content;
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
			return null;
		}
		
	}
}
