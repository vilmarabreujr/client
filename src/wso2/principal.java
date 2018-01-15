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
		System.out.println(validarToken(token));
		System.out.println(getRoles(token));
		System.out.println(getActivatedRoles(token));
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
	
	public static String getActivatedRoles(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/rbac/activated?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}

}
