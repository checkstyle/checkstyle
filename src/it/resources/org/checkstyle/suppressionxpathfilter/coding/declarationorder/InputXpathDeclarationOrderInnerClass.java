/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.declarationorder;

public class InputXpathDeclarationOrderInnerClass {
    class InnerClass {
        private int b;
        public int a; // violation 'Variable access definition in wrong order.'
    }
}
