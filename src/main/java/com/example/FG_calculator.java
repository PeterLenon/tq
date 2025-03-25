package com.example;

public class FG_calculator {
    FG_calculator() {}

    public double calculate(double interest, double periods) {
        double i = interest/100;
        PG_calculator pg_calculator = new PG_calculator();
        FP_calculator fp_calculator = new FP_calculator();
        return pg_calculator.calculate(interest, periods) * fp_calculator.calculate(interest, periods);
    }
}
