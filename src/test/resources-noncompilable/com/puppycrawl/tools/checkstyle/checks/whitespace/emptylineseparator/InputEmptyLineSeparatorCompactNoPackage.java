/* /nodynamiccopyright/ */ class InputEmptyLineSeparatorCompactNoPackage { // test
    // add two lines
    // to check line number tables
    void top() {
        return;                // 3
    }
    void nemcp2(int eights) { // violation 'should be separated from previous line'
        top();                 // 6
        return;                // 7
    }
    void nemcp1() { // violation 'should be separated from previous line'
        int rot = 4;
        nemcp2(888);           // 11
        return;                // 12
    }
    void emcp2() { // violation 'should be separated from previous line'
        nemcp1();              // 15
        return;                // 16
    }
    void emcp1(int myArg) { // violation 'should be separated from previous line'
        int paramy = 12;
        emcp2();               // 20
        return;                // 21
    }
    void bottom() { // violation 'should be separated from previous line'
        emcp1(56);             // 24
        return;                // 25
    }
    static void stnemcp() { // violation 'should be separated from previous line'
        (new InputEmptyLineSeparatorCompactNoPackage()).bottom(); // 28
                               // 29
        return;                // 30
    }
    static void stemcp() { // violation 'should be separated from previous line'
        stnemcp();             // 33
        return;                // 34
    }
}
