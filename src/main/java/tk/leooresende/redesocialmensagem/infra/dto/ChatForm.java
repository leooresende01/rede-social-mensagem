package tk.leooresende.redesocialmensagem.infra.dto;

public class ChatForm {
	private String oOutroUsuarioDoChat;

	public ChatForm(String oOutroUsuarioDoChat) {
		this.oOutroUsuarioDoChat = oOutroUsuarioDoChat;
	}

	public String getoOutroUsuarioDoChat() {
		return oOutroUsuarioDoChat;
	}

	public void setoOutroUsuarioDoChat(String oOutroUsuarioDoChat) {
		this.oOutroUsuarioDoChat = oOutroUsuarioDoChat;
	}
}
