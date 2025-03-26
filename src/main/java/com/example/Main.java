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
                default:
                    logger.warning(factor + " is an unrecognised argument.");
                    System.exit(1);
            }
        }
        return number;
    }
}