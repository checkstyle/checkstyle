package com.puppycrawl.tools.checkstyle.coding;

class Foo1 {
    public void foo(String text) {
        System.out.println(text);

        class Bar {
            void bar (String text) {
                text = "xxx";
            }
        }
    }
}

class Foo2 {
    public void foo() {
        int x;
        class Bar {
            void bar () {
                int x = 1;
                x++;
                x++;
            }
        }
    }
}
