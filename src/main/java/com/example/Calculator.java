package com.example;

public class Calculator {
    Calculator(){}

    public double AP_factor(double interest, double periods){
        double i = interest/100;
        return (i * Math.pow((1+i), periods))/ (Math.pow((1+i), periods) -1);
    }

    public double AF_factor(double interest, double periods){
        double i = interest/100;
        return  i / (Math.pow((1+i), periods) -1);
    }

    public double AG_factor(double interest, double periods){
        double i = interest/100;
        return Math.pow(i, -1) - (periods/ (Math.pow(1+i, periods) -1));
    }

    public double PF_factor(double interest, double periods){
        return Math.pow((1+interest/100), -periods);
    }

    public double PG_factor(double interest, double periods){
        double i = interest/100;
        return Math.pow(i,-1) * ( ( Math.pow(1+i, periods) -1 ) / ( i*Math.pow(1+i, periods) ) - ( periods*Math.pow(1+i, -periods) ));
    }

    public double PA_factor(double interest, double periods){
        double i = interest/100.0;
        return (Math.pow((1+i), periods) -1) / (i * Math.pow((1+i), periods));
    }

    public double FP_factor(double interest, double periods){
        return Math.pow((1 + interest/100), periods);
    }

    public double FG_factor(double interest, double periods){
        double i = interest/100;
        return PG_factor(interest, periods) * FP_factor(interest, periods);
    }
    public double FA_factor(double interest, double periods){
        double i = interest/100;
        return  (Math.pow((1+i), periods) -1) / i ;
    }
}
