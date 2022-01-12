package de.apnmt.appointment.messaging.sender;

import de.apnmt.common.event.ApnmtEvent;
import de.apnmt.common.event.value.AppointmentEventDTO;
import de.apnmt.common.sender.ApnmtEventSender;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentEventSender implements ApnmtEventSender<AppointmentEventDTO> {

    private static final Logger log = LoggerFactory.getLogger(AppointmentEventSender.class);

    private NotificationMessagingTemplate notificationMessagingTemplate;

    public AppointmentEventSender(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    @Override
    public void send(String topic, ApnmtEvent<AppointmentEventDTO> event) {
        this.log.info("Send event {} to kafka topic {}", event, topic);
        notificationMessagingTemplate.convertAndSend(topic, event);
    }
}
