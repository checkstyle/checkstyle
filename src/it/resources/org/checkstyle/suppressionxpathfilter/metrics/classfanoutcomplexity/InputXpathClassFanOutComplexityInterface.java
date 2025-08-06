package org.checkstyle.suppressionxpathfilter.metrics.classfanoutcomplexity;

public class InputXpathClassFanOutComplexityInterface {

}

interface BadInterface { // warn
    ExampleClass getExampleObject();
}

class ExampleClass {

}
