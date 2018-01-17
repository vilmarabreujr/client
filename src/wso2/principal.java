package wso2;

import java.security.PrivateKey;
import java.security.PublicKey;

public class principal {
	static 
	{
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) 
	        {
	            return true;
	        }
	    });
	}

	public static void main(String[] args) throws Exception {		
		//Autenticação
		String token = "e89e22f9-b700-3317-81b7-a818b1bf211a";
		String role = "doutorando";
		String domain = "utfpr";
		
		
		//TESTAR A PESQUISA DE PAPÉIS
		System.out.println(validarToken(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println("-- Exporting the role: " + role + " to the domain: " + domain+ " --");
		System.out.println(exportRole(token, role,domain));		
		PrivateKey privateKey = RSA.loadPrivateKey("utfpr_private.key");
		System.out.println("-- Requesting exported roles of domain: " + domain + " --");
		String cipherRoles = getExportedRoles(token, domain);
		System.out.println(cipherRoles);
		System.out.println("-- Decrypt the exported roles with " + domain + " privateKey --");
		String decipherROles = RSA.decrypt(cipherRoles, privateKey);
		System.out.println(decipherROles);
			
		
		//TESTAR EXPORTAÇÃO DE PAPEL
		/*System.out.println(validarToken(token));
		System.out.println(getTrustedDomains(token));
		System.out.println(getRoles(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println(exportRole(token, role,domain));		
		System.out.println(getActivateRoles(token));
		String resource = "button";
		String action = "read";
		System.out.println(requestAccess(token, resource, action));		
		System.out.println(dropActivateRoles(token,role));
		System.out.println(getActivateRoles(token));*/
		
		//TESTAR CONTROLE DE ACESSO
		/*String resource = "button";
		String action = "read";
		System.out.println(validarToken(token));
		System.out.println(getRoles(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println(requestAccess(token, resource, action));
		System.out.println(dropActivateRoles(token,role));
		System.out.println(getActivateRoles(token));*/
		
		// TESTAR ATIVAÇÃO E SOD
		/*
		String roleConstraints = "admin";
		System.out.println(validarToken(token));
		System.out.println(getRoles(token));
		System.out.println(addConstraints(token, role, roleConstraints));
		System.out.println(getConstraints(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println(addActivateRoles(token,roleConstraints));
		System.out.println(getActivateRoles(token));
		System.out.println(dropConstraints(token, role, roleConstraints));
		System.out.println(addActivateRoles(token,roleConstraints));		
		System.out.println(getActivateRoles(token));
		System.out.println(dropActivateRoles(token,role));
		System.out.println(dropActivateRoles(token,roleConstraints));
		System.out.println(getActivateRoles(token));
		*/
	}
	
	public static String validarToken(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/validate-token?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String getRoles(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String getActivateRoles(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/activated?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String addActivateRoles(String token, String roleID) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/activated?accessToken=" + token + "&role=" + roleID;
		String response = HttpConnection.sendPost(url);
		return response;
	}
	
	public static String dropActivateRoles(String token, String roleID) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/activated?accessToken=" + token + "&role=" + roleID;
		String response = HttpConnection.sendDelete(url);
		return response;
	}
	
	public static String getConstraints(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/constraints?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String addConstraints(String token, String roleA, String roleB) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/constraints?accessToken=" + token + "&roleA=" + roleA + "&roleB=" + roleB;
		String response = HttpConnection.sendPost(url);
		return response;
	}
	
	public static String dropConstraints(String token, String roleA, String roleB) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/constraints?accessToken=" + token + "&roleA=" + roleA + "&roleB=" + roleB;
		String response = HttpConnection.sendDelete(url);
		return response;
	}
	
	public static String requestAccess(String token, String resource, String action) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/access-control?accessToken=" + token + "&resource=" + resource + "&action=" + action;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String exportRole(String token, String role, String domain) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/wallet?accessToken=" + token + "&role=" + role+ "&domain=" + domain;
		String response = HttpConnection.sendPost(url);
		return response;
	}	
	
	public static String getExportedRoles(String token, String domain) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/wallet?accessToken=" + token + "&domain=" + domain;
		String response = HttpConnection.sendGet(url);
		return response;
	}

	public static String getTrustedDomains(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/domain?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}

}
