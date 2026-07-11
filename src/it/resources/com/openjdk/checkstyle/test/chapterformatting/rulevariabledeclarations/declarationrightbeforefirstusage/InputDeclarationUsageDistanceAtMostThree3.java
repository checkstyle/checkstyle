package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch*'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree3 {

    /** Some javadoc. */
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

    /** Some javadoc. */
    public void testMethod13() {
        int i = 9;
        int j = 6;
        int g = i + 8;
        int k = j + 10;
    }

    /** Some javadoc. */
    public void testMethod14() {
        Session s = openSession();
        Transaction t = s.beginTransaction();
        // violation above 'Distance between variable 't' .* first usage is 5, but allowed 3.'
        A a = new A();
        E d1 = new E();
        C1 c = new C1();
        E d2 = new E();
        a.setForward(d1);
        d1.setReverse(a);
        c.setForward(d2); // DECLARATION OF VARIABLE 'c' SHOULD BE HERE (distance = 3)
        // DECLARATION OF VARIABLE 'd2' SHOULD BE HERE (distance = 3)
        d2.setReverse(c);
        Serializable aid = s.save(a);
        Serializable d2id = s.save(d2);
        t.commit(); // DECLARATION OF VARIABLE 't' SHOULD BE HERE (distance = 5)
        s.close();
    }

    /** Some javadoc. */
    public boolean isCheckBoxEnabled(int path) {
        String model = "";
        if (true) {
            for (int index = 0; index < path; ++index) {
                int nodeIndex = model.codePointAt(path);
                if (model.contains("")) {
                    return false;
                }
            }
        } else {
            int nodeIndex = model.codePointAt(path);
            if (model.contains("")) {
                return false;
            }
        }
        return true;
    }

    /** Some javadoc. */
    public Object readObject(String in) throws Exception {
        String startDay = new String("");
        String endDay = new String("");
        return new String(startDay + endDay);
    }

    private Session openSession() {
        return null;
    }

    class Session {
        public Transaction beginTransaction() { return null; }

        public void close() {}

        public Serializable save(E d2) { return null; }

        public Serializable save(A a) { return null; }
    }

    class Transaction {
        public void commit() {}
    }

    class A {
        public void setForward(E d1) {}
    }

    class E {
        public void setReverse(C1 c) {}

        public void setReverse(A a) {}
    }

    class C1 {
        public void setForward(E d2) {}
    }

    class Serializable {}
}
