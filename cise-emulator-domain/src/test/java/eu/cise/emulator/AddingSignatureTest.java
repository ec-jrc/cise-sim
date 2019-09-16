package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Push;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddingSignatureTest {

    private EmulatorEngine engine;
    private SignatureService signatureService;
    private Push push;

    @Before
    public void before() {
        EmuConfig config = ConfigFactory.create(EmuConfig.class);
        signatureService = mock(SignatureService.class);
        engine = new DefaultEmulatorEngine(signatureService, config);
        push = newPush().sender(newService()).build();
    }

    @Test
    public void it_signs_the_message() {
        engine.prepare(push, params());

        verify(signatureService).sign(push);
    }

    private SendParam params() {
        return new SendParam(false, "msgId", "corrId");
    }
}
