package bk.chat.monitoring;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@ManagedResource
//@Component
public class SessionRepositoryMBean {

	@Autowired
	private MapSessionRepository mapSessionRepository;

	@ManagedAttribute
	public int getSize() {
		Map<String, Session> backingMap =
				(Map<String, Session>)new DirectFieldAccessor(mapSessionRepository).getPropertyValue("sessions");
		return backingMap.size();
	}


}
