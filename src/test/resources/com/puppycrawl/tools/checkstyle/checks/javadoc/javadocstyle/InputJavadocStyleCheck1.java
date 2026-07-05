/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheck1 {

    private int field4;

   /** NM Webapp address.**/
   public static final String NM_WEBAPP_ADDRESS = "webapp.address";

    /**
     * <thead> <br> </thead>
     * <p> </p>
     * @param field4 value to which {@link #field4} is to be set to
     */
    private void setField4(int field4) {this.field4 = field4;}
}
