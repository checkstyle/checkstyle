package org.checkstyle.suppressionxpathfilter.modifier.redundantmodifier;

public class InputXpathRedundantModifierWithEnum {
    interface I {
        static enum E { // warn
            A, B, C
        }
        void m();
        int x = 0;
  }
}
