package org.checkstyle.suppressionxpathfilter.needbraces;

public class InputXpathNeedBracesSingleLineLambda {
    static Runnable r3 = () -> // warn
            String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
}
