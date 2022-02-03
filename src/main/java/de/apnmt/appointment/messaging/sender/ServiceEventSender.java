package de.apnmt.appointment.messaging.sender;

import de.apnmt.aws.common.config.AwsCloudProperties;
import de.apnmt.aws.common.util.TracingUtil;
import de.apnmt.common.event.ApnmtEvent;
import de.apnmt.common.event.value.ServiceEventDTO;
import de.apnmt.common.sender.ApnmtEventSender;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceEventSender implements ApnmtEventSender<ServiceEventDTO> {

    private static final Logger log = LoggerFactory.getLogger(ServiceEventSender.class);

    private NotificationMessagingTemplate notificationMessagingTemplate;
    private AwsCloudProperties awsCloudProperties;

    public ServiceEventSender(NotificationMessagingTemplate notificationMessagingTemplate, AwsCloudProperties awsCloudProperties) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
        this.awsCloudProperties = awsCloudProperties;
    }

    @Override
    public void send(String topic, ApnmtEvent<ServiceEventDTO> event) {
        log.info("Send event {} to SNS topic {}", event, topic);
        String traceId = TracingUtil.createTraceId(awsCloudProperties.getTracing().getXRay().isEnabled());
        event.setTraceId(traceId);
        notificationMessagingTemplate.convertAndSend(topic, event);
    }
}
