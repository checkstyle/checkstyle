// Java17
package org.checkstyle.checks.suppressionxpathfilter.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameOne
        (String string,
            String var, // warn
            String otherString){
}
