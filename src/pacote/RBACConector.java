package pacote;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPRequest.Method;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

public class RBACConector 
{
	/**
	 * The initial rbac configuration properties
	 */
	public static final String RBAC_PROPERTIES_FILE = "/WEB-INF/rbac.properties";
	private static String url = "https://domain-a:8443/rbac-service/";
	private static String urlREMOTO = "https://domain-b:8443/rbac-service/";

	public static String getUrl()
	{
		return url;
	}
	
	public RBACConector()
	{
	}
	
	public static String getAvaliableRoles(String acessToken)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "type=0";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.GET, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public static String getAvaliableRolesREMOTO(String acessToken)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = urlREMOTO + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "type=0";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.GET, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public String getActivateRoles(String acessToken)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "type=1";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.GET, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public String getDSoD(String acessToken)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "type=2";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.GET, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public static String AddActiveRole(String acessToken, String role)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "role=" + role;
			rbacURL += "&";
			rbacURL += "type=0";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.POST, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public static String DropActiveRole(String acessToken, String role)
	{
		String returnValue = null;
		try 
		{		
			String rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "role=" + role;
			rbacURL += "&";
			rbacURL += "type=1";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.POST, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return returnValue;
	}
	
	public static String RequestAccess(String acessToken, String resource, String action)
	{
		String returnValue = null;
		String rbacURL = "";
		try 
		{		
			rbacURL = getUrl() + "authorization";
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
		return returnValue;
	}
	
	public static String RequestAccessSRA(String acessToken, String resource, String action)
	{
		String returnValue = null;
		String rbacURL = "";
		try 
		{		
			rbacURL = urlREMOTO + "authorization";
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
		return returnValue;
	}
	
	public String CreateDSoD(String acessToken, String roleA, String roleB)
	{
		String returnValue = null;
		String rbacURL = "";
		try 
		{		
			rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "roleA=" + roleA;
			rbacURL += "&";
			rbacURL += "roleB=" +roleB;
			rbacURL += "&";
			rbacURL += "type=0";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.PUT, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnValue = "Erro: " + e.getMessage() + "Url: " + rbacURL;
		}		
		return returnValue;
	}
	
	public String RemoveDSoD(String acessToken, String roleA, String roleB)
	{
		String returnValue = null;
		String rbacURL = "";
		try 
		{		
			rbacURL = getUrl() + "controller";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "roleA=" + roleA;
			rbacURL += "&";
			rbacURL += "roleB=" +roleB;
			rbacURL += "&";
			rbacURL += "type=1";
			
			HTTPRequest httpRequest = new HTTPRequest(Method.PUT, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnValue = "Erro: " + e.getMessage() + "Url: " + rbacURL;
		}		
		return returnValue;
	}
	
	public static String CreatePolicy(String acessToken, String role, String resource, String action)
	{
		String returnValue = null;
		String rbacURL = "";
		try 
		{		
			rbacURL = getUrl() + "wallet";
			rbacURL += "?";
			rbacURL += "token=" + acessToken;
			rbacURL += "&";
			rbacURL += "role=" + role;
			rbacURL += "&";
			rbacURL += "resource=" +resource;
			rbacURL += "&";
			rbacURL += "action= " + action;
			
			HTTPRequest httpRequest = new HTTPRequest(Method.PUT, new URL(rbacURL));
			HTTPResponse httpResponse = httpRequest.send();

			returnValue = httpResponse.getContent();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnValue = "Erro: " + e.getMessage() + "Url: " + rbacURL;
		}		
		return returnValue;
	}
}

