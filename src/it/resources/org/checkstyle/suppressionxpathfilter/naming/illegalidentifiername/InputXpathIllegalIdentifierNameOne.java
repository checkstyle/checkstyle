// Java17
package org.checkstyle.suppressionxpathfilter.naming.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameOne
        (String string,
            String var, // warn
            String otherString){
}
