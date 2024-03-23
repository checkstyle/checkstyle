package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParentheses5 {
    void foo () {
        String str = ("Checkstyle") + "is cool"; // warn
    }
}
