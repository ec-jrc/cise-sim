
package eu.cise.dispatcher.soap;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebServiceClient(
        name = "CISEMessageService",
        targetNamespace = "http://www.cise.eu/accesspoint/service/v1/",
        wsdlLocation = "META-INF/wsdl/CISEMessageService.wsdl")
//@SOAPBinding(
//        style = SOAPBinding.Style.DOCUMENT,
//        parameterStyle = SOAPBinding.ParameterStyle.WRAPPED,
//        use = SOAPBinding.Use.LITERAL)
//@InInterceptors(interceptors = {"java.eu.cise.dispatcher.soap.SoapInterceptor"})
//@OutInterceptors(interceptors = {"java.eu.cise.dispatcher.soap.SoapInterceptor"})
@HandlerChain(file = "handlers.xml")

public class CISEMessageService
        extends Service {

    private static final URL CISEMESSAGESERVICE_WSDL_LOCATION;
    private static final WebServiceException CISEMESSAGESERVICE_EXCEPTION;
    private static final QName CISEMESSAGESERVICE_QNAME = new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            URL baseUrl = CISEMessageService.class.getClassLoader().getResource(".");
            url = new URL(baseUrl, "META-INF/wsdl/CISEMessageService.wsdl");


        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CISEMESSAGESERVICE_WSDL_LOCATION = url;
        CISEMESSAGESERVICE_EXCEPTION = e;
    }

    public CISEMessageService() {
        super(getWsdlLocation(), CISEMESSAGESERVICE_QNAME);
    }

    public CISEMessageService(WebServiceFeature... features) {
        super(getWsdlLocation(), CISEMESSAGESERVICE_QNAME, features);
    }

    public CISEMessageService(URL wsdlLocation) {
        super(wsdlLocation, CISEMESSAGESERVICE_QNAME);
    }

    public CISEMessageService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CISEMESSAGESERVICE_QNAME, features);
    }

    public CISEMessageService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CISEMessageService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns CISEMessageServiceSoapImpl
     */
    @WebEndpoint(name = "CISEMessageServiceSoapPort")
    public CISEMessageServiceSoapImpl getCISEMessageServiceSoapPort() {
//        QName serviceName = new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoap");
//        Service s = Service.create(serviceName);
//
//        QName portName = new QName("http://www.cise.eu/accesspoint/service/v1", "CISEMessageServiceSoapPort");
//        s.addPort(portName, "http://schemas.xmlsoap.org/soap/", "http://localhost:8080/app/message");
//
//
//        CISEMessageServiceSoapImpl proxy = s.getPort(new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoapPort"), CISEMessageServiceSoapImpl.class);
//
//
//        Client cxfClient = ClientProxy.getClient(proxy);

        //ValidateInterceptor validInterceptor = new ValidateInterceptor(); 5
        //cxfClient.getInInterceptor().add(validInterceptor); 6

        return super.getPort(new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoapPort"), CISEMessageServiceSoapImpl.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns CISEMessageServiceSoapImpl
     */
    @WebEndpoint(name = "CISEMessageServiceSoapPort")
    public CISEMessageServiceSoapImpl getCISEMessageServiceSoapPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.cise.eu/accesspoint/service/v1/", "CISEMessageServiceSoapPort"), CISEMessageServiceSoapImpl.class, features);
    }

    private static URL getWsdlLocation() {
        if (CISEMESSAGESERVICE_EXCEPTION != null) {
            throw CISEMESSAGESERVICE_EXCEPTION;
        }
        return CISEMESSAGESERVICE_WSDL_LOCATION;
    }

}
