//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameOne
        (String string,
            String yield, // warn
            String otherString){
}
