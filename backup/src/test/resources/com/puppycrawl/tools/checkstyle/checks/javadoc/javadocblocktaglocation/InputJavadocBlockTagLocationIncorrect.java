/*
JavadocBlockTagLocation
tags = (default)author, deprecated, exception, hidden, param, provides, return, \
       see, serial, serialData, serialField, since, throws, uses, version
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationIncorrect {

    /**
     * Summary. @author me // violation
     * <p>Text</p> @since 1.0 // violation
     * @param param1 a parameter @param, @custom // violation
     * @param param2 a parameter @custom
     * * @throws Exception // violation
    +    * @see PersistenceContext#setReadOnly(Object,boolean) // violation
     * text @return one more @throws text again. // 2 violations
     */
    public void method(int param1, int param2) {
    }

}
