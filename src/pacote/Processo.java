package pacote;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
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
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCAccessTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;

public class Processo extends Thread
{
	private Usuario usuario = null;
	private int NumeroRequisicoes = 0;
	private String sessionCookie = null;
	private String codigoTemporario = null;
	private AccessToken accessToken = null;
	private AccessToken accessToken_REMOTO = null;
	private String papel = null;
	private boolean SRA_ENABLE = false;
	private List<String> listaImpressao = new ArrayList<String>();
	private boolean sucesso = false;
	
	public Processo(Usuario usuario, int NumeroRequisicoes, boolean SRA_ENABLE)
	{
		this.usuario = usuario;
		this.NumeroRequisicoes = NumeroRequisicoes;
		this.SRA_ENABLE = SRA_ENABLE;
	}
	
	public void run()
	{
		long tempoInicial = Util.getTempo();
		long tempoTotal = tempoInicial;
		if( !Autenticar() )
		{
			System.err.println("Erro: Autenticar");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("autenticar: " + Util.getDiferenca(tempoInicial));
		tempoInicial = Util.getTempo();
		if( !BuscarTokens() )
		{
			System.err.println("Erro: BuscarTokens");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("buscar tokens: " + Util.getDiferenca(tempoInicial));
		tempoInicial = Util.getTempo();
		if( !BuscarPapeis() )
		{
			System.err.println("Erro: BuscarPapeis");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("buscar papeis: " + Util.getDiferenca(tempoInicial));
		tempoInicial = Util.getTempo();
		if( !AtivarPapel() )
		{
			System.err.println("Erro: AtivarPapel");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("ativar papel: " + Util.getDiferenca(tempoInicial));
		
		
		if( SRA_ENABLE )
		{
			tempoInicial = Util.getTempo();
			if( !AutenticarSRA() )
			{
				System.err.println("Erro: AutenticarSRA");
				System.err.println(usuario.getLogin());
				return;
			}
			Imprimir("autenticar remoto: " + Util.getDiferenca(tempoInicial));
			
			tempoInicial = Util.getTempo();
			if( !BuscarTokensSRA() )
			{
				System.err.println("Erro: BuscarTokensSRA");
				System.err.println(usuario.getLogin());
				return;
			}
			Imprimir("buscar tokens remoto: " + Util.getDiferenca(tempoInicial));

			tempoInicial = Util.getTempo();
			if( !BuscarPapeisREMOTO() )
			{
				System.err.println("Erro: BuscarPapeisREMOTO");
				System.err.println(usuario.getLogin());
				return;
			}
			Imprimir("buscar papeis remoto: " + Util.getDiferenca(tempoInicial));
			
			tempoInicial = Util.getTempo();
			if( !RequisitarAcessoSRA(NumeroRequisicoes) )
			{
				System.err.println("Erro: RequisitarAcesso");
				System.err.println(usuario.getLogin());
				return;
			}
			Imprimir("realizar " + NumeroRequisicoes + " requisicoes de acesso remoto: " + Util.getDiferenca(tempoInicial));	
		}
		else
		{
			Imprimir("autenticar remoto: 0");
			Imprimir("buscar tokens remoto: 0");
			Imprimir("buscar papeis remoto: 0");
			
			tempoInicial = Util.getTempo();
			if( !RequisitarAcesso(NumeroRequisicoes) )
			{
				System.err.println("Erro: RequisitarAcesso");
				System.err.println(usuario.getLogin());
				return;
			}
			Imprimir("realizar " + NumeroRequisicoes + " requisicoes de acesso locais: " + Util.getDiferenca(tempoInicial));			
		}		
		tempoInicial = Util.getTempo();
		if( !DesativarPapel() )
		{
			System.err.println("Erro: DesativarPapel");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("desativar papel: " + Util.getDiferenca(tempoInicial));
		tempoInicial = Util.getTempo();
		if( !Deslogar() )
		{
			System.err.println("Erro: Deslogar");
			System.err.println(usuario.getLogin());
			return;
		}
		Imprimir("deslogar: " + Util.getDiferenca(tempoInicial));
		
		Imprimir("Tempo total da thread: " + Util.getDiferenca(tempoTotal));
		sucesso = true;
	}
	public boolean isSucesso()
	{
			return sucesso;
	}
	
	public void Imprimir(String mensagem)
	{
		listaImpressao.add(mensagem);
	}
	
	public void MostrarImpressao(String id)
	{
		for( int i = 0; i < listaImpressao.size(); i++ )
		{
			System.out.println(id + "-> " + listaImpressao.get(i));
		}
	}
	
	public String TextoCSV(String id)
	{
		String retorno = "";
		for( int i = 0; i < listaImpressao.size(); i++ )
		{
			String current = id + ";" + listaImpressao.get(i).replace(":", ";");
			current += ";";
			retorno += current + "\n";
		}
		return retorno;
	}
	
	public String TextoConsumo()
	{
		String retorno = "";
		for( int i = 0; i < listaImpressao.size(); i++ )
		{
			String current = listaImpressao.get(i);
			current = current.split(": ")[1];
			retorno += current + ";";
		}
		return retorno;
	}
	public boolean GerarSessao()
	{
		boolean retorno = false;
		try
		{
			//Gerar Sessao
			String nonce = new Nonce().getValue();
			String state = new com.nimbusds.oauth2.sdk.id.State().getValue();
			String query = "{\"query\":\"?response_type=code&client_id=" + OpenID.clientID + "&redirect_uri=" + OpenID.redirectURI + "&scope=openid+profile+rbac-home-full+rbac-remote-read&state=" + state + "&nonce=" + nonce + "&display=popup\"}";
			String resultado = OpenID.RequisicaoHTTP(OpenID.authzSessionURL, Method.POST, query);
			if( resultado != null )
			{
				JSONObject jObject = new JSONObject(resultado);
				String sid = jObject.get("sid").toString();
				usuario.setSid(sid);
				retorno = true;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean GerarSessaoSRA()
	{
		boolean retorno = false;
		try
		{
			//Gerar Sessao
			String nonce = new Nonce().getValue();
			String state = new com.nimbusds.oauth2.sdk.id.State().getValue();
			String query = "{\"query\":\"?response_type=code&client_id=" + OpenID.clientID_REMOTO + "&redirect_uri=" + OpenID.redirectURI_REMOTO + "&scope=openid+profile+rbac-home-full+rbac-remote-read&state=" + state + "&nonce=" + nonce + "&display=popup\",\"sub_sid\":\"" + sessionCookie +"\"}";
			String resultado = OpenID.RequisicaoHTTP(OpenID.authzSessionURL, Method.POST, query);
			if( resultado != null )
			{
				JSONObject jObject = new JSONObject(resultado);
				String sid = jObject.get("sid").toString();
				usuario.setSidREMOTO(sid);
				retorno = true;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean Consentimento(boolean isSRA)
	{
		boolean retorno = false;
		try
		{			
			String query = "{\"scope\":[\"openid\",\"profile\",\"rbac-home-full\",\"rbac-remote-read\"],\"claims\":[\"email\",\"nickname\",\"profile\"],\"preset_claims\":{\"id_token\":{\"ip_address\":\"10.20.30.50\",\"sid\":\"" + sessionCookie + "\",\"home\":\"domain-a\"},\"userinfo\":{\"home\":\"domain-a\",\"rbac_home\":\"https://domain-a:8443/rbac-service/\"}},\"audience\":[],\"long_lived\":true,\"issue_refresh_token\":true,\"access_token\":{\"encoding\":\"SELF_CONTAINED\",\"lifetime\":6000}}";
			
			String sid = usuario.getSid();
			if( isSRA )
				sid = usuario.getSidREMOTO();
			
			HTTPRequest httpRequest = new HTTPRequest(Method.PUT, new URL(OpenID.authzSessionURL + "/" + sid+"?ajax=true"));
			httpRequest.setAuthorization(OpenID.authorizationCode);		
			httpRequest.setContentType("application/json;charset=UTF-8");
			httpRequest.setQuery(query);
			HTTPResponse httpResponse = httpRequest.send();			
			String content = httpResponse.getLocation().toString();
			codigoTemporario = content.split("code=")[1];	
			retorno = true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean Autenticar()
	{
		boolean retorno = false;
		try
		{
			//Gerar Sessao
			if( !GerarSessao() )
				return false;
			
			String query = "{\"sub\":\"" + usuario.getLogin() + "\",\"acr\":\"1\",\"amr\":[\"ldap\"],\"data\":{\"name\":\"" + usuario.getLogin() + "\",\"email\":\"" + usuario.getLogin() + "@wonderland.net\"}}";
			String resultado = OpenID.RequisicaoHTTP(OpenID.authzSessionURL + "/" + usuario.getSid(), Method.PUT, query);
			if( resultado != null )
			{
				JSONObject jObject = new JSONObject(resultado);
				if( !jObject.isNull("type") && jObject.get("type").equals("consent") )
				{
					jObject = jObject.getJSONObject("sub_session");
					sessionCookie = jObject.getString("sid");
					if( !Consentimento(false) )
						return false;
					retorno = true;
				}
			}			
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean AutenticarSRA()
	{
		boolean retorno = false;
		try
		{
			//Gerar Sessao
			if( !GerarSessaoSRA() )
				return false;
			
			if( !Consentimento(true) )
				return false;
			retorno = true;		
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean BuscarTokens()
	{
		boolean retorno = false;
		try
		{
			AuthorizationCode authzCode = new AuthorizationCode(codigoTemporario);

			ClientID clientID = new ClientID(OpenID.clientID);
			Secret clientSECRET = new Secret(OpenID.clientSecret);
			
			URI tokenEndpointURI = new URI(OpenID.tokenEndPoint);
			ClientAuthentication clientAuth = new ClientSecretBasic(
				clientID,
				clientSECRET);

			AuthorizationGrant authzGrant = new AuthorizationCodeGrant(
				authzCode,
				new URI(OpenID.redirectURI),
				clientID);

			TokenRequest tokenRequest = new TokenRequest(tokenEndpointURI, clientAuth, authzGrant);
		
			HTTPRequest httpRequest = tokenRequest.toHTTPRequest();
			HTTPResponse httpResponse = httpRequest.send();
			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(httpResponse);

			if (tokenResponse instanceof TokenErrorResponse) {

				System.out.println(httpResponse.getContent());
				int httpStatusCode = httpResponse.getStatusCode();
				System.out.println(httpStatusCode);
				return false;
			}

			OIDCAccessTokenResponse accessTokenResponse = (OIDCAccessTokenResponse)tokenResponse;
											
			accessToken = accessTokenResponse.getAccessToken();		
			retorno = true;		
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean BuscarTokensSRA()
	{
		boolean retorno = false;
		try
		{
			AuthorizationCode authzCode = new AuthorizationCode(codigoTemporario);	

			ClientID clientID = new ClientID(OpenID.clientID_REMOTO);
			Secret clientSECRET = new Secret(OpenID.clientSecret_REMOTO);
			
			URI tokenEndpointURI = new URI(OpenID.tokenEndPoint);
			ClientAuthentication clientAuth = new ClientSecretBasic(
				clientID,
				clientSECRET);

			AuthorizationGrant authzGrant = new AuthorizationCodeGrant(
				authzCode,
				new URI(OpenID.redirectURI_REMOTO),
				clientID);

			TokenRequest tokenRequest = new TokenRequest(tokenEndpointURI, clientAuth, authzGrant);
			
			HTTPRequest httpRequest = tokenRequest.toHTTPRequest();

			HTTPResponse httpResponse = httpRequest.send();

			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(httpResponse);

			if (tokenResponse instanceof TokenErrorResponse) {

				System.out.println(httpResponse.getContent());
				int httpStatusCode = httpResponse.getStatusCode();
				System.out.println(httpStatusCode);
				return false;
			}

			OIDCAccessTokenResponse accessTokenResponse = (OIDCAccessTokenResponse)tokenResponse;
											
			accessToken_REMOTO = accessTokenResponse.getAccessToken();		
			retorno = true;		
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean BuscarPapeis()
	{
		boolean retorno = false;
		try
		{
			String content = RBACConector.getAvaliableRoles(accessToken.getValue());
			JSONObject jObject = new JSONObject(content);
			if( jObject.isNull("roles") )
			{
				System.out.println(content);
				return false;
			}
			JSONArray listAppliances = jObject.getJSONArray("roles");
			
			for( int i = 0; i < listAppliances.length(); i++ )
			{
				JSONObject jCurrent = (JSONObject)listAppliances.get(i);
				jCurrent = (JSONObject)jCurrent.get("role");

				String id = jCurrent.getString("id");
				if( !id.equals("admin") && !id.equals("Internal/everyone") )
				{
					papel = id;			
					retorno = true;				
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean BuscarPapeisREMOTO()
	{
		boolean retorno = false;
		try
		{
			String content = RBACConector.getAvaliableRolesREMOTO(accessToken_REMOTO.getValue());
			JSONObject jObject = new JSONObject(content);
			if( jObject.isNull("roles") )
			{
				return false;
			}	
			retorno = true;		
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean AtivarPapel()
	{
		boolean retorno = false;
		try
		{
			String content = RBACConector.AddActiveRole(accessToken.getValue(),papel);
			JSONObject jObject = new JSONObject(content);
			if( jObject.isNull("sucess") )
			{
				return false;
			}	
			retorno = true;	
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean DesativarPapel()
	{
		boolean retorno = false;
		try
		{
			String content = RBACConector.DropActiveRole(accessToken.getValue(),papel);
			JSONObject jObject = new JSONObject(content);
			if( jObject.isNull("sucess") )
			{
				return false;
			}	
			retorno = true;	
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean Deslogar()
	{
		boolean retorno = false;
		try
		{
			String resultado = OpenID.RequisicaoHTTP(OpenID.subjectSessionURL + "/" + sessionCookie, Method.DELETE, null);
			if( resultado != null )
			{
				retorno = true;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	
	public boolean RequisitarAcesso(int loop)
	{
		boolean retorno = false;
		try
		{
			String token = accessToken.getValue();
			String object = "resource" + papel;
			String action = "read";
			
			
			retorno = true;
			for( int i = 0; i < loop; i++ )
			{
				String content = RBACConector.RequestAccess(token, object, action);
				JSONObject jObject = new JSONObject(content);
				if( jObject.isNull("sucess") )
				{
					String status = jObject.getString("error");
					retorno = false;
				}
			}
			retorno = true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean RequisitarAcessoSRA(int loop)
	{
		boolean retorno = false;
		try
		{
			String token = accessToken_REMOTO.getValue();
			String object = "";
			String action = "read";
						
			if( papel.equals("Doctor") )
			{
				object = "resourceDoctor";
			}
			else if( papel.equals("Patient") )
			{
				object = "resourcePatient";
			}
			else //Physician
			{
				object = "resourcePhysician";
			}
			retorno = true;
			for( int i = 0; i < loop; i++ )
			{
				String content = RBACConector.RequestAccessSRA(token, object, action);
				JSONObject jObject = new JSONObject(content);
				if( jObject.isNull("sucess") )
				{
					String status = jObject.getString("error");
					retorno = false;
				}
			}
			retorno = true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean CriarPolitica()
	{
		boolean retorno = false;
		try
		{
			String token = accessToken.getValue();
			String resource = "resource" + papel;
			String action = "read";			
			
			String content = RBACConector.CreatePolicy(token, papel, resource, action);
			JSONObject jObject = new JSONObject(content);
			if( jObject.isNull("sucess") )
			{
				String status = jObject.getString("error");
				retorno = false;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		return retorno;
	}
}
