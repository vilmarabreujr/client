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
		String token = "fbbebab6-2e98-3cf1-977a-1cfb480fa707";
		System.out.println(validarToken(token));
	}
	
	public static String validarToken(String token) throws Exception
	{
		String url = "https://localhost:8443/securitycontrols/api/validate-token?accessToken=" + token;
		String response = HttpConnection.sendGet(url);
		return response;
	}

}
