/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

class InputJavadocTagContinuationIndentationPreTag2Two {

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
      * The default implementation must behave equivalent to this code:
      * <pre>
      *  if (query == TemporalQueries.zoneId() ||
      *        query == TemporalQueries.chronology() || query == TemporalQueries.precision()) {
      *    return null;
      *  }
      *  return query.queryFrom(this);
      * </pre>
      * Future versions are permitted to add further queries to the if statement.
      * <p>
      * All classes implementing this interface and overriding this method must call
      * {@code TemporalAccessor.super.query(query)}. JDK classes may avoid calling
      * non-JDK classes may this optimization and must call {@code super}.
      * <p>
      * If the implementation for one of the queries listed in the
      * if statement of the default implementation, then it must do so.
      * <pre>
      *    return MINUTES;
      *  return TemporalAccessor.super.query(query);
      * </pre>
      * <p>
      * Implementations must ensure that no observable state is altered when this
      * read-only method is invoked.
      *
      * @param query  the query to invoke, not null
      */
     Object query(Object query) {
         return new Object();
     }
     // violation 29 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 22 lines above 'Line continuation .* expected level should be 4'
     // violation 18 lines above 'Line continuation .* expected level should be 4'
     // violation 18 lines above 'Line continuation .* expected level should be 4'
     // violation 18 lines above 'Line continuation .* expected level should be 4'
}
