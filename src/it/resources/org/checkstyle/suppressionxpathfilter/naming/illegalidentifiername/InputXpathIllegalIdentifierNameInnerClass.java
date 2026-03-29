// Java17
package org.checkstyle.suppressionxpathfilter.naming.illegalidentifiername;

/* Config:
 *
 * default
 */
public class InputXpathIllegalIdentifierNameInnerClass {
    class Inner {
        void test(int var) { // warn
            // do something
        }
    }
}
