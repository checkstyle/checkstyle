package org.checkstyle.suppressionxpathfilter.methodtypeparametername;

public class InputXpathMethodTypeParameterNameInner<T>{

    static class Junk <T> {
        <fo_ extends T> void foo() { // warn
        }
    }
}
