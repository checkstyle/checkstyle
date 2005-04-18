package  com.puppycrawl.tools.checkstyle.coding;

public class InputReturnFromFinallyCheck {
    public void foo() {
        try {
            System.currentTimeMillis();
        } finally {
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } finally {
            if (System.currentTimeMillis() == 0) {
                return;
            }
        }
    }
}
