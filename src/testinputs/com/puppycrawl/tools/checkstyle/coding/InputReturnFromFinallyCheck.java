package  com.puppycrawl.tools.checkstyle.coding;

public class ReturnFromFinallyCheckTestInput {
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
