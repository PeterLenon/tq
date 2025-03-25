package com.example;

public class FA_calculator {
    FA_calculator(){}

    public double calculate(double interest, double periods){
        double i = interest/100;
        return  (Math.pow((1+i), periods) -1) / i ;
    }
}
