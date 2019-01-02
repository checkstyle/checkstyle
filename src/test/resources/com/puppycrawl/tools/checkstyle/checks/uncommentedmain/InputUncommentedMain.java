////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

/**
 * Test case for UncommentedMainCheck
 * @author o_sukhodolsky
 */
public class InputUncommentedMain
{
    // uncommented main
    public static void main(String[] args)
    {
        System.identityHashCode("InputUncommentedMain.main()");
    }
}

class Main1
{
    // uncommented main in class Main
    public static void main(String[] args)
    {
        System.identityHashCode("Main.main()");
    }
}

class UncommentedMainTest1
{
    // one more uncommented main
    public static void main(java.lang.String[] args)
    {
        System.identityHashCode("test1.main()");
    }
}

class UncommentedMainTest2
{
    // wrong arg type
    public static void main(int args)
    {
        System.identityHashCode("test2.main()");
    }
}

class UncommentedMainTest3
{
    // no-public main
    static void main(String[] args)
    {
        System.identityHashCode("test3.main()");
    }
}

class UncommentedMainTest4
{
    // non-static main
    public void main(String[] args)
    {
        System.identityHashCode("test4.main()");
    }
}

class UncommentedMainTest5
{
    // wrong return type
    public static int main(String[] args)
    {
        System.identityHashCode("test5.main()");
        return 1;
    }
}

class UncommentedMainTest6
{
    // too many params
    public static void main(String[] args, int param)
    {
        System.identityHashCode("test6.main()");
    }
}

class UncommentedMainTest7
{
    // main w/o params
    public static void main()
    {
        System.identityHashCode("test7.main()");
    }
}

class UncommentedMainTest8
{

    public static void main(String... args)
    {
        System.identityHashCode("test8.main()");
    }
}

class UncommentedMainTest9
{

    public static void main(String args)
    {
        System.identityHashCode("test9.main()");
    }
}
