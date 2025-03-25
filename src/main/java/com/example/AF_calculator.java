package com.example;

public class AF_calculator {
    AF_calculator(){}
    public double calculate(double interest , double periods){
        double i = interest/100;
        return  i / (Math.pow((1+i), periods) -1);
    }
}
