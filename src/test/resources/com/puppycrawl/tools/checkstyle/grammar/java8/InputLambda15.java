package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.function.Function;
import java.util.logging.Logger;


public class InputLambda15
{
    private static final Logger LOG = Logger.getLogger(InputLambda15.class.getName());

    public static void main(String[] args) {
        InputLambda15 ex = new InputLambda15();
        Function<Double, Double> sin = d -> ex.sin(d);
        Function<Double, Double> log = d -> ex.log(d);
        Function<Double, Double> exp = d -> ex.exp(d);
        InputLambda15 compose = new InputLambda15();
        LOG.info(compose.calculate(sin.compose(log), 0.8).toString());
        // prints log:sin:-0.22
        LOG.info(compose.calculate(sin.andThen(log), 0.8).toString());
        // prints sin:log:-0.33
        LOG.info(compose.calculate(sin.compose(log).andThen(exp), 0.8).toString());
        //log:sin:exp:0.80
        LOG.info(compose.calculate(sin.compose(log).compose(exp), 0.8).toString());
        //exp:log:sin:0.71
        LOG.info(compose.calculate(sin.andThen(log).compose(exp), 0.8).toString());
        //exp:sin:log:-0.23
        LOG.info(compose.calculate(sin.andThen(log).andThen(exp), 0.8).toString());
        //sin:log:exp:0.71

    }

    public Double calculate(Function<Double, Double> operator, Double d)
    {
        return operator.apply(d);
    }

    public Double sin(Double d)
    {
        LOG.info("sin:");
        return Math.sin(d);
    }

    public Double log(Double d)
    {
        LOG.info("log:");
        return Math.log(d);
    }

    public Double exp(Double d)
    {
        LOG.info("exp:");
        return Math.exp(d);
    }
}
