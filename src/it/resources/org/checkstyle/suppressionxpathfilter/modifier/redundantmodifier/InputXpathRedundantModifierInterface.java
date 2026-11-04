/*
RedundantModifier
jdkVersion = (default)22
tokens = METHOD_DEF


*/

package org.checkstyle.suppressionxpathfilter.modifier.redundantmodifier;

public abstract interface InputXpathRedundantModifierInterface {

    public void m(); // violation 'Redundant 'public' modifier.'
    public int x = 0;

    static enum E {
        A, B, C
  }
}
