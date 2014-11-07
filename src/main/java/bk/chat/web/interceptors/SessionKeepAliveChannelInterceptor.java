package bk.chat.web.interceptors;

import bk.chat.web.listeners.WSConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import java.util.Map;

public class SessionKeepAliveChannelInterceptor extends ChannelInterceptorAdapter {

	private SessionRepository sessionRepository;

	private static final Logger logger = LoggerFactory.getLogger(SessionKeepAliveChannelInterceptor.class);

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		Map<String, Object> sessionHeaders = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
		String sessionId = (String) sessionHeaders.get(WSConstants.SESSION_ATTR);
		if (sessionId != null) {
			Session session = sessionRepository.getSession(sessionId);
			if (session != null) {
				logger.info("Keeping session with id : " + sessionId + " alive ");
				sessionRepository.save(session);
			}
		}
		return super.preSend(message, channel);
	}

	@Autowired
	public void setSessionRepository(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
}
