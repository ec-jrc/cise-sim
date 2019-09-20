package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefaultMessageResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageResource.class);
    private final MessageAPI messageAPI;


    public DefaultMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;

    }

    @POST
    @Path("/messages")
    public Response send(JsonNode msgWithParams) {
        LOGGER.info("messageCreate with param: {}", msgWithParams);

        JsonNode resultMessage = messageAPI.send(msgWithParams);
        Response.StatusType resultStatusType = (resultMessage.at("/status").textValue().contains("SUCCESS") ? Response.Status.CREATED : Response.Status.BAD_REQUEST);
        return Response
                .status(resultStatusType)
                .entity(resultMessage)
                .build();
    }

}
