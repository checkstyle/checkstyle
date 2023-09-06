/*
ArrayTypeStyle
javaStyle = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.arraytypestyle;

public class InputArrayTypeStyleNestedGenerics {
    protected Pair<Integer, Object>[] values1;
    protected Pair<Integer, Pair<String, Object>[]>[] values2;
    protected Pair<Integer, Pair<String, Pair<String, Object>>[]>[] values3a;
    protected Pair<
        Integer,
        Pair<
            String,
            Pair<String, Object>
        >[]
    >[] values3b;

    protected Pair<Integer, Object> values1b[]; // violation
    protected Pair<Integer, Pair<String, Object>[]> values2b[]; // violation
    protected Pair<Integer, Pair<String, Pair<String, Object>>[]> values3ab[]; // violation
    protected Pair<
        Integer,
        Pair<
            String,
            Pair<String, Object>
        >[]
    > values3bb[]; // violation

    protected static class Pair<L, R> {
        protected L key;
        protected R value;
    }
}
