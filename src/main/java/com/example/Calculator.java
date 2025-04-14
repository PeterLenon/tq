package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Calculator {
    private final HashMap<Integer, HashMap<Integer, Double>> lifespan_to_year_rate = new HashMap<Integer, HashMap<Integer, Double>>();

    Calculator(){
        ArrayList<Integer> lifespans = new ArrayList<>(Arrays.asList(3, 5, 7, 10, 15, 20));
        for(int lifespan : lifespans){
            if(lifespan == 3){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 33.33);
                year_to_rate.put(2, 44.45);
                year_to_rate.put(3, 14.81);
                year_to_rate.put(4, 7.41);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }else if(lifespan == 5){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 20.00);
                year_to_rate.put(2, 32.00);
                year_to_rate.put(3, 19.20);
                year_to_rate.put(4, 11.52);
                year_to_rate.put(5, 11.52);
                year_to_rate.put(6, 5.76);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }else if(lifespan == 7){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 14.29);
                year_to_rate.put(2, 24.49);
                year_to_rate.put(3, 17.49);
                year_to_rate.put(4, 12.49);
                year_to_rate.put(5, 8.93);
                year_to_rate.put(6, 8.92);
                year_to_rate.put(7, 8.93);
                year_to_rate.put(8, 4.46);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }else if (lifespan == 10){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 10.00);
                year_to_rate.put(2, 18.00);
                year_to_rate.put(3, 14.40);
                year_to_rate.put(4, 11.52);
                year_to_rate.put(5, 9.22);
                year_to_rate.put(6, 7.37);
                year_to_rate.put(7, 6.55);
                year_to_rate.put(8, 6.55);
                year_to_rate.put(9, 6.56);
                year_to_rate.put(10, 6.55);
                year_to_rate.put(11, 3.28);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }else if (lifespan == 15){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 5.00);
                year_to_rate.put(2, 9.50);
                year_to_rate.put(3, 8.55);
                year_to_rate.put(4, 7.70);
                year_to_rate.put(5, 6.93);
                year_to_rate.put(6, 6.23);
                year_to_rate.put(7, 5.90);
                year_to_rate.put(8, 5.90);
                year_to_rate.put(9, 5.91);
                year_to_rate.put(10, 5.90);
                year_to_rate.put(11, 5.91);
                year_to_rate.put(12, 5.90);
                year_to_rate.put(13, 5.91);
                year_to_rate.put(14, 5.90);
                year_to_rate.put(15, 5.91);
                year_to_rate.put(16, 2.95);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }else if (lifespan == 20){
                HashMap<Integer, Double> year_to_rate = new HashMap<>();
                year_to_rate.put(1, 3.75);
                year_to_rate.put(2, 7.22);
                year_to_rate.put(3, 6.68);
                year_to_rate.put(4, 6.18);
                year_to_rate.put(5, 5.71);
                year_to_rate.put(6, 5.29);
                year_to_rate.put(7, 4.89);
                year_to_rate.put(8, 4.52);
                year_to_rate.put(9, 4.46);
                year_to_rate.put(10, 4.46);
                year_to_rate.put(11, 4.46);
                year_to_rate.put(12, 4.46);
                year_to_rate.put(13, 4.46);
                year_to_rate.put(14, 4.46);
                year_to_rate.put(15, 4.46);
                year_to_rate.put(16, 4.46);
                year_to_rate.put(17, 4.46);
                year_to_rate.put(18, 4.46);
                year_to_rate.put(19, 4.46);
                year_to_rate.put(20, 4.46);
                year_to_rate.put(21, 2.23);
                lifespan_to_year_rate.put(lifespan, year_to_rate);
            }
        }
    }

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

    public double straight_line_fixed_depreciation_rate(double lifespan){
        return Math.pow(lifespan, -1);
    }

    public double double_declining_fixed_depreciation_rate(double lifespan){
        return  2 * Math.pow(lifespan, -1);
    }

    public double straight_line_annual_depreciation_rate(double lifespan){
        return Math.pow(lifespan, -1);
    }

    public double double_declining_annual_depreciation_rate(double lifespan, double year){
        double fixed_depreciation = double_declining_fixed_depreciation_rate(lifespan);
        return fixed_depreciation * Math.pow(1 - fixed_depreciation, year -1 );
    }

    public double double_declining_annual_depreciation(double initial_cost,double lifespan,  double year){
        double prev_year_book_value = double_declining_book_value(initial_cost, lifespan, year - 1);
        double fixed_depreciation = double_declining_fixed_depreciation_rate(lifespan);
        return fixed_depreciation * prev_year_book_value;
    }

    public double double_declining_book_value(double initial_cost, double lifespan, double year){
        double fixed_depreciation = double_declining_fixed_depreciation_rate(lifespan);
        return initial_cost * Math.pow(1 - fixed_depreciation, year);
    }

    public double straight_line_annual_depreciation(double initial_cost, double salvage_value, double lifespan){
        return (initial_cost - salvage_value) / lifespan;
    }

    public double straight_line_book_value(double initial_cost, double annual_dep, double year){
        return initial_cost - year * annual_dep;
    }

    public double MACRS_depreciation_rate(int lifespan, int year){
        return lifespan_to_year_rate.get(lifespan).get(year);
    }

    public double MACRS_annual_depreciation(double initial_cost, int lifespan, int year){
        double depreciation = MACRS_depreciation_rate(lifespan, year)/100;
        return initial_cost * depreciation;
    }

    public double MACRS_book_value(double initial_cost, int lifespan, int year){
        double book_value = initial_cost;
        for(int currentYear = 1; currentYear <= year; currentYear++){
            book_value -= MACRS_annual_depreciation(initial_cost, lifespan, currentYear);
        }
        return book_value;
    }

    public double declining_balance_annual_depreciation(double initial_cost, double stated_fixed_rate, int year){
        double prev_year_book_value = declining_balance_book_value(initial_cost, stated_fixed_rate, year -1 );
        return (stated_fixed_rate/100) * prev_year_book_value;
    }

    public double declining_balance_book_value(double initial_cost, double stated_fixed_rate, int year){
        return initial_cost * Math.pow(1 - (stated_fixed_rate/100), year);
    }
}
