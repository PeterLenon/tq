package com.example;
import org.apache.commons.cli.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.Stack;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final AP_calculator AP_calculator = new AP_calculator();
    private static final AF_calculator AF_calculator = new AF_calculator();
    private static final AG_calculator AG_calculator = new AG_calculator();
    private static final PF_calculator PF_calculator = new PF_calculator();
    private static final PA_calculator PA_calculator = new PA_calculator();
    private static final PG_calculator PG_calculator = new PG_calculator();
    private static final FP_calculator FP_calculator = new FP_calculator();
    private static final FA_calculator FA_calculator = new FA_calculator();
    private static final FG_calculator FG_calculator = new FG_calculator();

    private static String[] preprocessArgs(String[] args) {
        Stack<Character> wellFormedStack = new Stack<>();
        Stack<Character> factorStack = new Stack<>();
        for (String arg : args) {
            for (int i = 0; i < arg.length(); i++) {
                char c = arg.charAt(i);
                if(c =='('){
                    if ( i == 0){
                        logger.severe(arg + " is not a well formed argument");
                    }else{
                        wellFormedStack.push(c);
                    }
                }else if( c == ')') {
                    if (i == 0 || wellFormedStack.isEmpty() || factorStack.size() != 2) {
                        logger.severe(arg + " is not a well formed argument");
                    }else {
                        wellFormedStack.pop();
                    }
                }else if(!wellFormedStack.isEmpty() && c == ','){
                    factorStack.push(c);
                }
            }
            if (!wellFormedStack.isEmpty()) {
                logger.severe(arg + " is not a well formed argument");
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
                number = Double.parseDouble(express);
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
                    number = number * AP_calculator.calculate(interest, periods);
                    break;
                case "a/f":
                    number = number * AF_calculator.calculate(interest, periods);
                    break;
                case "a/g":
                    number = number * AG_calculator.calculate(interest, periods);
                    break;
                case "p/f":
                    number = number * PF_calculator.calculate(interest, periods);
                    break;
                case "p/g":
                    number = number * PG_calculator.calculate(interest, periods);
                    break;
                case "p/a":
                    number = number *  PA_calculator.calculate(interest, periods);
                    break;
                case "f/p":
                    number = number * FP_calculator.calculate(interest, periods);
                    break;
                case "f/g":
                    number = number *  FG_calculator.calculate(interest, periods);
                    break;
                case "f/a":
                    number = number * FA_calculator.calculate(interest, periods);
                    break;
                default:
                    logger.warning(factor + " is an unrecognised argument.");
                    System.exit(1);
            }
        }
        return number;
    }
}