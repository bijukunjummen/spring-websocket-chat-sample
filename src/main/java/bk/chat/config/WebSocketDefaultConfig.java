package bk.chat.config;

import bk.chat.web.interceptors.SessionKeepAliveChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketDefaultConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//config.enableStompBrokerRelay("/queue", "/topic/");
		config.enableSimpleBroker("/topic/", "/queue/");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(sessionKeepAliveChannelInterceptor());
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").withSockJS();//.setInterceptors(httpSessionIdHandshakeInterceptor());
	}

//	@Bean
//	public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
//		return new HttpSessionIdHandshakeInterceptor();
//	}

	@Bean
	public SessionKeepAliveChannelInterceptor sessionKeepAliveChannelInterceptor() {
		return new SessionKeepAliveChannelInterceptor();
	}
}