/**
 * Created by lpason on 2017-02-11.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpressionCalculator {

    public static List<String> currencyList = new ArrayList<>();

    ExpressionCalculator(){
        currencyList.add("$");
        currencyList.add("eur");
    }

    public Double calculateExperssion(String rowLine) {

        List<String> onrList = fromIndexToOnr(rowLine);
        Stock onrStock = new Stock(onrList.size());

        for (String e : onrList) {
            if(Character.isDigit(e.charAt(0))){
                onrStock.push(e);
            }
            else if(!Character.isDigit(e.charAt(0))){
                Double a = Double.parseDouble(onrStock.pop());
                Double b = Double.parseDouble(onrStock.pop());
                Double c = 0.0;

                switch(e.charAt(0)){
                    case '+': c = b + a; break;
                    case '-': c = b - a; break;
                    case '*': c = b * a; break;
                    case '/': c = b / a; break;
                    case '%': c = b % a; break;
                    case '^': c = Math.pow(a,b); break;
                }
                onrStock.push(c.toString());
            }
        }
        return Double.parseDouble(onrStock.peek());
    }

    private List parseLineIntoStringArray(String rowLine){

        String line = rowLine.replaceAll("\\s+","");

        List<String> inputArray = new ArrayList<>();

        int index = 0;
        String tmpDigitString = "";
        String tmpAlphabeticString = "";
        while(index < line.length()){
            Character a = line.charAt(index);

            if(Character.isDigit(a) || a.equals('.') && index != line.length()-1){
                tmpDigitString = tmpDigitString + Character.toString(a);
                if(!tmpAlphabeticString.isEmpty()){
                    inputArray.add(tmpAlphabeticString);
                    tmpAlphabeticString = "";
                }
            }
            else if (Character.isDigit(a) && index == line.length()-1){
                tmpDigitString = tmpDigitString + Character.toString(a);
                inputArray.add(tmpDigitString);
            }

            else if (Character.isAlphabetic(a)) {
                tmpAlphabeticString = tmpAlphabeticString + a;
                if(!tmpDigitString.isEmpty()) {
                    inputArray.add(tmpDigitString);
                    tmpDigitString = "";
                }
                if(Character.isAlphabetic(a) && index == line.length()-1){
                    inputArray.add(tmpAlphabeticString);
                }


            }
            else{
                if(!tmpDigitString.isEmpty()) {
                    inputArray.add(tmpDigitString);
                }
                if(!tmpAlphabeticString.isEmpty()){
                    inputArray.add(tmpAlphabeticString);
                    tmpAlphabeticString = "";
                }
                inputArray.add(a.toString());
                tmpDigitString = "";
            }
            index++;
        }
        return inputArray;
    }


    private Boolean isCurrencyUnified(List<String> initList){

        String usedCurrency = "";
        for(String symbol : initList){
            if(currencyList.contains(symbol)){
                if(!usedCurrency.isEmpty() && !symbol.equals(usedCurrency)){
                    throw new IllegalArgumentException("Currency mismatch");
                } else usedCurrency = symbol;
            }
        }
        return true;
    }


    private List<String> getRidOfCurrencyNotation(List<String> rawList){

        List<String> newList = new ArrayList<>();
        for(String s : rawList){
            if(!currencyList.contains(s)){
                newList.add(s);
            }
        }
        return newList;
    }

    private List<String> fromIndexToOnr(String rowLine){

        List<String> parsedLine = parseLineIntoStringArray(rowLine);
        isCurrencyUnified(parsedLine); //WTH?
        List<String> line = getRidOfCurrencyNotation(parsedLine);


        HashMap<String, Integer> symbolMap = new HashMap<>();
        symbolMap.put("(", 0);
        symbolMap.put("+", 1);
        symbolMap.put("-", 1);
        symbolMap.put(")", 1);
        symbolMap.put("*", 2);
        symbolMap.put("/", 2);
        symbolMap.put("%", 2);
        symbolMap.put("^", 3);

        int index = 0;
        List<String> charQueue = new ArrayList<>();
        Stock stock = new Stock(line.size());

        while(index < line.size()){
            String sign = line.get(index);
            if(Character.isDigit(sign.charAt(0)) && Double.parseDouble(sign) > 0.0)
                charQueue.add(sign);

            else if(sign.equals("(")){
                stock.push(sign);
            }
            else if(sign.equals(")")){
                while(!stock.isEmpty() && !stock.peek().equals("(")) {
                    if (!stock.peek().equals("(")) {
                        charQueue.add(stock.pop());
                    }
                }
                stock.pop();
            }
            else if(symbolMap.containsKey(sign)){

                if(stock.isEmpty() == true || (symbolMap.get(sign)) > symbolMap.get(stock.peek()))
                    stock.push(sign);
                else {
                    while(stock.isEmpty() == false && symbolMap.get(sign) <= symbolMap.get(stock.peek())){
                        charQueue.add(stock.pop());
                    }
                    stock.push(sign);
                }
            }
            else if(!Character.isDigit(sign.charAt(0)) && !symbolMap.containsKey(sign)) {
                System.out.println("expression contains incorrect sign: " + sign);
            }
            index++;
        }

        while(!stock.isEmpty()){
            if((stock.peek() != "(" && stock.peek() != ")")) {
                charQueue.add(stock.pop());
            }
            else
                stock.pop();
        }
        return charQueue;
    }
}
