package org.checkstyle.suppressionxpathfilter.methodtypeparametername;

public class SuppressionXpathRegressionMethodTypeParameterName2 <T>{

    static class Junk <T> {
        <fo_ extends T> void foo() { // warn
        }
    }
}
