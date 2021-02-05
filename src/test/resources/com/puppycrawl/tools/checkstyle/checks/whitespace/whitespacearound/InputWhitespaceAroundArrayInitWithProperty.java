/* Config:
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
        "http://www.puppycrawl.com/dtds/configuration_1_3.dtd">
<module name="Checker">
<property name="charset" value="UTF-8"/>
<module name="TreeWalker">
    <module name="WhitespaceAround">
        <property name="allowWhitespaceAroundArrayInit" value="true"/>
        <property name="tokens" value="ARRAY_INIT,RCURLY"/>
    </module>
</module>
</module>
 */
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundArrayInitWithProperty {

    public void arrayInitTest() {
        final int[] colors = {1,2,3}; // missing whitespace after
                                    // '{' and missing whitespace before '}'
        final int[] colors1 = new int[]{4,5,6}; // missing whitespace before
                                                // and after '{' and missing whitespace before '}'
        final int[][] colors2 = {{1,2,3},{4,5,6}}; // violation whitespace
                                                    // at various spaces, mentioned in test
        final int[][] colors3 = { { 1,2,3 } , { 4,5,6 } }; // ok

    }
}
