package pacote;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;

public class Ambiente 
{
	private String serviceEndPoint = "https://domain-b:9444/services/RemoteUserStoreManagerService";
	private String adminUser = "admin";
	private String adminPassword = "admin";	
	private List<String> Papeis = new ArrayList<String>();
	
	public Ambiente()
	{
		
		Papeis.add("Doctor");
		Papeis.add("Patient");
		Papeis.add("Physician");

		/*Papeis.add("Engineer");
		Papeis.add("Lawyer");
		Papeis.add("Writer");*/
	}	
	
	public boolean CarregarUsuarios(List<String> todosUsuarios)
	{
        try 
        {
        	ConfigurationContext configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
            
            RemoteUserStoreManagerServiceStub adminStub = new RemoteUserStoreManagerServiceStub(configContext, serviceEndPoint);
            ServiceClient client = adminStub._getServiceClient();

            Options option = client.getOptions();
            option.setManageSession(true);
            String authCookie = null;
            option.setProperty(HTTPConstants.COOKIE_STRING, authCookie);            
            
            if( authCookie == null )
            {
                /**
                 * Setting basic auth headers for authentication for user admin
                 */
            	HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
                auth.setUsername(adminUser);
                auth.setPassword(adminPassword);
                auth.setPreemptiveAuthentication(true);
                option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
            }
            authCookie = (String) adminStub._getServiceClient().getServiceContext().getProperty(HTTPConstants.COOKIE_STRING);   

            String[] users = adminStub.listUsers("*", 10000);
                        
            List<String> novosUsuarios = new ArrayList<String>();
            
            for(int i = 0; i < todosUsuarios.size(); i++)
            {
            	boolean existe = false;
            	String usuario = todosUsuarios.get(i);
            	for(int j = 0; j < users.length; j++)
                {
                	if( usuario.equals(users[j]) )
                	{
                		existe = true;
                		String[] papeis = adminStub.getRoleListOfUser(usuario);
            			
                		for( int k = 0; k < papeis.length; k++ )
                		{
                			System.out.print(papeis[k] + " - ");
                		}
                		System.out.println("");
                		
                		break;
                	}
                }
            	if( !existe )
            	{
            		novosUsuarios.add(usuario);
            	}
            }
            /*
            int indicePapel = 0;
            //Ligar com os papeis
            for( int i = 0; i < novosUsuarios.size(); i++ )
            {
            	String usuario = novosUsuarios.get(i);     
            	
            	if( !adminStub.isExistingUser(usuario) )
            	{
            		adminStub.addUser(usuario, "secret", new String[]{Papeis.get(indicePapel)},null,null,false);
                	
                	System.out.println(usuario + " " + Papeis.get(indicePapel));
                	
                	if( indicePapel == 0 || indicePapel == 1)
                		indicePapel++;
                	else
                		indicePapel = 0;
            	}
            	else
            	{
            		System.out.println(usuario);
            	}
            	            		
            }*/
                        
    		return true;         
        } 
        catch (Exception e) 
        {
            System.out.println("\nError :  " + e.getMessage());
            e.printStackTrace();
    		return false;
        }
	}

}
