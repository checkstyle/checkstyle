/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' has more than 1 empty lines before.'


import java.util.*; // violation ''import' has more than 1 empty lines before.'

import java.io.*;


public class // violation ''CLASS_DEF' has more than 1 empty lines before.'
InputEmptyLineSeparatorMultipleEmptyLines {


    private int counter; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'




    private Object obj = null; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'

    private int k;


    private static void foo() {} // violation ''METHOD_DEF' has more than 1 empty lines before.'

    private static void foo1() {} // violation ''}' has more than 1 empty lines after.'


}

class Test2 {
    void testFor()
    {
        for (int i = 1; i < 5; i++) {
        }


        for(int i = 1;i < 5;i++);
    }
}
