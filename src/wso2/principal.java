package wso2;

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
		String token = "4acf83fc-00a0-37cb-bb68-1e5100db0333";
		String role = "doutorando";
		//TESTAR CONTROLE DE ACESSO
		String resource = "button";
		String action = "read";
		System.out.println(validarToken(token));
		System.out.println(getRoles(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println(requestAccess(token, resource, action));
		System.out.println(dropActivateRoles(token,role));
		System.out.println(getActivateRoles(token));
		
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

}
