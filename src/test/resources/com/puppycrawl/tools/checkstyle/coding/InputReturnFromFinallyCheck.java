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
                return; // return from if statement
            }
        }
    }
    public void thisNull() {
        boolean result = (this == null) || (null == this);
        boolean result2 = (this != null) && (null != this);
        boolean result3 = (this.getClass().getName() == String.valueOf(null == System.getProperty("abc")));
    }
}
