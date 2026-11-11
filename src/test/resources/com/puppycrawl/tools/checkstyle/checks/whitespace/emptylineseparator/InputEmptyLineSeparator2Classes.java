/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparator2Classes {

    class InnerClass {

        public static final double FOO_PI_INNER = 3.1415;

        private boolean flagInner = true;

        {
            //empty instance initializer
        }

        private InnerClass()
        {
            //empty
        }
    }


    class SecondInnerClass {

        private int intVariable;
    }
}

class Class22{
    public int compareTo(InputEmptyLineSeparator aObject)
    {
        int number = 0;
        return 0;
    }

    Class2 anon = new Class2(){
        public int compareTo(InputEmptyLineSeparator aObject)
        {
            int number = 0;
            return 0;
        }
    };
}

class Class32 {
    int field;
}
