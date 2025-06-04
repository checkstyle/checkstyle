package org.checkstyle.suppressionxpathfilter;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameOne
        (String string,
            String var, // warn
            String otherString){
}
