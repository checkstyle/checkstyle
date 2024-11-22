package com.puppycrawl.tools.checkstyle.grammar;

public class InputAstRegressionTryWithResourcesOnAutoCloseable {
    static class T implements AutoCloseable {
        public void doIt() {
            open();
            try (this) {
                System.out.println("doIt");
            }

            T t = new T();

            try (t) {
                System.out.println("doIt");
            }
        }

        public void open() {
            System.out.println("open");
        }

        @Override
        public void close() {
            System.out.println("close");
        }
    }
}
