package tk.leooresende.redesocialmensagem.infra.util;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

import tk.leooresende.redesocialmensagem.infra.dto.LoginForm;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemDto;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemForm;
import tk.leooresende.redesocialmensagem.infra.util.model.Token;

public class MensagemUtil {

	public static final String KEY_USUARIO_AUTENTICADO = "usuarioAutenticado";

	public static LoginForm pegarTokenAtravezDaUrl(URI uri) {
		String token = uri.getPath().replace("/", "");
		if (token != null) {
			return MensagemUtil.pegarPayloadComoLoginForm(token);
		} throw new RuntimeException();
	}

	private static LoginForm pegarPayloadComoLoginForm(String payload) {
		return new LoginForm(payload);
	}

	public static String pegarDonoDaSessao(WebSocketSession session) {
		return (String) session.getAttributes().get(MensagemUtil.KEY_USUARIO_AUTENTICADO);
	}

	public static MensagemForm pegarMensagemComoObjeto(String mensagemJson) {
		Gson gson = new Gson();
		return gson.fromJson(mensagemJson, MensagemForm.class);
	}

	public static String pegarUsuarioDaSessao(WebSocketSession session) {
		return (String) session.getAttributes().get(KEY_USUARIO_AUTENTICADO);
	}
	
	public static TextMessage pegarMensagemEnviadaComoTextMessage(String usuarioDaSessao, MensagemDto mensagem) {
		String mensagemDtoComoJson = MensagemUtil.pegarMensagemDtoComoJson(usuarioDaSessao, mensagem);
		return new TextMessage(mensagemDtoComoJson);
	}

	private static String pegarMensagemDtoComoJson(String usuarioDaSessao, MensagemDto mensagem) {
		return new Gson().toJson(mensagem);
	}

	public static void notificarQueOutroUsuarioLoggou(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage("Alguém acessou a conta em outro dispositivo"));
		session.close();
	}

	public static Token decodificarEPegarToken(LoginForm loginForm) {
		String token = loginForm.getToken();
		String payload = MensagemUtil.pegarPayloadDtoToken(token);
		return new Gson().fromJson(payload, Token.class);
	}

	private static String pegarPayloadDtoToken(String token) {
		String payload = token.split("[.]")[1];
		return new String(Base64.getDecoder().decode(payload));
	}
}
