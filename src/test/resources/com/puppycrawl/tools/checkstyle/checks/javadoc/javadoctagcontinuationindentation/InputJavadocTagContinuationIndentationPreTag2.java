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
      * For example, <em>FooLength [1] IMPLICIT INTEGER</em>, with value=4; // violation
      * would be encoded as "81 01 04"  whereas in explicit // violation
      * tagging it would be encoded as "A1 03 02 01 04". // violation
      * explicit tagging the form is always constructed. // violation
      * @param value original value being implicitly tagged
      */
     public Object writeImplicit(byte tag, Object value) {
         return new Object();
     }

     /**
      * Writes the object using a
      * <a href="{@docRoot}/serialized-form.html#java.time.Ser">dedicated serialized form</a>.
      * @serialData
      * <pre>
      *  out.writeByte(1);  // identifies a Duration
      *  out.writeLong(seconds);
      *  out.writeInt(nanos);
      * </pre>
      *
      * @return the instance of {@code Ser}, not null
      */
     @Serial
     private Object writeReplace() {
         return new Object();
     }

     /**
      * Queries this date-time.
      * <p>
      * This queries this date-time using the specified query strategy object.
      * <p>
      * Queries are a key tool for extracting information from date-times.
      * <p>
      * The most common query implementations are method references, such as
      * Additional implementations are provided as static methods on {@link TemporalQuery}.
      *
      * @implSpec
      * The default implementation must behave equivalent to this code: // violation
      * <pre>
      *  if (query == TemporalQueries.zoneId() ||
      *        query == TemporalQueries.chronology() || query == TemporalQueries.precision()) {
      *    return null;
      *  }
      *  return query.queryFrom(this);
      * </pre>
      * Future versions are permitted to add further queries to the if statement. // violation
      * <p> // violation
      * All classes implementing this interface and overriding this method must call // violation
      * {@code TemporalAccessor.super.query(query)}. JDK classes may avoid calling // violation
      * non-JDK classes may this optimization and must call {@code super}. // violation
      * <p> // violation
      * If the implementation for one of the queries listed in the // violation
      * if statement of the default implementation, then it must do so. // violation
      * <pre>
      *    return MINUTES;
      *  return TemporalAccessor.super.query(query);
      * </pre>
      * <p> // violation
      * Implementations must ensure that no observable state is altered when this // violation
      * read-only method is invoked. // violation
      *
      * @param query  the query to invoke, not null
      */
     Object query(Object query) {
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
