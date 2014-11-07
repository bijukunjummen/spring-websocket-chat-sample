package bk.chat.web;

import bk.chat.model.ChatMessage;
import com.google.common.collect.EvictingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChatController {
	@Autowired
	private HttpSession session;

	private static final Logger logger =  LoggerFactory.getLogger(ChatController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chats/{chatRoomId}")
	public void handleChat(@Payload ChatMessage message, @DestinationVariable("chatRoomId") String chatRoomId, MessageHeaders messageHeaders, Principal user) {
		logger.info(messageHeaders.toString());
		this.simpMessagingTemplate.convertAndSend("/topic/chats." + chatRoomId, "[" + getTimestamp() + "]:" + user.getName() + ":" + message.getMessage());
	}

	private String getTimestamp() {
		LocalDateTime date = LocalDateTime.now();
		return date.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	@ResponseBody
	@RequestMapping("/sessionId")
	public String sessionId() {
		return this.session.getId();
	}
}
