/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

public class InputXpathModifierOrderVariable {
    static private boolean var = false; // violation ''private' modifier out of order with the JLS suggestions.'
}
