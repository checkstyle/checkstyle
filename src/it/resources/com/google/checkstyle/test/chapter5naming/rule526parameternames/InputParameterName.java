package com.google.checkstyle.test.chapter5naming.rule526parameternames;

class InputParameterName {

  /** Some more Javadoc. */
  public void doSomething(
      int aaa,
      int abn,
      String aaA,
      boolean bB) { // violation 'Parameter name 'bB' must match pattern'
    for (Object obj : new java.util.ArrayList()) {}
  }

  /** Test enum for member naming check. */
  enum MyEnum1 {
    /** ABC constant. */
    ABC,

    /** XYZ constant. */
    XYZ;

    /** Should be mSomeMember. */
    private int someMember;

    public void doEnum(
        int aaaL,
        long llll_llll, // violation 'Parameter name 'llll_llll' must match pattern'
        boolean bB) {} // violation 'Parameter name 'bB' must match pattern'
  }

  /** Test public vs private method parameter naming check. */
  class InputParameterNameSimplePub {
    public void testing0(int par, int parA) {}

    public void testing1(int p) {}

    /** Holder for inner anonymous classes. */
    private void testing2(int param) {
      new Object() {
        public void testing3(int p) {}
      };
    }

    /** Invalid: public constructor and one char long. */
    public InputParameterNameSimplePub(int p) {}

    /** Valid: private constructor and one char long. */
    private InputParameterNameSimplePub(float p) {}

    void toManyArgs(
        int $arg1, // violation 'Parameter name .* must match pattern'
        int ar$g2, // violation 'Parameter name .* must match pattern'
        int arg3$, // violation 'Parameter name .* must match pattern'
        int a_rg4, // violation 'Parameter name 'a_rg4' must match pattern'
        int _arg5, // violation 'Parameter name '_arg5' must match pattern'
        int arg6_, // violation 'Parameter name 'arg6_' must match pattern'
        int aArg7, // violation 'Parameter name 'aArg7' must match pattern'
        int aArg8, // violation 'Parameter name 'aArg8' must match pattern'
        int aar_g) {} // violation 'Parameter name 'aar_g' must match pattern'
  }
}
