package org.checkstyle.checks.suppressionxpathfilter.redundantmodifier;

public class InputXpathRedundantModifierWithEnum {
    interface I {
        static enum E { // warn
            A, B, C
        }
        void m();
        int x = 0;
  }
}
