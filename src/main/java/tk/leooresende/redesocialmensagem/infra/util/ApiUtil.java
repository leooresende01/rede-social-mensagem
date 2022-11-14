package tk.leooresende.redesocialmensagem.infra.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

import tk.leooresende.redesocialmensagem.infra.dto.ChatForm;
import tk.leooresende.redesocialmensagem.infra.dto.LoginForm;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemDto;
import tk.leooresende.redesocialmensagem.infra.dto.MensagemForm;
import tk.leooresende.redesocialmensagem.infra.dto.UsuarioDto;
import tk.leooresende.redesocialmensagem.infra.util.model.Token;

public class ApiUtil {
	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String CONTENT_TYPE = "content-type";
	private static final String AUTHORIZATION = "Authorization";
	private static final String URL_API = "https://api-rede-social.leooresende.tk";
	private static final String PATH_USUARIOS = "/api/v1/usuarios";
	private static final String PATH_CHATS = "/api/v1/chats";
	private static final String AUTH_TYPE = "Bearer ";

	public static UsuarioDto buscarUsuarioNaApi(LoginForm loginForm) throws Exception {
		Token tokenDescriptografado = MensagemUtil.decodificarEPegarToken(loginForm);
		HttpRequest request = HttpRequest
				.newBuilder(new URI(ApiUtil.URL_API + ApiUtil.PATH_USUARIOS + "/" + tokenDescriptografado.getSub()))
				.GET().setHeader(ApiUtil.AUTHORIZATION, ApiUtil.AUTH_TYPE + loginForm.getToken()).build();
		HttpClient clienteHttp = HttpClient.newHttpClient();
		HttpResponse<String> resposta = clienteHttp.send(request, BodyHandlers.ofString());
		String usuarioDtoDescriptografadoJSON = SecurityUtil.descriptografarAESComAKey(resposta.body());
		return new Gson().fromJson(usuarioDtoDescriptografadoJSON, UsuarioDto.class);
	}

	public static MensagemDto enviarMensagemDoUsuarioParaAApi(MensagemForm mensagem, WebSocketSession session)
			throws Exception {
		String mensagemJson = new Gson().toJson(mensagem);
		BodyPublisher mensagemBody = BodyPublishers.ofString(mensagemJson);
		HttpRequest httpRequest = HttpRequest
				.newBuilder(new URI(ApiUtil.URL_API + ApiUtil.PATH_CHATS + "/" + mensagem.getSendTo() + "/mensagens"))
				.POST(mensagemBody).setHeader(ApiUtil.CONTENT_TYPE, ApiUtil.CONTENT_TYPE_JSON)
				.setHeader(ApiUtil.AUTHORIZATION, ApiUtil.AUTH_TYPE + mensagem.getToken()).build();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
		if (response.statusCode() == HttpStatus.CREATED.value()) {
			return new Gson().fromJson(response.body(), MensagemDto.class);
		}

		if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
			ApiUtil.criarChatComOUsuario(mensagem);
			return ApiUtil.enviarMensagemDoUsuarioParaAApi(mensagem, session);
		}
		session.sendMessage(new TextMessage("Api status erro: " + response.statusCode()));
		session.close();
		throw new RuntimeException(String.valueOf(response.statusCode()));
	}

	private static void criarChatComOUsuario(MensagemForm mensagem) throws Exception {
		ChatForm chatForm = new ChatForm(mensagem.getSendTo());
		String chatFormJson = new Gson().toJson(chatForm);
		BodyPublisher corpoRequest = BodyPublishers.ofString(chatFormJson);
		HttpRequest httpRequest = HttpRequest.newBuilder(new URI(ApiUtil.URL_API + ApiUtil.PATH_CHATS)).POST(corpoRequest)
				.setHeader(ApiUtil.AUTHORIZATION, ApiUtil.AUTH_TYPE + mensagem.getToken())
				.setHeader(ApiUtil.CONTENT_TYPE, ApiUtil.CONTENT_TYPE_JSON)
				.build();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		if (httpResponse.statusCode() == HttpStatus.CREATED.value()) {
			return;
		} throw new RuntimeException();
	}

}
