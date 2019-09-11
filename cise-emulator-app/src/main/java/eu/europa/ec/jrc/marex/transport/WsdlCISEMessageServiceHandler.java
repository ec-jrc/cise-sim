package eu.europa.ec.jrc.marex.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;


public class WsdlCISEMessageServiceHandler implements SOAPHandler<SOAPMessageContext> {

    Logger log = LoggerFactory.getLogger(WsdlCISEMessageServiceHandler.class);

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public void close(MessageContext messageContext) {
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outbound) {
            log.info("WsdlCISEMessageServiceHandler server handler - outbound");
        } else {
            log.info("WsdlCISEMessageServiceHandler server handler - inbound");
        }

        return true;
    }

}