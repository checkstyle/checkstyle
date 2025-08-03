package org.checkstyle.checks.suppressionxpathfilter.classfanoutcomplexity;

public class InputXpathClassFanOutComplexityInterface {

}

interface BadInterface { // warn
    ExampleClass getExampleObject();
}

class ExampleClass {

}
