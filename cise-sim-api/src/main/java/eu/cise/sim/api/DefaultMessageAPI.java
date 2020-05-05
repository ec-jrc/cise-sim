package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.exceptions.InvalidMessageSignatureEx;
import eu.cise.signature.exceptions.SigningCACertInvalidSignatureEx;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.sim.api.dto.MessageApiDto;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.api.helpers.SendParamsReader;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.NullSenderEx;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.sim.utils.Pair;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageAPI implements MessageAPI {

    private final Logger logger = LoggerFactory.getLogger(MessageAPI.class);

    private final MessageStorage<MessageApiDto> messageStorage;
    private final MessageStorage<MessageShortInfoDto> historyMessageStorage;
    private final MessageProcessor engineMessageProcessor;
    private final XmlMapper xmlMapper;
    private final XmlMapper prettyNotValidatingXmlMapper;
    private final TemplateLoader templateLoader;
    private final SynchronousAcknowledgementFactory synchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

    DefaultMessageAPI(MessageProcessor engineMessageProcessor,
                      MessageStorage<MessageApiDto> messageStorage,
                      MessageStorage<MessageShortInfoDto> historyMessageStorage,
                      TemplateLoader templateLoader,
                      XmlMapper xmlMapper,
                      XmlMapper prettyNotValidatingXmlMapper) {

        this.engineMessageProcessor = engineMessageProcessor;
        this.messageStorage = messageStorage;
        this.historyMessageStorage = historyMessageStorage;
        this.xmlMapper = xmlMapper;
        this.prettyNotValidatingXmlMapper = prettyNotValidatingXmlMapper;
        this.templateLoader = templateLoader;
    }

    @Override
    public SendResponse send(String templateId, JsonNode params) {
        logger.debug("send is passed through api templateId: {}, params: {}", templateId, params);

        try {
            Template template = templateLoader.loadTemplate(templateId);
            String xmlContent = template.getTemplateContent();

            Message message = xmlMapper.fromXML(xmlContent);
            SendParam sendParam = new SendParamsReader().extractParams(params);
            Pair<Acknowledgement, Message> sendResponse = engineMessageProcessor.send(message, sendParam);

            // TODO history storage
//            historyMessageStorage.store(MessageShortInfoDto.getInstance(prettyNotValidatingXmlMapper.toXML(sendResponse.getB()), Boolean.TRUE));
//            historyMessageStorage.store(MessageShortInfoDto.getInstance(prettyNotValidatingXmlMapper.toXML(sendResponse.getA()), Boolean.FALSE));
            return new SendResponse.OK(
                new MessageApiDto(
                    prettyNotValidatingXmlMapper.toXML(sendResponse.getA()),
                    prettyNotValidatingXmlMapper.toXML(sendResponse.getB())));

        } catch (Exception e) {
            logger.error("Error sending a message to destination.url", e);
            return new SendResponse.KO(e.getMessage());
        }
    }

    @Override
    public Acknowledgement receive(String content) {
        logger.debug("receive is receiving through api : {}", content.substring(0, 200));
        Message message = new Push();
        try {
            message = prettyNotValidatingXmlMapper.fromXML(content);

            // store the input message and the acknowledgement
            Acknowledgement acknowledgement = engineMessageProcessor.receive(message);

            String acknowledgementXml = prettyNotValidatingXmlMapper.toXML(acknowledgement);
            String messageXml = prettyNotValidatingXmlMapper.toXML(message);

            MessageApiDto messageApiDto = new MessageApiDto(acknowledgementXml, messageXml);
            messageStorage.store(messageApiDto);

            // todo history storage
//            historyMessageStorage.store(MessageShortInfoDto.getInstance(messageXml, Boolean.FALSE));
//            historyMessageStorage.store(MessageShortInfoDto.getInstance(acknowledgementXml, Boolean.TRUE));

            return acknowledgement;


        } catch (InvalidMessageSignatureEx | SigningCACertInvalidSignatureEx eInvalidSignature) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.INVALID_SIGNATURE,
                    "" + eInvalidSignature.getMessage());
        } catch (XmlNotParsableException eXmlMalformed) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.XML_MALFORMED,
                    "" + eXmlMalformed.getMessage());
        } catch (NullSenderEx eSemantic) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.SEMANTIC,
                    "" + eSemantic.getMessage());
        } catch (Exception eAny) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.INTERNAL_ERROR,
                    "Unknown Error : " + eAny.getMessage());
        }


    }

    @Override
    public MessageApiDto getLastStoredMessage() {
        return messageStorage.read();
    }

    public boolean consumeStoredMessage(MessageApiDto storedMessage) {
        //TODO: read the template from fileStorage
        return messageStorage.delete(storedMessage);
    }
}
