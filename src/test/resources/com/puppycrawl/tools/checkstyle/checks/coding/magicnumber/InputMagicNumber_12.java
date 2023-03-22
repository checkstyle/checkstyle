/*
MagicNumber
ignoreNumbers = -1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = true
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = NUM_DOUBLE, NUM_FLOAT, NUM_INT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputMagicNumber_12 {

    Object obj = new Object() {

        class Inner {
            int a = 0;
            int b = 9;
            double e = 0;
            double c = 5.5;
            float d = 12.2f;
            Set<Person> p1 = new HashSet<Person>(5);
            Object[] values = new Object[3];
            String[] fStrings = new String[3];
            Person p = new Person(8);
            List<Integer> multisets = new ArrayList(0x10000);
        }
    };
}

class outside {

    class inside {
        int a = 0;
        int b = 9;
        double e = 0;
        double c = 5.5;
        float d = 12.2f;
        Set<Person> p1 = new HashSet<Person>(5);
        Object[] values = new Object[3];
        String[] fStrings = new String[3];
        Person p = new Person(8);
        List<Integer> multisets = new ArrayList(0x10000);
    }
}

class outer {

    class inner {

        Object obj = new Object() {
            int a = 0;
            int b = 9; // violation ''9' is a magic number'
            double e = 0;
            double c = 5.5; // violation ''5.5' is a magic number'
            float d = 12.2f; // violation ''12.2f' is a magic number'
            Set<Person> p1 = new HashSet<Person>(5); // violation ''5' is a magic number'
            Object[] values = new Object[3]; // violation ''3' is a magic number'
            String[] fStrings = new String[3]; // violation ''3' is a magic number'
            Person p = new Person(8); // violation ''8' is a magic number'
            List<Integer> multisets = new ArrayList(5); // violation ''5' is a magic number'

            class innerMost {
                int a = 0;
                int b = 9;
                double e = 0;
                double c = 5.5;
                float d = 12.2f;
                Set<Person> p1 = new HashSet<Person>(5);
                Object[] values = new Object[3];
                String[] fStrings = new String[3];
                Person p = new Person(8);
                List<Integer> multisets = new ArrayList(0x10000);
            }
        };
    }
}
