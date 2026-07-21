/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.Serial;

/**
 * Test class.
 *
 * @apiNote
 *          This is the predefined indentation applied by Eclipse formatter to api/impl taglets.
 *          <pre>
 * this content, and reports an error:
 *   "JavadocTagContinuationIndentation: Line continuation have incorrect indentation level,
 *   expected level should be 4"</pre>
 */
public class InputJavadocTagContinuationIndentationPreTag2 {

     /**
      * Writes implicitly tagged data using buffer-to-buffer copy.
      *
      * @param tag the DER value of the context-specific tag that replaces
      *     original tag of the value in the output, such as in
      * <pre>
      *          <em> {@code <field> [N] IMPLICIT <type>}</em>
      * </pre>
      * For example, <em>FooLength [1] IMPLICIT INTEGER</em>, with value=4;
      * would be encoded as "81 01 04"  whereas in explicit
      * tagging it would be encoded as "A1 03 02 01 04".
      * explicit tagging the form is always constructed.
      * @param value original value being implicitly tagged
      */
     public Object writeImplicit(byte tag, Object value) {
         return new Object();
     }
     // violation 9 lines above 'Line continuation .* expected level should be 4'
     // violation 9 lines above 'Line continuation .* expected level should be 4'
     // violation 9 lines above 'Line continuation .* expected level should be 4'
     // violation 9 lines above 'Line continuation .* expected level should be 4'

     /**
      * Writes the object using a
      * <a href="{@docRoot}/serialized-form.html#java.time.Ser">dedicated serialized form</a>.
      * @serialData
      * <pre>
      *  out.writeByte(1);  // identifies a Duration
      *  out.writeLong(seconds);
      *  out.writeInt(name);
      * </pre>
      *
      * @return the instance of {@code Ser}, not null
      */
     @Serial
     private Object writeReplace() {
         return new Object();
     }

     /**
      * Sends an attributeChangeNotification which contains the old value and new value for the
      * attribute to the registered AttributeChangeNotification listeners on the ModelMBean.
      *
      * @param oldValue The original value for the Attribute
      * @param newValue The current value for the Attribute
      * <PRE>
      * The constructed attributeChangeNotification will be:
      * attributeType oldValue's class
      *          attributeOldValue oldValue.getValue()
      *   attributeNewValue newValue.getValue()
      * </PRE>
      *
      * @exception MBeanException Wraps a distributed communication Exception.
      */
     Object query2(Object query) {
         return new Object();
     }
}
