/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

import java.util.HashMap;

public class InputDeclarationOrderRecordsAndCompactCtors {
    public record MyRecord1() {
        private static int a;

        void m() {
        }

        public MyRecord1() { // violation 'Constructor definition in wrong order.'
        }

        private static int b; // violation 'Static variable definition in wrong order.'
    }

    public record MyRecord2() {
        private static int a;

        void m() {
        }

        public MyRecord2 { // violation 'Constructor definition in wrong order.'
        }

        private static int b; // violation 'Static variable definition in wrong order.'
    }

    public record MyRecord3(String myString, Integer number) {
        record MyInnerRecord(int z){}
        static int x = 43;
        public MyRecord3{}
        private static final HashMap<String,// violation 'Static variable definition in wrong order'
                Integer> hashMap = new HashMap<>();
    }
}
