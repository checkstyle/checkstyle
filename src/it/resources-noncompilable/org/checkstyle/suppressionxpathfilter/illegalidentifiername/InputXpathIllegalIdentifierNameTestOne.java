//non-compiled with javac: Compilable with Java14
package org.checkstyle.suppressionxpathfilter.illegalidentifiername;

/* Config:
 *
 * default
 */
public record InputXpathIllegalIdentifierNameTestOne
        (String string,
            String yield, // warn
            String otherString){
}
