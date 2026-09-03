package com.openjdk.checkstyle.test.chapterformatting.rulewrappingmethoddeclarations;

// violation first line 'Header mismatch'

import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputWrappingMethodDoAndDonts {
    class Do {
        public void someMethod(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap,
                       int anInt,
                       long aLong,
                       Set<Number> aSet,
                       double aDouble) {
        }

        public void someMethod1(String aString, List<Integer> aList,
                Map<String, String> aMap, int anInt, long aLong,
                double aDouble, long aLongs) {
        }

        public void someMethod2(String aString,
                       List<Map<Integer, StringBuffer>> aListOfMaps,
                       Map<String, String> aMap)
                throws IllegalArgumentException {
        }

        public void someMethod3(String aString, List<Integer> aList,
                Map<String, String> aMap, int anInt)
                        throws IllegalArgumentException {
        }

    }

    class Donts {

        // Not covered until https://github.com/checkstyle/checkstyle/issues/20638
        public void someMethod(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap,
                       int anInt, long aLong,
                       Set<Number> aSet,
                       double aDouble) {
        }

        public void someMethod2(String aString,
                       List<Map<Integer, StringBuffer>> aListOfMaps,
                       Map<String, String> aMap) throws InterruptedException {
            // violation above """The 'throws' clause should be on a new line when the
            // method declaration is wrapped."""
        }

        public void someMethod3(String aString,
                       List<Integer> aList,
                       Map<String, String> aMap)
                       throws IllegalArgumentException {
            // violation above """The 'throws' clause should be indented 8 spaces relative
            // to the method declaration or the previous line and should not align with the
            // previous line."""
        }

    }
}
