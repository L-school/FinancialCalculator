/**
 * Created by lpason on 2017-02-12.
 */
public class TestExpressioncalculator {



    public static void main(String[] args) {

        ExpressionCalculator calculator = new ExpressionCalculator();
        String rowLine = "$3 + (2eur+1.0)*3eur";
        Double result = calculator.calculateExperssion(rowLine);
        System.out.println(result);

    }


}
