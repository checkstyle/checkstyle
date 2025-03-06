package org.checkstyle.suppressionxpathfilter.unnecessarynullwithinstanceof;

public class InputXpathUnnecessaryNullCheckWithInstanceOf {
    public void methodWithUnnecessaryNullCheck1(Object obj) {
        if (obj != null && obj instanceof String) { //warn
            String str = (String) obj;
        }
    }
}
