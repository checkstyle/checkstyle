/*
 * This test file intentionally does not use an inline config header
 * to reproduce the crash conditions (token at index 0 or 1).
 */
public class InputEmptyLineSeparatorCrash18660 {
    // This comment is at the start of the class body and used to crash EmptyLineSeparatorCheck

    void display(boolean ifYes) {
        if (true) {
            System.out.println("Hello world");
        }
    }
}
