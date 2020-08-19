//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

import java.util.HashMap;

/* Config:
 * ignoreConstructors = false
 * ignoreModifiers = false
 */
public class InputDeclarationOrderRecordsAndCompactCtors {
    public record MyRecord1() {
        private static int a;

        void m() {
        }

        public MyRecord1() { // violation
        }

        private static int b; // violation
    }

    public record MyRecord2() {
        private static int a;

        void m() {
        }

        public MyRecord2 { // violation
        }

        private static int b; // violation
    }

    public record MyRecord3(String myString, Integer number) {
        record MyInnerRecord(int z){}
        static int x = 43;
        public MyRecord3{}
        private static final HashMap<String, Integer> hashMap = new HashMap<>(); // violation
    }
}
