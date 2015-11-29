package com.example.soap.client;


import com.example.calculator.CalculatorService;
import com.example.calculator.CalculatorWs;
import com.example.security.ObjectFactory;
import com.example.security.SecurityHeader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CalculatorWSJavaClient {

    private final ThreadLocal<CalculatorWs> threadLocal;
    private final CalculatorService service;
    private final String urlString;

    private static final WebServiceClient wsc = CalculatorService.class.getAnnotation(WebServiceClient.class);
    private static final QName qName = new QName(wsc.targetNamespace(), wsc.name());
    private static final URL wsdlLocation = CalculatorService.class.getResource(wsc.wsdlLocation());

    private String userName = null;
    private String password = null;



    public CalculatorWSJavaClient(String urlString, String userName, String password) {
        this(new CalculatorService(wsdlLocation, qName), urlString, userName, password);
    }


    public CalculatorWSJavaClient(CalculatorService service, String urlString, String userName, String password) {
        this.service = service;
        this.urlString = urlString;
        this.userName = userName;
        this.password = password;
        threadLocal = new ThreadLocal<>();
    }

    private CalculatorWs getCalculatorWs() {
        final String userName = this.userName;
        final String password = this.password;

        CalculatorWs CalculatorWs = threadLocal.get();

        if (CalculatorWs == null) {
            CalculatorWs = service.getCalculatorPort();

            List<Handler> handlersList = new ArrayList<>();
            handlersList.add(new SOAPHandler<SOAPMessageContext>() {

                @Override
                public boolean handleMessage(SOAPMessageContext soapMessageContext) {
                    Boolean isRequest = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                    if (isRequest) {
                        try {
                            ObjectFactory objectFactory = new ObjectFactory();


                            SecurityHeader securityHeader = objectFactory.createSecurityHeader();
                            securityHeader.setApplicationCode("App1");

                            securityHeader.setClientId(userName);

                            securityHeader.setClientPassword(password);
                            final Marshaller marshaller = JAXBContext.newInstance(SecurityHeader.class).createMarshaller();
                            final SOAPHeader soapHeader = soapMessageContext.getMessage().getSOAPPart().getEnvelope().addHeader();

                            marshaller.marshal(securityHeader, soapHeader);

                        } catch (final Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return true;
                }

                @Override
                public boolean handleFault(SOAPMessageContext soapMessageContext) {
                    return false;
                }

                @Override
                public void close(MessageContext messageContext) {

                }

                @Override
                public Set<QName> getHeaders() {
                    return null;
                }
            });

            Binding binding = ((BindingProvider) CalculatorWs).getBinding();
            binding.setHandlerChain(handlersList);


            ((BindingProvider) CalculatorWs).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);

            threadLocal.set(CalculatorWs);
        }
        return CalculatorWs;
    }


    public int multiply(int arg0, int arg1) {
        CalculatorWs calculatorWs = getCalculatorWs();

        return calculatorWs.multiply(arg0, arg1);

    }

    public int sum(int arg0, int arg1) {
        CalculatorWs calculatorWs = getCalculatorWs();

        return calculatorWs.sum(arg0, arg1);

    }

    public static void main(String[] args) {

        CalculatorWSJavaClient calculatorWSJavaClient = new CalculatorWSJavaClient("http://localhost:8080/javaCalculator", "username", "password");

        System.out.println("Sum of 2 and 3 is: " + calculatorWSJavaClient.sum(2, 3));
        System.out.println("Multiplication of 2 and 3 is: " + calculatorWSJavaClient.multiply(2, 3));

    }

}
