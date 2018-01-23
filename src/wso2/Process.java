package wso2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Process {
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

	public static String get(String url) throws Exception
	{
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static String Authenticate(String user, String password) throws Exception
	{
		String clientID = "u4mdQYbk1yI3dj1JPFT7_k_d_F8a";
		String clientPWD = "iw3eiWp7hN2nPeRtk8zAMi7lLa4a";
		URL obj = new URL("https://localhost:9443/oauth2/token");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");

		//add request header
		//con.setRequestProperty("User-Agent", "Mozilla/5.0");
		String encoded = Base64.getEncoder().encodeToString((clientID+":"+clientPWD).getBytes(StandardCharsets.UTF_8));  //Java 8
		con.setRequestProperty("Authorization", "Basic "+encoded);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
		con.setDoOutput(true);
		
		String str =  "grant_type=password&username=" + user + "&password=" + password;
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write( outputInBytes );    
		os.close();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();
	}
	
	
	public static String getCode(String accessToken, String callBackURL) throws Exception
	{
		String clientID = "u4mdQYbk1yI3dj1JPFT7_k_d_F8a";
		URL obj = new URL("https://localhost:9443/oauth2/authorize");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");

		//add request header
		con.setRequestProperty("Authorization", "Bearer " + accessToken);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		
		String str =  "response_type=code&client_id=" + clientID + "&redirect_uri=" + callBackURL + "&scope=openid";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write( outputInBytes );    
		os.close();
		
		System.out.println(con.getResponseCode());
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(con.getHeaderField("Location"));

		//print result
		return response.toString();
	}
	
	public static String getTokenFull(String code, String callBackURL) throws Exception
	{
		String clientID = "u4mdQYbk1yI3dj1JPFT7_k_d_F8a";
		String clientPWD = "iw3eiWp7hN2nPeRtk8zAMi7lLa4a";
		URL obj = new URL("https://localhost:9443/oauth2/token");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");

		//add request header
		//con.setRequestProperty("User-Agent", "Mozilla/5.0");
		String encoded = Base64.getEncoder().encodeToString((clientID+":"+clientPWD).getBytes(StandardCharsets.UTF_8));  //Java 8
		con.setRequestProperty("Authorization", "Basic "+encoded);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
		con.setDoOutput(true);
		
		String str =  "grant_type=authorization_code&client_id=" + clientID + "&redirect_uri=" + callBackURL + "&code=" + code + "&scope=openid";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write( outputInBytes );    
		os.close();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();
	}
	
	public static String getToken(String code, String callBackURL) throws Exception
	{
		String url =  "https://localhost:8443/securitycontrols/api/authorizate?authorizationToken=" + code+ "&callbackUri="+callBackURL;
		String response = HttpConnection.sendGet(url);
		return response;
	}
	
	public static void main(String[] args) throws Exception 
	{	
		System.out.println(Authenticate("vilmar","vilmar"));
		System.out.println(getCode("88eb26a4-e9cd-3273-84da-78c726fd0d96","https://localhost:8443/securitycontrols/"));
		//System.out.println(getTokenFull("1d2b1727-314d-33ca-83c3-c458cf2091c8","https://localhost:8443/securitycontrols/"));
		//System.out.println(get("https://localhost:9443/oauth2/authorize?scope=openid&response_type=code&client_id=u4mdQYbk1yI3dj1JPFT7_k_d_F8a"));
		
	}
}
