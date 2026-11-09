/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceBlocks {

    public void testMethod12() {
        boolean result = false;
        boolean b3 = true;
        boolean b1 = true;
        boolean b2 = false;
        if (b1) {
            if (b3) {
                if (!b2) {
                    result = true;
                }
                result = true;
            }
        }
    }

    public void testMethod13() {
        int i = 9;
        int j = 6;
        int g = i + 8;
        int k = j + 10;
    }

    public void testMethod14() {
        Session s = openSession();
        Transaction t = s.beginTransaction(); // violation 'Distance .* is 5.'
        A a = new A();
        E d1 = new E();
        C1 c = new C1();
        E d2 = new E();
        a.setForward(d1);
        d1.setReverse(a);
        c.setForward(d2);
        d2.setReverse(c);
        Serializable aid = s.save(a);
        Serializable d2id = s.save(d2);
        t.commit();
        s.close();
    }

    private Session openSession() {
        return null;
    }

    static class Session {
        public Transaction beginTransaction() {
            return null;
        }

        public void close() {
        }

        public Serializable save(E d2) {
            return null;
        }

        public Serializable save(A a) {
            return null;
        }
    }

    static class Transaction {
        public void commit() {
        }
    }

    static class A {
        public void setForward(E d1) {
        }
    }

    static class E {
        public void setReverse(C1 c) {
        }

        public void setReverse(A a) {
        }
    }

    static class C1 {
        public void setForward(E d2) {
        }
    }

    static class Serializable {
    }
}
