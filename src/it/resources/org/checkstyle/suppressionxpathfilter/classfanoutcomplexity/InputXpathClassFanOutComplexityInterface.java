package org.checkstyle.suppressionxpathfilter.classfanoutcomplexity;

public class InputXpathClassFanOutComplexityInterface {

}

interface BadInterface { // warn
    ExampleClass getExampleObject();
}

class ExampleClass {

}
