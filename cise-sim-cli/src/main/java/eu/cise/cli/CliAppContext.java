package eu.cise.cli;

import eu.cise.cli.repository.FileMessagePersistence;
import eu.cise.dispatcher.DispatcherFactory;
import eu.cise.signature.SignatureService;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.engine.*;
import eu.cise.sim.io.MessagePersistence;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

public class CliAppContext {

    private final SimConfig simConfig;
    private final XmlMapper xmlMapper;
    private final SignatureService signatureService;

    public CliAppContext() {
        this.simConfig = ConfigFactory.create(SimConfig.class);
        this.xmlMapper = new DefaultXmlMapper.NotValidating();
        this.signatureService = newSignatureService(xmlMapper)
                .withKeyStoreName(simConfig.keyStoreFileName())
                .withKeyStorePassword(simConfig.keyStorePassword())
                .withPrivateKeyAlias(simConfig.privateKeyAlias())
                .withPrivateKeyPassword(simConfig.privateKeyPassword())
                .build();
    }

    public XmlMapper getXmlMapper() {
        return this.xmlMapper;
    }

    public SimConfig getSimConfig() {
        return this.simConfig;
    }

    public SimEngine makeSimEngine() {
        return new DefaultSimEngine(signatureService, makeDispatcher(), simConfig);
    }

    public MessageProcessor makeMessageProcessor() {
        return new DefaultMessageProcessor(makeSimEngine(), makeMessagePersistence());
    }

    public MessagePersistence makeMessagePersistence() {
        return new FileMessagePersistence(xmlMapper, simConfig.messageHistoryDir());
    }

    public Dispatcher makeDispatcher() {
        DispatcherFactory dispatcherFactory = new DispatcherFactory();
        return dispatcherFactory.getDispatcher(this.simConfig.destinationProtocol(), this.xmlMapper);
    }

    public MessageLoader makeMessageLoader() {
        return new MessageLoader(xmlMapper);
    }
}
