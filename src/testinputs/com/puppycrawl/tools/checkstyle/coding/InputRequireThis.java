public class InputRequireThis {
    int i;
    void method1() {
        i = 3;
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1();
        j--;
    }
}
