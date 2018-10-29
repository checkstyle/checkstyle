package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineCheck {

    @Annotation int x;

    int y;

    @Annotation
    @SomeClass.Annotation
    @java.lang.Deprecated
    public int getX() {
        return (int) x;
    }

}

class SomeClass {

    @interface Annotation {
    }

}

@interface Annotation {
}
