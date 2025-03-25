package com.example;

public class PF_calculator {
    public double calculate(double interest , double periods){
        return Math.pow((1+interest/100), -periods);
    }
}
