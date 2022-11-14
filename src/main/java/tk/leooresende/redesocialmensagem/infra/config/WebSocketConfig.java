package tk.leooresende.redesocialmensagem.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import tk.leooresende.redesocialmensagem.infra.controller.MensagemController;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MensagemController(), "/*").setAllowedOrigins("*");
	}
}
