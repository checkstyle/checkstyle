/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

/* /nodynamiccopyright/ */ class InputEmptyLineSeparatorCompactNoPackage { // test
// non-compiled with javac: missing package. Used for Testing purpose.
    // to check separator capabilities
    void top() {
        return;
    }
    void nemcp2(int eights) { // violation 'should be separated from previous line'
        top();
        return;
    }
    void nemcp1() { // violation 'should be separated from previous line'
        int rot = 4;
        nemcp2(888);
        return;
    }
    void emcp2() { // violation 'should be separated from previous line'
        nemcp1();
        return;
    }
    void emcp1(int myArg) { // violation 'should be separated from previous line'
        int paramy = 12;
        emcp2();
        return;
    }
    void bottom() { // violation 'should be separated from previous line'
        emcp1(56);
        return;
    }
    static void stnemcp() { // violation 'should be separated from previous line'
        (new InputEmptyLineSeparatorCompactNoPackage()).bottom();

        return;
    }
    static void stemcp() { // violation 'should be separated from previous line'
        stnemcp();
        return;
    }
}
