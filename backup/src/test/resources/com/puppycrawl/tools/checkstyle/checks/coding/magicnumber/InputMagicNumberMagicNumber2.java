/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
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

public class InputMagicNumberMagicNumber2 {
    {
        int a = 0;
        int b = 9; // violation ''9' is a magic number'
        double e = 0;
        double c = 5.5; // violation ''5.5' is a magic number'
        float d = 12.2f; // violation ''12.2f' is a magic number'
    }

    void foo(){
        {
            int notField = 45; // violation ''45' is a magic number'
            int notField2 = 1;
        }
    }

    void foo2() {
        {
            int a = 0;
            int b = 9; // violation ''9' is a magic number'
            double e = 0;
            double c = 5.5; // violation ''5.5' is a magic number'
            float d = 12.2f; // violation ''12.2f' is a magic number'
        }
    }

    {
        Set<Person> p1 = new HashSet<Person>(5); // violation ''5' is a magic number'
        Object[] values = new Object[5]; // violation ''5' is a magic number'
        String[] fStrings = new String[5]; // violation ''5' is a magic number'
        Person p = new Person(5); // violation ''5' is a magic number'
        List<Integer> multisets = new ArrayList(5); // violation ''5' is a magic number'
    }

    {
        Set<Person> p1 = new HashSet<Person>(1);
        Object[] values = new Object[1];
        String[] fStrings = new String[1];
        Person p = new Person(1);
        List<Integer> multisets = new ArrayList(1);
    }

    void method() {
        {
            Set<Person> p1 = new HashSet<Person>(5); // violation ''5' is a magic number'
            Object[] values = new Object[5]; // violation ''5' is a magic number'
            String[] fStrings = new String[5]; // violation ''5' is a magic number'
            Person p = new Person(5); // violation ''5' is a magic number'
            List<Integer> multisets = new ArrayList(5); // violation ''5' is a magic number'
        }

        {
            Set<Person> p1 = new HashSet<Person>(1);
            Object[] values = new Object[1];
            String[] fStrings = new String[1];
            Person p = new Person(1);
            List<Integer> multisets = new ArrayList(1);
        }
    }
}
