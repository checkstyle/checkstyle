package org.checkstyle.suppressionxpathfilter.defaultcomeslast;

public class InputXpathDefaultComesLastNonEmptyCase {
    public void test() {
        String lang = "Java";
        int id = 0;
        switch (lang) {
            default : id = -1; //warn
            case "C++" : id = 1; break;
            case "Python" : id = 2; break;
        }
    }
}
