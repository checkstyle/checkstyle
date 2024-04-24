package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesLambdas {
    public interface Predicate {
        boolean test(Integer t);
    }
    Predicate predicate = (value) -> value != null; // warn
}
