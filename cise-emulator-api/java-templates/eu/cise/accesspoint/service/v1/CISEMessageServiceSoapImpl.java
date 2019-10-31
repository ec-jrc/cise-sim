package eu.cise.accesspoint.service.v1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.9
 * 2019-10-31T18:05:17.826+01:00
 * Generated source version: 3.2.9
 *
 */
@WebService(targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", name = "CISEMessageServiceSoapImpl")
@XmlSeeAlso({eu.cise.servicemodel.v1.service.ObjectFactory.class, eu.cise.servicemodel.v1.authority.ObjectFactory.class, ObjectFactory.class, eu.cise.servicemodel.v1.message.ObjectFactory.class})
public interface CISEMessageServiceSoapImpl {

    @WebMethod
    @RequestWrapper(localName = "send", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", className = "eu.cise.accesspoint.service.v1.Send")
    @ResponseWrapper(localName = "sendResponse", targetNamespace = "http://www.cise.eu/accesspoint/service/v1/", className = "eu.cise.accesspoint.service.v1.SendResponse")
    @WebResult(name = "return", targetNamespace = "")
    public eu.cise.servicemodel.v1.message.Acknowledgement send(
        @WebParam(name = "message", targetNamespace = "")
        eu.cise.servicemodel.v1.message.Message message
    );
}
