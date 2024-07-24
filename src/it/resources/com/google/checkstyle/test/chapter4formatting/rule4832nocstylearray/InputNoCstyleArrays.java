package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

/** Test case for ArrayTypeStyle (Java vs C). */
public class InputNoCstyleArrays {
  private int[] javastyle = new int[0];
  private int cstyle[] = new int[0]; // violation 'Array brackets at illegal position.'

  public static void mainJava(String[] javastyle) {} // ok

  /** some javadoc. */
  public static void mainC(String cstyle[]) { // violation 'Array brackets at illegal position.'
    final int[] blah = new int[0]; // ok
    final boolean isok1 = cstyle instanceof String[]; // ok
    final boolean isok2 = cstyle instanceof java.lang.String[]; // ok
    final boolean isok3 = blah instanceof int[]; // ok
    int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
    int array2[][][] = new int[3][3][3];
    // 3 violations above:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
  }

  /** some javadoc. */
  public class Test {
    public Test[] variable; // ok

    public Test[] getTests() {
      return null;
    }

    public Test[] getNewTest() { // ok
      return null;
    }

    public Test getOldTest()[] { // violation 'Array brackets at illegal position.'
      return null;
    }

    // 2 violations 3 lines below:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    public Test getOldTests()[][] {
      return null;
    }

    public Test[] getMoreTests()[] { // violation 'Array brackets at illegal position.'
      return null;
    }

    public Test[][] getTests2() { // ok
      return null;
    }
  }

  int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
  int array2[][][] = new int[3][3][3];
  // 3 violations above:
  //                    'Array brackets at illegal position.'
  //                    'Array brackets at illegal position.'
  //                    'Array brackets at illegal position.'
}
