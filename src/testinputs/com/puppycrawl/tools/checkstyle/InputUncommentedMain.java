////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for UncommentedMainCheck
 * @author o_sukhodolsky
 */
public class InputUncommentedMain
{
    // uncommented main
    public static void main(String[] args)
    {
        System.out.println("InputUncommentedMain.main()");
    }
}

class Main
{
    // uncommented main in class Main
    public static void main(String[] args)
    {
        System.out.println("Main.main()");
    }
}

class test1
{
    // one more uncommented main
    public static void main(java.lang.String[] args)
    {
        System.out.println("test1.main()");
    }
}

class test2
{
    // wrong arg type
    public static void main(int args)
    {
        System.out.println("test2.main()");
    }
}

class test3
{
    // no-public main
    static void main(String[] args)
    {
        System.out.println("test3.main()");
    }
}

class test4
{
    // non-static main
    public void main(String[] args)
    {
        System.out.println("test4.main()");
    }
}

class test5
{
    // wrong return type
    public static int main(String[] args)
    {
        System.out.println("test5.main()");
        return 1;
    }
}

class test6
{
    // too many params
    public static void main(String[] args, int param)
    {
        System.out.println("test6.main()");
    }
}

class test7
{
    // main w/o params
    public static void main()
    {
        System.out.println("test7.main()");
    }
}
