/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleDefaultSettingsFive
{
   /**
    * {@return a string}
    */
    public String foo()
    {
        return "Hello, world";
    }

   /**
    *   This is the first sentence with leading and trailing spaces.   Second sentence.
    */
    public String foo2()
    {
        return "Hello, world";
    }

   /**
    * {@return the Java language {@linkplain Modifier modifiers} for
    * the executable represented by this object}
    */
    public int foo3()
    {
        return 0;
    }

   /**
    * {@return {@code true} if this method is a bridge
    * method; returns {@code false} otherwise}
    */
    public boolean foo4()
    {
        return true;
    }

    /**
     * {@return {@code true} if this object has been {@linkplain #init
     * initialized}, {@code false} otherwise}
     */
     public boolean foo5()
     {
       return true;
     }
}
