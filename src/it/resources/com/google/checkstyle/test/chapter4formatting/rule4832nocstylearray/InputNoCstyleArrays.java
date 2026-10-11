package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

/** Test case for ArrayTypeStyle (Java vs C). */
public class InputNoCstyleArrays {
  private int[] javastyle = new int[0];
  private int cstyle[] = new int[0]; // violation 'Array brackets at illegal position.'

  /** Some javadoc. */
  public static void mainJava(String[] javastyle) {}

  /** Some javadoc. */
  public static void mainC(String cstyle[]) { // violation 'Array brackets at illegal position.'
    final int[] blah = new int[0];
    final boolean isok1 = cstyle instanceof String[];
    final boolean isok2 = cstyle instanceof java.lang.String[];
    final boolean isok3 = blah instanceof int[];
    int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
    int array2[][][] = new int[3][3][3];
    // 3 violations above:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
  }

  /** Some javadoc. */
  public class Test {
    public Test[] variable;

    public Test[] getTests() {
      return null;
    }

    public Test[] getNewTest() {
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

    public Test[][] getTests2() {
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
