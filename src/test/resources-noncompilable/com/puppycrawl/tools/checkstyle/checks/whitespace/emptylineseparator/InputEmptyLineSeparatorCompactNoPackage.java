/* /nodynamiccopyright/ */ class InputEmptyLineSeparatorCompactNoPackage { // test
    // add two lines
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
