package com.example.calculator.server;


import com.example.calculator.CalculatorWs;

import javax.jws.WebService;

@WebService(endpointInterface = "com.example.calculator.CalculatorWs")
public class CalculatorServerJavaImpl implements CalculatorWs {
    @Override
    public int multiply(int arg0, int arg1) {
        return 0;
    }

    @Override
    public int sum(int arg0, int arg1) {
        return 0;
    }
}
