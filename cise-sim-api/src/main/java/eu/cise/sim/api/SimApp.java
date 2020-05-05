package eu.cise.sim.api;

import com.codahale.metrics.health.HealthCheck;
import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.sim.AppContext;
import eu.cise.sim.DefaultAppContext;
import eu.cise.sim.api.helpers.CrossOriginSupport;
import eu.cise.sim.api.rest.MessageResource;
import eu.cise.sim.api.rest.TemplateResource;
import eu.cise.sim.api.rest.UIServiceResource;
import eu.cise.sim.api.rest.UiMessageResource;
import eu.cise.sim.api.soap.CISEMessageServiceSoapImplDefault;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;

public class SimApp extends Application<SimConf> {

    // JAX-WS Bundle
    private final JAXWSBundle<Object> jaxwsBundle = new JAXWSBundle<>("/api/soap");

    @Override
    public String getName() {
        return "CISE Sim";
    }

    @Override
    public void initialize(final Bootstrap<SimConf> bootstrap) {
        bootstrap.addBundle(jaxwsBundle);
        bootstrap.addBundle(
            new ConfiguredAssetsBundle(
                "/assets/",
                "/",
                "index.html")); // imply redirect from root ?
    }

    @Override
    public void run(final SimConf conf, final Environment environment) {
        CrossOriginSupport.setup(environment);

        environment.jersey().setUrlPattern("/api");

        AppContext appCtx = new DefaultAppContext();

        MessageAPI messageAPI = new DefaultMessageAPI(
                appCtx.makeMessageProcessor(),
                appCtx.makeMessageStorage(),
                appCtx.makeTemplateLoader(),
                appCtx.getXmlMapper(),
                appCtx.getPrettyNotValidatingXmlMapper());

        TemplateAPI templateAPI = new TemplateAPI(
                appCtx.makeMessageProcessor(),
                appCtx.makeTemplateLoader(),
                appCtx.getXmlMapper(),
                appCtx.getPrettyNotValidatingXmlMapper());

        environment.healthChecks().register("noop", new HealthCheck() {
            @Override
            protected Result check() {
                return Result.healthy();
            }
        });

        environment.jersey().register(new UiMessageResource(messageAPI));
        environment.jersey().register(new UIServiceResource(appCtx.makeEmuConfig()));
        environment.jersey().register(new MessageResource(messageAPI, appCtx.makeMessageStorage()));
        environment.jersey().register(new TemplateResource(messageAPI, templateAPI));

        CISEMessageServiceSoapImpl ciseMessageServiceSoap = new CISEMessageServiceSoapImplDefault(
                messageAPI,
                appCtx.getPrettyNotValidatingXmlMapper());

        // WSDL first service using server side JAX-WS handler and CXF logging interceptors
        jaxwsBundle.publishEndpoint(new EndpointBuilder("messages", ciseMessageServiceSoap));

        environment.lifecycle().addLifeCycleListener(new Listener() {
            @Override
            public void lifeCycleStarting(LifeCycle lifeCycle) {
            }

            @Override
            public void lifeCycleStarted(LifeCycle lifeCycle) {
                System.out.println("== API Server started ===========================");
            }

            @Override
            public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {
            }

            @Override
            public void lifeCycleStopping(LifeCycle lifeCycle) {
            }

            @Override
            public void lifeCycleStopped(LifeCycle lifeCycle) {
            }
        });

    }

    public static void main(final String[] args) {
        try {
            new SimApp().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}