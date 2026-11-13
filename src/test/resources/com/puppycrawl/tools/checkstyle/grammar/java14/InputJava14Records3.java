/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/


package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Input for Java 14 records: varargs, local and nested records.
 */
public class InputJava14Records3 // ok
{
    record NoComps() {}
    record Record(Record record) {}

    record R5(String... args) {}
    record R6(long l, String... args) implements Serializable {}
    record R7(String s1, String s2, String... args) {}

    record RI(int... xs) { }
    record RII(int x, int... xs) { }
    record RX(int[] xs) { }

    private static int record = 2;

    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    class LocalRecordHelper {
        Class<?> m(int x) {
            record R76 (int x) { }
            return R.class;
        }

        private class R {
            public R(int x) {
            }
        }
    }

    record R1 () implements Serializable {
        private static final TimeUnit Path = null;
        private static final long serialVersionUID = -2911897846173867769L;

        public R1 {

        }
    }

    record RR3 (String... args) implements Serializable {
        private static final boolean firstDataSetCreated = false;
        private static final long serialVersionUID = -5626758281412733319L;

        public RR3 {
            if (firstDataSetCreated) {
                ProcessHandle.current();
            }
        }
    }

    private static void recordArray(int [] record) {
        record[2] = InputJava14Records3.record;
    }
}
