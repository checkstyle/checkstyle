/*
JavadocBlockTagLocation
tags = (default)author, deprecated, exception, hidden, param, provides, return, \
       see, serial, serialData, serialField, since, throws, uses, version
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationIncorrect {

    // violation 9 lines below 'The Javadoc block tag '@author' should be placed'
    // violation 9 lines below 'The Javadoc block tag '@since' should be placed'
    // violation 9 lines below 'The Javadoc block tag '@param' should be placed'
    // violation 10 lines below 'The Javadoc block tag '@throws' should be placed'
    // violation 10 lines below 'The Javadoc block tag '@see' should be placed'
    // 2 violations 10 lines below:
    //     'The Javadoc block tag '@return' should be placed'
    //     'The Javadoc block tag '@throws' should be placed'
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
