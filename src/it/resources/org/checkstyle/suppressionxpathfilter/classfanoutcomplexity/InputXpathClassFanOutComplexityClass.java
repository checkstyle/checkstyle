package org.checkstyle.suppressionxpathfilter.classfanoutcomplexity;

import java.util.Date;
import java.util.Timer;

public class InputXpathClassFanOutComplexityClass // warn
        extends ParentClass
        implements SomeInterface {
    @Override
    public void exampleMethod() {
        Timer timer = new Timer();
    }

    public Date returnTypeViolatesCheck(){
        int x = 1;
        return new Date();
    }
}

class ParentClass {
    public void exampleMethod () {
        Character c = 'c';
    }
    static class InnerClass {

    }
}

interface SomeInterface {

}
