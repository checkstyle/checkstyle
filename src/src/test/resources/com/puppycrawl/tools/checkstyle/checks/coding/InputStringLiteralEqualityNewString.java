public class InputStringLiteralEqualityNewString {
    void test() {
        String a = new String("hello");
        String b = "hello";

        boolean result = (a == b); // violation expected
    }
}