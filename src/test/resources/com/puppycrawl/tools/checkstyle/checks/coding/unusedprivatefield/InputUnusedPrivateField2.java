/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField2 {

    private int unused; // violation 'Unused private field'

    private int anotherUnused; // violation 'Unused private field'

    private String yetAnother; // violation 'Unused private field'

    public void someMethod() {
        System.out.println("No fields used");
    }

    private void outer() {

    class Inner {
        private int field; // violation 'Unused private field'
        void innerMethod() {}
    }
    }
interface Selector {
        boolean select(String s);
    }

    interface SelectorProvider {
        Selector getSelectorForSource(String source);
    }

    public void methodWithAnonymous(String param) {
        SelectorProvider p = new SelectorProvider() {
            @Override
            public Selector getSelectorForSource(final String source) {
                return new Selector() {
                    @Override
                    public boolean select(String s) {
                        return s.equals(source);
                    }
                };
            }
        };
        p.getSelectorForSource(param).select("test");
    }

    public void outerMethod(int p) {
        Object anon = new Object() {
            void anonMethod(int x) {
                int local = x + 1; // VARIABLE_DEF inside SLIST under METHOD_DEF
                System.out.println(local);
            }
        };
        System.out.println(anon);
    }

    public void noParamMethod() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                }
            }
        };
        r.run();
    }
}
