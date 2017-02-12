/**
 * Created by lpason on 2017-02-12.
 */
public class TestExpressioncalculator {



    public static void main(String[] args) {

        ExpressionCalculator calculator = new ExpressionCalculator();
        String rowLine = "(2+1)*3-4*(7+4)";
        Double result = calculator.calculateExperssion(rowLine);
        System.out.println(result);
        System.out.println("test");

    }


}
