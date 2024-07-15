package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

/** Test case for ArrayTypeStyle (Java vs C) */
public class InputNoCStyleArrays {
  private int[] javaStyle = new int[0];
  private int cStyle[] = new int[0]; // violation 'Array brackets at illegal position.'

  public static void mainJava(String[] aJavaStyle) // ok
  {}

  public static void mainC(String aCStyle[]) // violation 'Array brackets at illegal position.'
  {
    final int[] blah = new int[0]; // ok
    final boolean isOK1 = aCStyle instanceof String[]; // ok
    final boolean isOK2 = aCStyle instanceof java.lang.String[]; // ok
    final boolean isOK3 = blah instanceof int[]; // ok
    int[] array[] = new int[2][2]; // violation 'Array brackets at illegal position.'
    int array2[][][] = new int[3][3][3];
    // 3 violations above:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
  }

  public class Test {
    public Test[] variable; // ok

    public Test[] getTests() {
      return null;
    }

    public Test[] getNewTest() // ok
    {
      return null;
    }

    public Test getOldTest()[] // violation 'Array brackets at illegal position.'
    {
      return null;
    }

    public Test getOldTests()[][]
    // 2 violations above:
    //                    'Array brackets at illegal position.'
    //                    'Array brackets at illegal position.'
    {
      return null;
    }

    public Test[] getMoreTests()[] // violation 'Array brackets at illegal position.'
    {
      return null;
    }

    public Test[][] getTests2() // ok
    {
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
