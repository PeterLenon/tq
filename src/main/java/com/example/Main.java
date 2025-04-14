package com.example;
import org.apache.commons.cli.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Calculator tq = new Calculator();

    private static String[] preprocessArgs(String[] args) {
        Stack<Character> openBraceStack = new Stack<>();
        Stack<Character> factorStack = new Stack<>();
        for (String arg : args) {
            for (int i = 0; i < arg.length(); i++) {
                char c = arg.charAt(i);
                if(c =='('){
                    if ( i == 0){
                        logger.severe(arg + " is not a well formed argument. No factor amount inserted before the parenthesis");
                        System.exit(1);
                    }else{
                        openBraceStack.push(c);
                    }
                }else if( c == ')') {
                    if (openBraceStack.isEmpty() || factorStack.size() != 2) {
                        if (openBraceStack.isEmpty()) {
                            logger.severe(arg + " this argument has imbalanced parenthesis.");
                        } else if (factorStack.isEmpty()) {
                            logger.severe(arg + " this argument has not enough arguments between the parenthesis");
                        }
                        System.exit(1);
                    }else {
                        openBraceStack.pop();
                        factorStack.clear();
                    }
                }else if(!openBraceStack.isEmpty() && c == ','){
                    factorStack.push(c);
                }
            }
            if (!openBraceStack.isEmpty()) {
                logger.severe(arg + " has more open braces than expected");
                System.exit(1);
            }
            factorStack.clear();
        }
        return args;
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("e", "expression", true, "expression to be evaluated");

        CommandLineParser parser = new DefaultParser();
        double answer = 0;

        try{
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("e")){
                String expression = cmd.getOptionValue("e");
                String[] parts = expression.split("(?=[+-])");
                String[] preprocessedArgs = preprocessArgs(parts);

                for(String preprocessedArg : preprocessedArgs) {
                    answer += process(preprocessedArg);
                }
            }
        }catch (ParseException e){
            logger.severe(e.getMessage());
        }
        logger.info("Final Answer: " + answer);
    }

    private static double process(String expression){
        String express = expression.trim().replace(" ", "");
        int start = -1;
        int end = -1;
        double number = 0;

        ArrayList<String> factors = new ArrayList<>();
        for(int index = 0; index < express.length(); index++){
            char ch = express.charAt(index);
            if(express.indexOf('(') != -1 && ch == '('){
                if(factors.isEmpty()){
                    number = Double.parseDouble(express.substring(0, index));
                }
                start = index;
            }else if(ch == ')'){
                end = index;
                factors.add(express.substring(start, index+1));
            }
        }
        if(start >= end) {
            logger.warning("No factor found in expression: " + express);
            if(express.indexOf('(') == -1 && express.indexOf(')') == -1){
                try{
                    number = Double.parseDouble(express);
                } catch (NumberFormatException e) {
                    logger.severe(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }

        for(String factor : factors){
            Pattern operatorPattern = Pattern.compile("\\(([^,]+),");
            Matcher operatorMatcher = operatorPattern.matcher(factor);
            if (!operatorMatcher.find()) {
                logger.severe(factor + " is not a well formed argument");
                System.exit(1);
            }
            String operator = operatorMatcher.group(1).trim().toLowerCase();
            Pattern interestPattern = Pattern.compile(",([^,]+),");
            Matcher interestMatcher = interestPattern.matcher(factor);
            if (!interestMatcher.find()) {
                logger.severe(factor + " is not a well formed argument");
                System.exit(1);
            }
            double interest = Double.parseDouble(interestMatcher.group(1).trim());

            Pattern periodPattern = Pattern.compile(",([^,)]+)\\)");
            Matcher periodMatcher = periodPattern.matcher(factor);
            if (!periodMatcher.find()) {
                logger.severe(factor + " is not a well formed argument");
                System.exit(1);
            }
            double periods = Double.parseDouble(periodMatcher.group(1).trim());

            switch (operator){
                case "a/p":
                    number = number * tq.AP_factor(interest, periods);
                    break;
                case "a/f":
                    number = number * tq.AF_factor(interest, periods);
                    break;
                case "a/g":
                    number = number * tq.AG_factor(interest, periods);
                    break;
                case "p/f":
                    number = number * tq.PF_factor(interest, periods);
                    break;
                case "p/g":
                    number = number * tq.PG_factor(interest, periods);
                    break;
                case "p/a":
                    number = number *  tq.PA_factor(interest, periods);
                    break;
                case "f/p":
                    number = number * tq.FP_factor(interest, periods);
                    break;
                case "f/g":
                    number = number *  tq.FG_factor(interest, periods);
                    break;
                case "f/a":
                    number = number * tq.FA_factor(interest, periods);
                    break;
                case "macrs_ar":
                    logger.info("macrs_ar format : 1(`macrs_ar`, lifespan, year)");
                    int lifespan = (int) interest;
                    int year = (int) periods;
                    number = tq.MACRS_depreciation_rate(lifespan, year);
                    break;
                case "macrs_ad":
                    logger.info("macrs_ad format : initial cost(`macrs_ad`, lifespan, year)");
                    int lifespan2 = (int) interest;
                    int year2 = (int) periods;
                    double initial_cost = number;
                    number = 1 * tq.MACRS_annual_depreciation(initial_cost, lifespan2, year2);
                    break;
                case "macrs_bv":
                    logger.info("macrs_bv format : initial cost(`macrs_bv`, lifespan, year)");
                    int lifespan3 = (int) interest;
                    int year3 = (int) periods;
                    double initial_cost3 = number;
                    number = tq.MACRS_book_value(initial_cost3, lifespan3, year3);
                    break;
                case "sl_fr":
                    logger.info("sl_fr format : 1(`sl_fr`, lifespan, 0)");
                    int lifespan4 = (int) interest;
                    number = tq.straight_line_fixed_depreciation_rate(lifespan4);
                    break;
                case "sl_ar":
                    logger.info("sl_ad format : 1(`sl_ar`, lifespan, 0)");
                    int lifespan5 = (int) interest;
                    number = tq.straight_line_annual_depreciation_rate(lifespan5);
                    break;
                case "sl_ad":
                    logger.info("sl_ad format : initial cost(`sl_ad`, salvage value, lifespan)");
                    int salvage_value = (int) interest;
                    int lifespan6 = (int) periods;
                    double initial_cost6 = number;
                    number = tq.straight_line_annual_depreciation(initial_cost6, salvage_value, lifespan6);
                    break;
                case "sl_bv" :
                    logger.info("sl_bv format : initial cost(`sl_bv`, annual depreciation amount, year)");
                    double initial_cost7 = number;
                    double annual_depreciation = interest;
                    int year7 = (int) periods;
                    number = tq.straight_line_book_value(initial_cost7, annual_depreciation, year7);
                    break;
                case "ddb_fr":
                    logger.info("ddb_fr format : 1(`ddb_fr`, lifespan, 0)");
                    int lifespan8 = (int) interest;
                    number = tq.double_declining_fixed_depreciation_rate(lifespan8);
                    break;
                case "ddb_ar":
                    logger.info("ddb_ar format : 1(`ddb_ar`, lifespan, year)");
                    int lifespan9 = (int) interest;
                    int year9 = (int) periods;
                    number = tq.double_declining_annual_depreciation_rate(lifespan9, year9);
                    break;
                case "ddb_ad":
                    logger.info("ddb_ad format : initial cost(`ddv_ad`, lifespan, year)");
                    double initial_cost10 = number;
                    int lifespan10 = (int) interest;
                    int year10 = (int) periods;
                    number = tq.double_declining_annual_depreciation(initial_cost10, lifespan10, year10);
                    break;
                case "ddb_bv":
                    logger.info("ddb_bv format : initial cost(`ddb_bv`, lifespan, year)");
                    double initial_cost11 = number;
                    int lifespan11 = (int) interest;
                    int year11 = (int) periods;
                    number = tq.double_declining_book_value(initial_cost11, lifespan11, year11);
                    break;
                case "db_ad":
                    logger.info("db_ad format : initial cost(`db_ad`, stated fixed rate %, year)");
                    double initial_cost12 = number;
                    int year12 = (int) periods;
                    number = tq.declining_balance_annual_depreciation(initial_cost12, interest, year12);
                    break;
                case "db_bv":
                    logger.info("db_bv format : initial cost(`db_bv`, stated fixed rate %, year)");
                    double initial_cost13 = number;
                    int year13 = (int) periods;
                    number = tq.declining_balance_book_value(initial_cost13, interest, year13);
                    break;
                default:
                    logger.warning(factor + " is an unrecognised argument.");
                    System.exit(1);
            }
        }
        return number;
    }
}