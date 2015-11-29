package com.example.soap.client

import java.util
import javax.xml.bind.{JAXBContext, Marshaller}
import javax.xml.namespace.QName
import javax.xml.soap.SOAPHeader
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPHandler, SOAPMessageContext}

import com.example.security.{ObjectFactory, SecurityHeader}


class CalculatorClientSoapHandler(val userName:String, val password:String, val applicationCode:String) extends SOAPHandler[SOAPMessageContext]{
  override def getHeaders: util.Set[QName] = null

  override def handleMessage(soapMessageContext: SOAPMessageContext): Boolean = {
    val isRequest: Boolean = soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY).asInstanceOf[Boolean]


    if(isRequest){
      val objectFactory: ObjectFactory = new ObjectFactory


      val securityHeader: SecurityHeader = objectFactory.createSecurityHeader
      securityHeader.setApplicationCode(applicationCode)
      securityHeader.setClientId(userName)
      securityHeader.setClientPassword(password)
      val marshaller: Marshaller = JAXBContext newInstance classOf[SecurityHeader] createMarshaller
      val soapHeader: SOAPHeader = soapMessageContext getMessage() getSOAPPart() getEnvelope() addHeader()

      marshaller.marshal(securityHeader, soapHeader)

    }

    isRequest
  }

  override def close(context: MessageContext): Unit ={}

  override def handleFault(context: SOAPMessageContext): Boolean = false
}
