/*
AccessModifierOption
option = \tSPACE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.accessmodifieroption;


public class InputAccessModifierOption {


    class classOne {

    }

    class classTwo {

    }

    /**
     *
     */
    public int method1() { // violation , ' @return tag should be present and have description'
     return 2;
    }

    /**
     * @return
     */
    public String method2() { // violation , ' @return tag should be present and have description'
    return "arbitrary_string";
    }

    /**
     *
     * @return returns something
     * @throws
     */
    public int method3() throws Exception { // violation , 'Expected @throws tag for 'Exception''
        if(1!=2)
            System.out.println("hello world");
        else
            throw new Exception();
        return 1;
    }

}