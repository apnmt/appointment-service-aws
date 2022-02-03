package de.apnmt.appointment.messaging.sender;

import de.apnmt.aws.common.config.AwsCloudProperties;
import de.apnmt.aws.common.util.TracingUtil;
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
    private AwsCloudProperties awsCloudProperties;

    public AppointmentEventSender(NotificationMessagingTemplate notificationMessagingTemplate, AwsCloudProperties awsCloudProperties) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
        this.awsCloudProperties = awsCloudProperties;
    }

    @Override
    public void send(String topic, ApnmtEvent<AppointmentEventDTO> event) {
        this.log.info("Send event {} to SNS topic {}", event, topic);
        String traceId = TracingUtil.createTraceId(awsCloudProperties.getTracing().getXRay().isEnabled());
        event.setTraceId(traceId);
        notificationMessagingTemplate.convertAndSend(topic, event);
    }
}
