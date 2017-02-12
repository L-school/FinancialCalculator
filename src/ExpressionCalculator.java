/**
 * Created by lpason on 2017-02-11.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpressionCalculator {

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
        while(index < line.length()){
            Character a = line.charAt(index);

            if(Character.isDigit(a) && index != line.length()-1){
                tmpDigitString = tmpDigitString + Character.toString(a);
            }
            else if (Character.isDigit(a) && index == line.length()-1){
                inputArray.add(a.toString());
            }
            else{
                if(!tmpDigitString.isEmpty()) {
                    inputArray.add(tmpDigitString);
                }
                inputArray.add(a.toString());
                tmpDigitString = "";
            }
            index++;
        }
        return inputArray;
    }


    private List<String> fromIndexToOnr(String rowLine){

        List<String> line = parseLineIntoStringArray(rowLine);
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
            if(Character.isDigit(sign.charAt(0)) && Integer.parseInt(sign) > 0)
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
