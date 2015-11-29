package com.example.calculator.server

import javax.annotation.Resource
import javax.jws.WebService
import javax.xml.ws.WebServiceContext

import com.example.calculator.CalculatorWs


@WebService(endpointInterface = "com.example.calculator.CalculatorWs")
class CalculatorServerScalaImpl extends CalculatorWs {

  override def multiply(arg0: Int, arg1: Int): Int = {
    arg0 * arg1
  }

  override def sum(arg0: Int, arg1: Int): Int = {
    arg0 + arg1
  }

}
