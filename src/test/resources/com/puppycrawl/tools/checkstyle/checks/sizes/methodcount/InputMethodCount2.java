/*
MethodCount
maxTotal = 2
maxPrivate = 0
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF,ENUM_CONSTANT_DEF,ENUM_DEF,INTERFACE_DEF,ANNOTATION_DEF,METHOD_DEF, \
          RECORD_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public enum InputMethodCount2 {

    RED {
        @Override void something() {};
    },

    BLUE {
        // 2 violations above:
        //   'Number of private methods is 1 (max allowed is 0).'
        //   'Total number of methods is 3 (max allowed is 2).'
        @Override void something() {};
        protected void other1() {};
        private void other2() {};
    };

    @Override public String toString() { return ""; };

    abstract void something();
}
