public class Test {
    public static final Test ONE = new Test() {
        @Override
        public int value() {
            return 1;
        }
    };

    private Test() {
    }

    public int value() {
        return 0;
    }
}