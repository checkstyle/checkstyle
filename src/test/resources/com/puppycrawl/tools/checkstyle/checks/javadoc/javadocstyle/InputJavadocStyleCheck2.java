/*
JavadocStyle


*/

package /** package */ com.puppycrawl.tools
        .checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheck2 {

    /** Empty constructor block. **/
    public InputJavadocStyleCheck2() {
    }
}

/**
 * <body>
 * <p> This class is only meant for testing. </p>
 * <p> In html, closing all tags is not necessary.
 * </body>
 *
 * @see "https://www.w3.org/TR/html51/syntax.html#optional-start-and-end-tags"
 */
// violation 4 lines above 'Extra HTML tag found: </body>'
class check {

    /**
     * Clear the cache and free all cached SSL_SESSION*.
     */
    synchronized void clear() {
    }

}
