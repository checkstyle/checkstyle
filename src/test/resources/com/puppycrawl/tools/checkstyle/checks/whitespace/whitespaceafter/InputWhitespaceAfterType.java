/*
WhitespaceAfter
tokens = TYPE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterType {
    int[]arrayBad; // violation '']' is not followed by whitespace'
    int[] arrayOk;
    int[][]matrixBad; // violation '']' is not followed by whitespace'
    int[][] matrixOk;
    int[][][]cubeBad; // violation '']' is not followed by whitespace'
    int[][][] cubeOk;
    int[]
    arrayNewlineOk;
    int[
            ]arrayNewlineBad; // violation '']' is not followed by whitespace'
    java.util.Map.Entry<String, Integer>entryBad; // violation ''>' is not followed by whitespace'
    java.util.Map.Entry<String, Integer> entryOk;
    java.util.List<java.util.Set<Byte>>listSetBad; // violation ''>' is not followed by whitespace'
    java.util.List<java.util.Set<Byte>> listSetOk;
    java.util.List<String>[]arrayOfListStringBad; // violation '']' is not followed by whitespace'
    java.util.List<String>[] arrayOfListStringOk;
    void paramBad(java.util.List<String>param) {} // violation ''>' is not followed by whitespace'
    void paramOk(java.util.List<String> param) {}
    void varargsBad(String...args) {} // violation ''...' is not followed by whitespace'
    void varargsOk(String... args) {}
    void multipleParamsBad(int[]arr, java.util.Map<String, Integer>map) {}
    // 2 violations above:
    //  '']' is not followed by whitespace'
    //  ''>' is not followed by whitespace'
    void multiParamsOk(int[] arr, java.util.Map<String, Integer> map) {}
    void instanceOf() {
        Object o = java.util.List.of("x");
        if (o instanceof java.util.List<?>bad) { } // violation ''>' is not followed by whitespace'
        if (o instanceof java.util.List<?> ok) { }
    }
    int switches(Object o) {
        record Point(int x, int y) {}
        switch (o) {
            case Point(int x, int y) when !(x >= 0 && y >= 0) -> {}
            default -> {}
        }
        return switch (o) {
            case java.util.List<?>bad -> 1; // violation ''>' is not followed by whitespace'
            case java.util.SequencedCollection<?> ok -> 2;
            case int[]bad -> 3; // violation '']' is not followed by whitespace'
            case byte[] ok -> 4;
            default -> 5;
        };
    }
    java.util.function.Predicate<int[]> p1 =
            (int[]bad) -> true; // violation '']' is not followed by whitespace'
    java.util.function.Predicate<int[]> p2 =
            (int...bad) -> true; // violation ''...' is not followed by whitespace'
    java.util.function.Predicate<int[]> p3 = (int[] ok) -> true;
    java.util.function.BiFunction<Integer, Integer, Integer> ok = (a, b) -> a + b;
    boolean returnInstanceof(Object o) { return o instanceof Enum; }
    record recordBad(String...s) {} // violation ''...' is not followed by whitespace'
    record recordGood(String... s) {}
    int/*<TargetElement>*/commentBad; // violation ''int' is not followed by whitespace'
    int /*<TargetElement>*/ commentOk2;
    int cStyleArrOk[];
    int cStyleMatrixOk[][];
    void cStyleArrayDeclarationOk(char ch[], int a) {}
}
