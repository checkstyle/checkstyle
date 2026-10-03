/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformExpressionsAsOne = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityMethodCallCanonicalization {
  public boolean sameMethodCallChain(int a, int b, int c, int d, int e, int f)
  {
    return isThis(a) && isThis(b) && isThis(c) && isThis(d)
            && isThis(e) && isThis(f);
  }

  public boolean unresolvableCallChain(int[][] a, int[][] b, int[][] c,
      int[][] d, int[][] e)
  { // violation below 'Boolean expression complexity is 4 (max allowed is 3)'
    return a[0].toString().isEmpty() && b[0].toString().isEmpty()
            && c[0].toString().isEmpty() && d[0].toString().isEmpty()
            && e[0].toString().isEmpty();
  }

  private boolean isThis(int x)
  {
    return x > 0;
  }

}
