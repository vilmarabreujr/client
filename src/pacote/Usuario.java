package pacote;

public class Usuario 
{
	private String login;
	private String sid;
	private String sidREMOTO;
	
	public String getSid() {
		return sid;
	}
	public String getSidREMOTO() {
		return sidREMOTO;
	}
	public void setSidREMOTO(String sidREMOTO) {
		this.sidREMOTO = sidREMOTO;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Usuario(String login)
	{
		this.login = login;
	}
}
