package org.checkstyle.suppressionxpathfilter.methodtypeparametername;

public class InputXpathMethodTypeParameterName2<T>{

    static class Junk <T> {
        <fo_ extends T> void foo() { // warn
        }
    }
}
