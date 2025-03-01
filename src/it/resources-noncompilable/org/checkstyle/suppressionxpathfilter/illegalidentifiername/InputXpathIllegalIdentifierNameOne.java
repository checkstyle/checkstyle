//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameOne
        (String string,
            String var, // warn
            String otherString){
}
