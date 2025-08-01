package org.checkstyle.checks.annotation.annotationonsameline;

public class InputXpathAnnotationOnSameLineMethod {
    @Deprecated int x;

    @Deprecated //warn
    public int getX() {
        return (int) x;
    }
}
