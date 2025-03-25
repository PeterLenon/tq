package com.example;

public class PA_calculator {
    PA_calculator(){}

    public double calculate(double interest, double periods){
        double i = interest/100.0;
        return (Math.pow((1+i), periods) -1) / (i * Math.pow((1+i), periods));
    }
}
