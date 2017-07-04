public class InputMethodCount7 {
    void method1() {
    }

    void method2() {
    }

    enum InnerEnum {
        ;

        public static void test1() {
            Runnable r = (new Runnable() {
                public void run() {
                    run2();
                }

                private void run2() {
                }
            });
        }

        public static void test2() {
        }
    }
}
