import java.awt.Toolkit;

public class InputRequireThis {
    int i;
    void method1() {
        i = 3;
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1();
        try {
            this.method1();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        this.i--;

        Integer.toString(10);
    }
}
