package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CiseEmulatorApi extends Application<CiseEmulatorConf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);

    public static void main(final String[] args) throws Exception {
        new CiseEmulatorApi().run(args);
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorConf> bootstrap) {

    }

    @Override
    public void run(final CiseEmulatorConf configuration, final Environment environment) {


        MessageProcessor messageProcessor= ApplicationContext.makeMessageProcesor();
        MessageAPI messageAPI=new DefaultMessageAPI(messageProcessor);

        LOGGER.info("Registering REST resources with crossOriginSupport");
        CrossOriginSupport.setup(environment);
        environment.jersey().register(new DefaultMessageResource(messageAPI));
    }

    private static class ApplicationContext {
        public static MessageProcessor makeMessageProcesor() {
        return null;
        }
    }
}


