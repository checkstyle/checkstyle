public class Example17 {
    void test(int x) {
        switch (x) { // violation, 'At least two case statements are required.'
            case 1:
                break;
        }
    }
}