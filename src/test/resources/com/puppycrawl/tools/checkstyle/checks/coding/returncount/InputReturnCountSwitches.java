package com.puppycrawl.tools.checkstyle.checks.coding.returncount;
/* комментарий на русском */
public class InputReturnCountSwitches
{
    public boolean equals(Object obj) {
        int i = 1;
        switch (i) {
        case 1: return true;
        case 2: return true;
        case 3: return true;
        case 4: return true;
        case 5: return true;
        case 6: return true;
        }
        return false;
    }

    void foo(int i) {
        switch (i) {
        case 1: return;
        case 2: return;
        case 3: return;
        case 4: return;
        case 5: return;
        case 6: return;
        }
        return;
    }

    void foo1(int i) {
        if (i == 1) {
            return;
        }
        Object obj = new Object() {
                void method1(int i) {
                    switch (i) {
                    case 1: return;
                    case 2: return;
                    case 3: return;
                    case 4: return;
                    case 5: return;
                    }
                    return;
                }
            };
        return;
    }

    public boolean foo2() {
        int i = 1;
        switch (i) {
        case 1: return true;
        case 2: return true;
        case 3: return true;
        case 4: return true;
        case 5: return true;
        case 6: return true;
        }
        return false;
    }
}

class Test {

    public Test() {}
}
