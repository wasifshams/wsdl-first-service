
package com.example.calculator;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "CalculatorWs", targetNamespace = "http://calculator.example.com")
@XmlSeeAlso({
    com.example.calculator.ObjectFactory.class,
    com.example.security.ObjectFactory.class
})
public interface CalculatorWs {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "http://calculator.example.com")
    @RequestWrapper(localName = "multiply", targetNamespace = "http://calculator.example.com", className = "com.example.calculator.Multiply")
    @ResponseWrapper(localName = "multiplyResponse", targetNamespace = "http://calculator.example.com", className = "com.example.calculator.MultiplyResponse")
    public int multiply(
        @WebParam(name = "arg0", targetNamespace = "http://calculator.example.com")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "http://calculator.example.com")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "http://calculator.example.com")
    @RequestWrapper(localName = "sum", targetNamespace = "http://calculator.example.com", className = "com.example.calculator.Sum")
    @ResponseWrapper(localName = "sumResponse", targetNamespace = "http://calculator.example.com", className = "com.example.calculator.SumResponse")
    public int sum(
        @WebParam(name = "arg0", targetNamespace = "http://calculator.example.com")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "http://calculator.example.com")
        int arg1);

}
