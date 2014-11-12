package bk.chat.web.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class WebSocketSessionCapturingHandlerDecorator extends WebSocketHandlerDecorator {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionCapturingHandlerDecorator.class);

	public WebSocketSessionCapturingHandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
	}

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("afterConnectionEstablished");
		super.afterConnectionEstablished(session);
	}

	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info("handleMessage");
		super.handleMessage(session, message);
	}

	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info("handleTransportError");
		super.handleTransportError(session, exception);
	}

	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("afterConnectionClosed");
		super.afterConnectionClosed(session, closeStatus);
	}
}
