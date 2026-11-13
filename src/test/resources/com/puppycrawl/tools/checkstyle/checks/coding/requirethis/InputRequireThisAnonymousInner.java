/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousInner {
}

class Another {
    void method1() {
       for (int i = 0; i < 1; i++) {
           i = i + 1;
       }
       for (int i = 0; i < 1; i++) {
           for (int j = 0; j < 1; i++) {
               --i;
           }
       }
   }
   private int i;
}
class TestClass {
    private final TestClass field = new TestClass();

    private String child;

    public void method() {
        if (false) {
            return;
        } else if (true) {
            String child = (String) this.child;
            if (!(this.child instanceof String)) {
                child = field.get(child); // violation '.* variable 'field' needs "this.".'
            }
        }
    }

    public String get(String s) {
        return s;
    }
}
class TestClass3 {
    private static class Flags {
        public void method() {
            final char ch = ' ';
            parse(ch);
        }

        private static void parse(char c) {
        }
    }

    private void parse(String s) {
    }
}
