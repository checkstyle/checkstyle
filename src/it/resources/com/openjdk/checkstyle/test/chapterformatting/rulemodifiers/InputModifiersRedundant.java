package com.openjdk.checkstyle.test.chapterformatting.rulemodifiers;

// violation first line 'Header mismatch'

public class InputModifiersRedundant {

    void test() {
        // violation below, 'Redundant 'final' modifier'
        try (final var a = lock()) {

        } catch (Exception e) {

        }
    }

    // violation below, 'Redundant 'abstract' modifier'
    abstract interface I {

        public int TEMP = 0; // violation, 'Redundant 'public' modifier'

        public abstract void method();
        // 2 violations above:
        //    'Redundant 'public' modifier'
        //    'Redundant 'abstract' modifier'
    }

    static enum E { // violation, 'Redundant 'static' modifier'
        A, B, C
    }

    // violation below, 'Redundant 'strictfp' modifier'
    public strictfp class Test { }

    AutoCloseable lock() {
        return null;
    }

}
