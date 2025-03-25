package com.example;

public class PG_calculator {
    PG_calculator() {}

    public double calculate(double interest, double periods){
        double i = interest/100;
        return Math.pow(i,-1) * ( ( Math.pow(1+i, periods) -1 ) / ( i*Math.pow(1+i, periods) ) - ( periods*Math.pow(1+i, -periods) ));
    }
}
