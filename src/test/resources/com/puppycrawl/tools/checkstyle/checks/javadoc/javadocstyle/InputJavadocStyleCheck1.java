/*
JavadocStyle
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheck1 {

    private int field4;

   /** NM Webapp address.**/
   public static final String NM_WEBAPP_ADDRESS = "webapp.address";

    /** // violation 'First sentence should end with a period'
     * <thead> <br> </thead>
     * <tfoot> <AREA ALT="alt" Coordination="100,0,200,50" HREF="/href/"> </tfoot> comment
     * <p> </p>
     * @param field4 value to which {@link #field4} is to be set to
     */
    // violation 4 lines above 'Unclosed HTML tag found: .*'
    private void setField4(int field4) {this.field4 = field4;}
}
