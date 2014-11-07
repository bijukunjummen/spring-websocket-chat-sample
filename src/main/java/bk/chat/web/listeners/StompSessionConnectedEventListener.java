package bk.chat.web.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompSessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(StompSessionConnectedEventListener.class);

	@Override
	public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
		logger.info(headerAccessor.toString());
	}
}
