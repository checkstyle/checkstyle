/*
MagicNumber
ignoreNumbers = 0, 0.5, 1
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

public class InputMagicNumber_10 {

    double double_var2 = 43D; // ok
    double double_magic1 = 1.5_0; // ok

    private static Set<Person> p1 = new HashSet<Person>(5); // violation ''5' is a magic number'
    public Object[] values = new Object[3]; // violation ''3' is a magic number'
    private String[] fStrings = new String[3]; // violation ''3' is a magic number'
    private Person p = new Person(8); // violation ''8' is a magic number'
    List<Integer> multisets = new ArrayList(0x10000); // violation ''0x10000' is a magic number'
}

class Person{
    Person(int t){}

}
class C1 {
    public void magicMethod() {
        double double_var1 = 0D;
        double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
        double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'

        if (1.0 < 2.0); // violation ''2.0' is a magic number'
        double_magic1 *= 1.5; // violation ''1.5' is a magic number'
    }
}

class C2 {
    Object anon = new Object() {
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };
}

class C3 {

    C1 c = new C1(){
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };

    final Object anon = new Object() {
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };
}

class C4 {
    C2 cn = new C2(){
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };

    final Object anon = new Object() {
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };
}

class c3 {
    Person person = new Person(12) { // violation ''12' is a magic number'
        public void magicMethod() {
            double double_var1 = 0D;
            double_var1 = 1.0 + 2.0; // violation ''2.0' is a magic number'
            double double_magic1 = 1.5_0; // violation ''1.5_0' is a magic number'
            if (1.0 < 2.0); // violation ''2.0' is a magic number'
            double_magic1 *= 1.5; // violation ''1.5' is a magic number'
        }
    };
}
