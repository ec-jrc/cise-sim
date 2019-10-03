package eu.cise.emulator.api.resources;

import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.api.*;
import eu.cise.emulator.templates.Template;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetTemplatesTest {

    private static TemplateAPI templateAPI = mock(TemplateAPI.class);
    private static MessageAPI messageAPI = mock(MessageAPI.class);
    private static EmuConfig emuConfig = mock(EmuConfig.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TemplateResource(messageAPI, templateAPI, emuConfig))
            .bootstrapLogging(false)
            .build();

    private Template expectedTemplate;

    @Before
    public void before() {
        expectedTemplate = new Template("template-id-#1");

    }

    @After
    public void after() {
        reset(templateAPI);
        reset(messageAPI);
    }

    @Test
    public void it_checks_the_route_to_get_templates_exists() {
        List<Template> expectedTemplateList = asList(new Template("id1"), new Template("id1"));
        when(templateAPI.getTemplates()).thenReturn(new TemplateListResponse(expectedTemplateList));
        Response response = resources.target("/api/templates").request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void it_returns_a_template_list_for_templateListResponse_is_ok() {
        List<Template> expectedTemplateList = asList(new Template("id1"), new Template("id1"));

        when(templateAPI.getTemplates()).thenReturn(new TemplateListResponse(expectedTemplateList));

        Response response = resources.target("/api/templates").request().get();

        List<Template> actualTemplateList = response.readEntity(genericTemplateList());

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_an_api_for_templateListResponse_is_ko() {
        when(templateAPI.getTemplates()).thenReturn(new TemplateListResponse.KO("exception"));

        Response response = resources.target("/api/templates").request().get();

        APIError actualApiError = response.readEntity(APIError.class);

        APIError expectedApiError = new APIError("exception");

        assertThat(actualApiError).isEqualTo(expectedApiError);
    }

    @Test
    public void it_checks_HTTP_code_to_server_error_when_templateListResponse_is_ko() {
        when(templateAPI.getTemplates()).thenReturn(new TemplateListResponse.KO("exception"));

        Response response = resources.target("/api/templates").request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }


    private GenericType<List<Template>> genericTemplateList() {
        return new GenericType<List<Template>>() {
        };
    }
}
