package wso2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
		String token = "e870ffa8-eca6-3b2d-a876-ca5bb4d6dfc0";
		String role = "doutorando";
		String domain = "utfpr";
		
		//System.out.println(send());
		
		String id = "eyJ4NXQiOiJObUptT0dVeE16WmxZak0yWkRSaE5UWmxZVEExWXpkaFpUUmlPV0UwTldJMk0ySm1PVGMxWkEiLCJraWQiOiJkMGVjNTE0YTMyYjZmODhjMGFiZDEyYTI4NDA2OTliZGQzZGViYTlkIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoiQVN1clJJbWNNTVBjM2ZOX1pNcENRdyIsInN1YiI6InZpbG1hciIsImF1ZCI6WyJ1NG1kUVliazF5STNkajFKUEZUN19rX2RfRjhhIl0sImF6cCI6InU0bWRRWWJrMXlJM2RqMUpQRlQ3X2tfZF9GOGEiLCJhdXRoX3RpbWUiOjE1MTY3MTM2NTYsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTUxNjcxNzI1OSwiaWF0IjoxNTE2NzEzNjU5fQ.bZ5obPo3NcywoBFCaUJpqgxfPxAMYgrZ8thCeFc4lotsR8i42y68ExoRjErnvw41XCgqzC3Fv2wPKeNhfwMn6kW2VqojwwYUjVtU1zdM4OoUkJn9i-czn9R2i7azBWW1Qgr7CEYA_54IBwlRlTCC8_-9jm5yuBIzsHpRNDfH1zY";
		JWT.ValidateToken(id);
		
		//TESTAR A PESQUISA DE PAPÉIS
		/*System.out.println(validarToken(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println("-- Exporting the role: " + role + " to the domain: " + domain+ " --");
		System.out.println(exportRole(token, role,domain));		
		PrivateKey privateKey = RSA.loadPrivateKey("utfpr_private.key");
		System.out.println("-- Requesting exported roles of domain: " + domain + " --");
		String cipherRoles = getExportedRoles(token, domain);
		System.out.println(cipherRoles);
		System.out.println("-- Decrypt the exported roles with " + domain + " privateKey --");
		String decipherROles = RSA.decrypt(cipherRoles, privateKey);
		System.out.println(decipherROles);*/
			
		
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

	
	public static String send() throws Exception {
		
		String url = "http://localhost:9763/oauth2/authorize";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		// optional default is GET
		con.setRequestMethod("POST");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Authorization", "YWRtaW46YWRtaW4=");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
		String body = "response_type=code&client_id=HivrSzuzeQyZ7PkuJ0aOTxxqYloa&redirect_uri=https://localhost:8443/wso2Example/&scope=openid&prompt=none";
		byte[] outputInBytes = body.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write( outputInBytes );    

		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		os.close();

		//print result
		return response.toString();
	}
}
