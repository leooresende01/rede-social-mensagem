package tk.leooresende.redesocialmensagem.infra.dto;

public class LoginForm {
	private String token;

	public LoginForm(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
