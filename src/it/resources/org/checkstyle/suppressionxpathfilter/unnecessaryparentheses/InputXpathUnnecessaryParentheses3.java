package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParentheses3 {
    public interface Predicate {
        boolean test(Integer t);
    }
    Predicate predicate = (value) -> value != null; // warn
}
