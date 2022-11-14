package tk.leooresende.redesocialmensagem.infra.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import tk.leooresende.redesocialmensagem.infra.dto.LoginForm;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemDto;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemForm;
import tk.leooresende.redesocialmensagem.infra.dto.UsuarioDto;
import tk.leooresende.redesocialmensagem.infra.util.ApiUtil;
import tk.leooresende.redesocialmensagem.infra.util.MensagemUtil;

@Service
public class MensagemService {

	private static final Map<String, WebSocketSession> SESSOES = new HashMap<>();
	private static final MensagemService MENSAGEM_SERVICE = new MensagemService();

	public void pegaOUsuarioAutenticadoESalvarSessao(WebSocketSession session) throws Exception {
		LoginForm loginForm = MensagemUtil.pegarTokenAtravezDaUrl(session.getUri());
		UsuarioDto usuarioDto = ApiUtil.buscarUsuarioNaApi(loginForm);
		String usernameUsuarioAutenticado = usuarioDto.getUsername();
		boolean usuarioJaEstaLogado = MensagemService.SESSOES.containsKey(usernameUsuarioAutenticado);
		if (usuarioJaEstaLogado) { 
			WebSocketSession sessaoAntiga = MensagemService.SESSOES.get(usernameUsuarioAutenticado);
			MensagemUtil.notificarQueOutroUsuarioLoggou(sessaoAntiga);
		}
		session.getAttributes().put(MensagemUtil.KEY_USUARIO_AUTENTICADO, usernameUsuarioAutenticado);
		this.salvarSessaoDoUsuario(usernameUsuarioAutenticado, session);
	}


	public void removerSessaoDoUsuario(WebSocketSession session) {
		String donoDaSessao = MensagemUtil.pegarDonoDaSessao(session);
		MensagemService.SESSOES.remove(donoDaSessao);
	}

	public void enviarMensagemParaODestinatario(MensagemForm mensagem, WebSocketSession session) throws Exception {
		String usuarioDaSessao = MensagemUtil.pegarUsuarioDaSessao(session);
		MensagemDto mensagemDto = ApiUtil.enviarMensagemDoUsuarioParaAApi(mensagem, session);
		this.enviarMensagemPraASessaoDoUsuario(usuarioDaSessao, mensagemDto);
	}

	private void enviarMensagemPraASessaoDoUsuario(String usuarioDaSessao, MensagemDto mensagemDto) throws IOException {
		String usuarioQueVaiReceberAMensagem = mensagemDto.getDestinatario();
		if (usuarioDaSessao.equals(usuarioQueVaiReceberAMensagem)) return;
		try {			
			WebSocketSession sessaoUsuarioQueVaiReceberAMensagem = MensagemService.SESSOES
					.get(usuarioQueVaiReceberAMensagem);
			TextMessage mensagemQueVaiSerEnviada = MensagemUtil.pegarMensagemEnviadaComoTextMessage(usuarioDaSessao,
					mensagemDto);
			sessaoUsuarioQueVaiReceberAMensagem.sendMessage(mensagemQueVaiSerEnviada);
		} catch (Exception ex) {}
	}

	public void mostrarSessoesAtivas() {
		System.out.println("===== Sessoes ativas ======");
		MensagemService.SESSOES.forEach((t, u) -> System.out.println(t));
		System.out.println();
	}

	public static MensagemService getInstance() {
		return MensagemService.MENSAGEM_SERVICE;
	}

	public MensagemForm pegarMensagemDoUsuario(TextMessage message) {
		String mensagemJson = message.getPayload();
		return MensagemUtil.pegarMensagemComoObjeto(mensagemJson);
	}

	private void salvarSessaoDoUsuario(String usuarioAutenticado, WebSocketSession session) throws IOException {
		MensagemService.SESSOES.put(usuarioAutenticado, session);
	}
}
