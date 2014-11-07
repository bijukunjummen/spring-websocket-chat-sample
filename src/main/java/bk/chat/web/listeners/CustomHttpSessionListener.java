package bk.chat.web.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CustomHttpSessionListener implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomHttpSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		logger.info("Session Creation event called!! : " + httpSessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		logger.info("Session Destroyed event called!! : " + httpSessionEvent.getSession().getId());
	}
}
