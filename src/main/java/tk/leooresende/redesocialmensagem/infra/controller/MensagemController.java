package tk.leooresende.redesocialmensagem.infra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import tk.leooresende.redesocialmensagem.infra.dto.MensagemForm;
import tk.leooresende.redesocialmensagem.infra.service.MensagemService;

@Controller
@ControllerAdvice
public class MensagemController extends TextWebSocketHandler {
	 
	private final MensagemService service = MensagemService.getInstance();
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		MensagemForm mensagem = this.service.pegarMensagemDoUsuario(message);
		this.service.enviarMensagemParaODestinatario(mensagem, session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		this.service.removerSessaoDoUsuario(session);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		this.service.pegaOUsuarioAutenticadoESalvarSessao(session);
	}
}
