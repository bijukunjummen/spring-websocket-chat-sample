package bk.chat.web.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class StompUnSubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {

	private static final Logger logger = LoggerFactory.getLogger(StompUnSubscribeEventListener.class);

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent sessionUnSubscribeEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionUnSubscribeEvent.getMessage());
		logger.info(headerAccessor.toString());
	}
}
