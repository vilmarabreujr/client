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
		String token = "e90130f4-cb72-3c2b-bbb2-7380f213cae4";
		String role = "doutorando";
		String roleConstraints = "doutorando";
		System.out.println(validarToken(token));
		System.out.println(getRoles(token));
		
		System.out.println(getActivateRoles(token));
		System.out.println(addActivateRoles(token,role));
		System.out.println(getActivateRoles(token));
		System.out.println(dropActivateRoles(token,role));
		System.out.println(getActivateRoles(token));
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
	
	public static String addConstraints(String token, String roleA, String roleB) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/constraints?accessToken=" + token + "&roleA=" + roleA + "&roleB=" + roleB;
		String response = HttpConnection.sendDelete(url);
		return response;
	}

}
