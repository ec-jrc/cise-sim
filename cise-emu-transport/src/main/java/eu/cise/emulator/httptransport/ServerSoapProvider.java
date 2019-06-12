package eu.cise.emulator.httptransport;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class ServerSoapProvider implements MessageBodyReader<Message> {
    private static final ServerSoapProvider instance = new ServerSoapProvider();




    private ServerSoapProvider() {

    }
    

    static public ServerSoapProvider getInstance(){
        return instance;
    }


    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Message.class);
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType,                         Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {
        Message cReturn= new Acknowledgement();
        StringBuffer inXMLcontent=new StringBuffer();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            inXMLcontent.append(br.readLine());
        }catch (Exception e){
            if (e instanceof IOException ){throw  (IOException) e ;};
            if (e instanceof WebApplicationException ){throw  (WebApplicationException) e ;};
        }

        return cReturn;
    }
}