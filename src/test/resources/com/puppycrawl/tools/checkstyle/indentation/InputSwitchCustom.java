public class MyClass {
    public int getValue(int value) {
        switch (value) {
            case 0: return ABC1;
            case 1: return ABC2;
            case 2: return ABC3;
        }
        return 0;
    }

    public int getValue1(int value) {
        switch (value) {
            case 0:
                return ABC1;
            case 1:
                return ABC2;
            case 2:
                return ABC3;
        }
        return 0;
    }
}