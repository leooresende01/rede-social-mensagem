package tk.leooresende.redesocialmensagem.infra.dto;

public class MensagemDto {
	private Long id;
	private String mensagem;
	private String emissor;
	private Long chatId;
	private String destinatario;
	private String dataDeEnvio;
	private String dataSemFormatar;

	public String getDataSemFormatar() {
		return dataSemFormatar;
	}

	public void setDataSemFormatar(String dataSemFormatar) {
		this.dataSemFormatar = dataSemFormatar;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDataDeEnvio() {
		return dataDeEnvio;
	}

	public void setDataDeEnvio(String dataDeEnvio) {
		this.dataDeEnvio = dataDeEnvio;
	}
}
