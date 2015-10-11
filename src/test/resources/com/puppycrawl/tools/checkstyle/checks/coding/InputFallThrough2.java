package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputFallThrough2 {
    enum Test {
        A, B, C
    }

    public static void test() {
        Test test = Test.A;
        int variable = 0;

        switch (test) {
            case A:
                break;
            case B:
                if (variable == 1) {
                    // some work
                    break;
                }
            case C:
                break;
        }

        int var2 = 1;
        switch (variable) {
            case 0:
            case 1:
            case 2:
                System.out.println(var2);
                break;
            case 3:
                if (true) {
                    return;
                }
            case 4:
                if (var2 == 2) {
                    break;
                }
            case 5:
                if (var2 == 1) {

                }
                else if (true) {
                    return;
                }
            case 6:
                if (var2 > 1) {
                    break;
                }
                else {
                    break;
                }
            case 7:
                if (var2 ==1) {
                    break;
                }
                else if (true) {
                    return;
                }
            case 8:
                if(var2 == 5) {
                    System.out.println("0xB16B00B5");
                }
                else {
                    break;
                }
            case 9:
                if(var2 == 5) {
                    System.out.println("0xCAFED00D");
                }
                else {
                    System.out.printf("0x4B1D");
                }
                break;
            case 10:
                int var3 = 0xDEADBEEF;
                switch (var3) {
                    case 0xCAFEBABE:
                        System.out.printf("0x1CEB00DA");
                    default:
                        System.out.printf("");
                }
                if(true) {
                    break;
                }
            case 11:
                if(false) {break;}
            case 12:
                if(true);
                break;
            default:
                break;
        }
    }
}
