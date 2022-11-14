package tk.leooresende.redesocialmensagem.infra.dto;

public class ChatDto {
	private Long id;
	private String usuarioQueIniciou;
	private String oOutroUsuarioDoChat;
	private String dataDeCriacao;
	private MensagemDto ultimaMensagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuarioQueIniciou() {
		return usuarioQueIniciou;
	}

	public void setUsuarioQueIniciou(String usuarioQueIniciou) {
		this.usuarioQueIniciou = usuarioQueIniciou;
	}

	public String getoOutroUsuarioDoChat() {
		return oOutroUsuarioDoChat;
	}

	public void setoOutroUsuarioDoChat(String oOutroUsuarioDoChat) {
		this.oOutroUsuarioDoChat = oOutroUsuarioDoChat;
	}

	public String getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(String dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public MensagemDto getUltimaMensagem() {
		return ultimaMensagem;
	}

	public void setUltimaMensagem(MensagemDto ultimaMensagem) {
		this.ultimaMensagem = ultimaMensagem;
	}
}
