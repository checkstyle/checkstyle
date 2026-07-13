package com.openjdk.checkstyle.test.chapterformatting.ruleannotations;

public class InputAnnotationsDoAndDonts {
    class Temp {
        public void foo() {
        }

        public void foo1() {
        }

        public void foo2(String... arg) {

        }
    }

    class Do extends Temp {
        public interface Listener {
            void event1();

            void event2();

            void event3();

            void event4();
        }

        public void addListener(Listener listener) {
        }

        @Deprecated
        @Override
        public void foo() {
        }

        @Deprecated @Override
        public void foo1() {
        }

        public void setup() {
            addListener(new Listener() {
                // Ignored events
                @Override public void event1() { }

                @Override public void event2() { }

                @Override public void event3() { }

                // Important event
                @Override
                public void event4() {
                    System.out.println("Event 4 triggered!");
                }
            });
        }
    }

    // No violation until https://github.com/checkstyle/checkstyle/issues/20209
    class Donts extends Temp {

        @Override @Deprecated public void foo() {
        }

        @Override @Deprecated
        @SafeVarargs
        public final void foo2(String... arg) {
        }
    }
}
