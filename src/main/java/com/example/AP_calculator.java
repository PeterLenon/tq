package com.example;

public class AP_calculator {
    AP_calculator() {}

    public double calculate(double interest , double periods){
        double i = interest/100;
        return (i * Math.pow((1+i), periods))/ (Math.pow((1+i), periods) -1);
    }

}
