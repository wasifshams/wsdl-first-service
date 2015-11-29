package com.example.soap.client

import javax.xml.namespace.QName
import javax.xml.ws.{Binding, BindingProvider, WebServiceClient}

import com.example.calculator.{CalculatorService, CalculatorWs}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer


class CalculatorWSScalaClient(val service: CalculatorService, val urlString: String, val userName: String, val password: String) {

  def this(urlString: String, userName: String, password: String,
           wsc: WebServiceClient = classOf[CalculatorService] getAnnotation classOf[WebServiceClient]) {
    this(new CalculatorService(
      classOf[CalculatorService].getResource(wsc wsdlLocation), new QName(wsc targetNamespace, wsc name)),
      urlString, userName, password)
  }

  val threadLocal: ThreadLocal[CalculatorWs] = new ThreadLocal[CalculatorWs]

  def getCalculatorWs: CalculatorWs = {
    var CalculatorWs: CalculatorWs = threadLocal get

    if (CalculatorWs == null) {
      CalculatorWs = service getCalculatorPort

      val binding: Binding = CalculatorWs.asInstanceOf[BindingProvider].getBinding

      val handlersList: List[CalculatorClientSoapHandler] = List(new CalculatorClientSoapHandler(userName, password, "App1"))
      binding.setHandlerChain(handlersList.to[ListBuffer])


      CalculatorWs.asInstanceOf[BindingProvider].getRequestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString)
      threadLocal set CalculatorWs

    }

    CalculatorWs
  }


  def multiply(arg0:Int,arg1:Int): Int = {
    val calculatorWs: CalculatorWs = getCalculatorWs
    calculatorWs multiply(arg0,arg1)
  }

  def sum(arg0:Int,arg1:Int): Int = {
    val calculatorWs: CalculatorWs = getCalculatorWs
    calculatorWs sum(arg0,arg1)
  }



}

object ScalaApp{
  def main(args: Array[String]) {
    val calculatorWSClient: CalculatorWSScalaClient = new CalculatorWSScalaClient("http://localhost:8080/scalaCalculator", "username", "password")
    println (calculatorWSClient multiply(2,3))
    println (calculatorWSClient sum(2,3))
  }
}
