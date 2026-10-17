/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformExpressionsAsOne = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityUniformChainOperators2 {
    public boolean differentMethodCalls(int a, int b, int c, int d, int e, int f)
    {
        return isThis(a) && isThat(b) && isSome(c) && isSomething(d) && isSomeComplicated(e, f);
        // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
    }

    private static class Node
    {
        Node[] children;
        boolean isCompilable() { return true; }
        Object exitTypeDescriptor;
    }
    private static class CodeFlow
    {

        static boolean isBooleanCompatible(Object descriptor) { return descriptor != null; }
    }
    private final Node[] children = new Node[3];

    public boolean isCompilable()
    {
        Node condition = children[0];
        Node left = children[1];
        Node right = children[2];
        // violation below 'Boolean expression complexity is 5 (max allowed is 3)'
        return condition.isCompilable() && left.isCompilable()
                && right.isCompilable()
                && CodeFlow.isBooleanCompatible(condition.exitTypeDescriptor)
         && left.exitTypeDescriptor != null && right.exitTypeDescriptor != null;
    }
    public boolean sameMethodChain(int a, int b, int c)
    {
        return This(a) && This(b) &&This(c);
        // ok, uniform chain of calls to the same method collapses to complexity 1
    }

    public boolean arrayFieldCompare(int[][] a, int[][] b, int[][] c,
      int x, int y, int z, int p, int q, int r, int s, int t, int u, int v, int w)
    { // violation below 'Boolean expression complexity is 6 (max allowed is 3)'
        return a[0].length == x && b[0].length == y && c[0].length == z
                && p == q && r == s && t == u && v == w;
    }

    private boolean This(int x) { return x > 0; }
    private boolean isThis(int a) { return a > 0; }
    private boolean isThat(int b) { return b > 0; }
    private boolean isSome(int c) { return c > 0; }
    private boolean isSomething(int d) { return d > 0; }
    private boolean isSomeComplicated(int e, int f) { return e > f; }

}
