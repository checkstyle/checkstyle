package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

/**
 * Test case for ArrayTypeStyle (Java vs C)
 **/
public class InputArrayTypeStyle
{
    private int[] javaStyle = new int[0];
    private int cStyle[] = new int[0]; //warn

    public static void mainJava(String[] aJavaStyle) 
    {
    }

    public static void mainC(String aCStyle[]) //warn
    {
        final int[] blah = new int[0]; 
        final boolean isOK1 = aCStyle instanceof String[]; 
        final boolean isOK2 = aCStyle instanceof java.lang.String[]; 
        final boolean isOK3 = blah instanceof int[]; 
        int[] array[] = new int [2][2]; //warn
        int array2[][][] = new int[3][3][3]; //warn
    }

    public class Test
    {
        public Test[]
            variable; 
        public Test[]
            getTests()
        {
            return null;
        }

        public Test[] getNewTest() 
        {
            return null;
        }

        public Test getOldTest()[] //warn
        {
            return null;
        }

        public Test getOldTests()[][] //warn
        {
            return null;
        }

        public Test[]
            getMoreTests()[] //warn
        {
            return null;
        }

        public Test[][] getTests2() 
        {
            return null;
        }
    }
    int[] array[] = new int [2][2]; //warn
    int array2[][][] = new int[3][3][3]; //warn
}
