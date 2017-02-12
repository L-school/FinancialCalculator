/**
 * Created by lpason on 2017-02-12.
 */
public class TestExpressioncalculator {



    public static void main(String[] args) {

        ExpressionCalculator calculator = new ExpressionCalculator();
        String rowLine = "(2+1.0)*3.0-4.0*(7+4)";
        Double result = calculator.calculateExperssion(rowLine);
        System.out.println(result);

    }


}
