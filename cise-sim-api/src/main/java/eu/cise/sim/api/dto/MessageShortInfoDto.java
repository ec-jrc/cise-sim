package eu.cise.sim.api.dto;

import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class MessageShortInfoDto implements Serializable {

    private static final long serialVersionUID = 2601968705651574024L;
    private static final XmlMapper XML_MAPPER = new DefaultXmlMapper.PrettyNotValidating();

    private final String id;
    private final String dateTime;
    private final String messageType;
    private final String serviceType;
    private final boolean isSent;

    private MessageShortInfoDto(String id, String dateTime, String messageType, String serviceType, boolean isSent) {
        this.id = id;
        this.dateTime = dateTime;
        this.messageType = messageType;
        this.serviceType = serviceType;
        this.isSent = isSent;
    }

    public static MessageShortInfoDto getInstance(String message, boolean isSent) throws IllegalArgumentException {

        Message ciseMessage = XML_MAPPER.fromXML(message);

        String  id = ciseMessage.getMessageID();

        // TODO Check if it's better to leave the original info (like 2019-10-16T14:04:20.673Z)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String  dateTime = formatter.format(ciseMessage.getCreationDateTime().toGregorianCalendar().toZonedDateTime());

        MessageTypeEnum messageType = MessageTypeEnum.valueOf(ciseMessage);
        String messageTypeName = messageType.name();

        String  serviceType = "";
        if (messageType != MessageTypeEnum.ACKNOWLEDGEMENT) {
            serviceType = ciseMessage.getSender().getServiceType().value();
            if (StringUtils.isEmpty(serviceType)) {
                throw new IllegalArgumentException("Service type is empty");
            }
        }

        MessageShortInfoDto instance = new MessageShortInfoDto(id, dateTime, messageTypeName, serviceType, isSent);
        check(instance);

        return instance;
    }

    private static void check(MessageShortInfoDto instance) throws IllegalArgumentException {

        if (StringUtils.isEmpty(instance.getId())) {
            throw new IllegalArgumentException("MessageID is empty");
        }

        if (StringUtils.isEmpty(instance.getDateTime())) {
            throw new IllegalArgumentException("CreationDateTime is empty");
        }

        if (StringUtils.isEmpty(instance.getMessageType())) {
            throw new IllegalArgumentException("Type of Message is empty");
        }
    }

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public boolean isSent() {
        return isSent;
    }
}
