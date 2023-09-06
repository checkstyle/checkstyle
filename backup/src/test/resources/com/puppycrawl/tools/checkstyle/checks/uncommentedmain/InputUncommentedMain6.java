/*
UncommentedMain
excludedClasses = \\.Main.*$


*/

package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

/**
 * Test case for UncommentedMainCheck
 * @author o_sukhodolsky
 */
public class InputUncommentedMain6
{
    // uncommented main
    public static void main(String[] args) // violation
    {
        System.identityHashCode("InputUncommentedMain.main()");
    }
}

class Main2
{
    // uncommented main in class Main
    public static void main(String[] args)
    {
        System.identityHashCode("Main.main()");
    }
}

class UncommentedMainTest61
{
    // one more uncommented main
    public static void main(String[] args) // violation
    {
        System.identityHashCode("test1.main()");
    }
}

class UncommentedMainTest62
{
    // wrong arg type
    public static void main(int args)
    {
        System.identityHashCode("test2.main()");
    }
}

class UncommentedMainTest63
{
    // no-public main
    static void main(String[] args)
    {
        System.identityHashCode("test3.main()");
    }
}

class UncommentedMainTest64
{
    // non-static main
    public void main(String[] args)
    {
        System.identityHashCode("test4.main()");
    }
}

class UncommentedMainTest65
{
    // wrong return type
    public static int main(String[] args)
    {
        System.identityHashCode("test5.main()");
        return 1;
    }
}

class UncommentedMainTest66
{
    // too many params
    public static void main(String[] args, int param)
    {
        System.identityHashCode("test6.main()");
    }
}

class UncommentedMainTest67
{
    // main w/o params
    public static void main()
    {
        System.identityHashCode("test7.main()");
    }
}

class UncommentedMainTest68
{

    public static void main(String... args) // violation
    {
        System.identityHashCode("test8.main()");
    }
}

class UncommentedMainTest69
{

    public static void main(String args)
    {
        System.identityHashCode("test9.main()");
    }
}
