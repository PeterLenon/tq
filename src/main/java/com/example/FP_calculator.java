package com.example;

public class FP_calculator {
    public double calculate(double interest , double period){
        return Math.pow((1 + interest/100), period);
    }
}
