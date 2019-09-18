package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CiseEmulatorAPI extends Application<CiseEmulatorDropwizardConf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private static boolean requiredFileConfig;

    public static void main(final String[] args) throws Exception {
        if (args.length > 1 & args[1].contains("/")) requiredFileConfig = true;
        new CiseEmulatorAPI().run(args);
    }

    @Override
    public void initialize(final Bootstrap<CiseEmulatorDropwizardConf> bootstrap) {
        //use boot criteria to allow file instead of resource when path is provided
        if (!requiredFileConfig) {
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
    }

    @Override
    public void run(final CiseEmulatorDropwizardConf configuration, final Environment environment) {
        MessageProcessor messageProcessor = configuration.getMessageProcessor();
        MessageAPI messageAPI = new DefaultMessageAPI(messageProcessor);

        LOGGER.info("Registering REST resources with crossOriginSupport");
        CrossOriginSupport.setup(environment);
        environment.jersey().register(new DefaultMessageResource(messageAPI));
    }


}



