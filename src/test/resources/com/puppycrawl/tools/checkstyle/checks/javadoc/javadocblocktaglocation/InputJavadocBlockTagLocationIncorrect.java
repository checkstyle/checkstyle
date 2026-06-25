/*
JavadocBlockTagLocation
tags = (default)author, deprecated, exception, hidden, param, provides, return, \
       see, serial, serialData, serialField, since, throws, uses, version
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationIncorrect {

    // violation 8 lines below ''@author' should be placed at the beginning'
    // violation 8 lines below ''@since' should be placed at the beginning'
    // violation 8 lines below ''@param' should be placed at the beginning'
    // violation 9 lines below ''@throws' should be placed at the beginning'
    // violation 9 lines below ''@see' should be placed at the beginning'
    // violation 9 lines below ''@return' should be placed at the beginning'
    // violation 8 lines below ''@throws' should be placed at the beginning'
    /**
     * Summary. @author me
     * <p>Text</p> @since 1.0
     * @param param1 a parameter @param, @custom
     * @param param2 a parameter @custom
     * * @throws Exception
    +    * @see PersistenceContext#setReadOnly(Object,boolean)
     * text @return one more @throws text again.
     */
    public void method(int param1, int param2) {
    }

}
