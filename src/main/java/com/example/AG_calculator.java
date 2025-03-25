package com.example;

public class AG_calculator {
    AG_calculator(){}

    public double calculate(double interest, double periods){
        double i = interest/100;
        return Math.pow(i, -1) - (periods/ (Math.pow(1+i, periods) -1));
    }
}
