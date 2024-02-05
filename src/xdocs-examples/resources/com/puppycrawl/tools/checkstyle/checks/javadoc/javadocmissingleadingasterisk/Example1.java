/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMissingLeadingAsterisk"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

// xdoc section -- start
/**
 * Valid Java-style comment.
 *
 * <pre>
 *   int value = 0;
 * </pre>
 */
class JavaStyle {}

/** Valid Scala-style comment.
  * Some description here.
  **/
class ScalaStyle {}

/** **
  * Asterisks on first and last lines are optional.
  * */
class Asterisks {}

/** No asterisks are required for single-line comments. */
class SingleLine {}

/** // violation below

 */
class BlankLine {}

/** Wrapped
    single-line comment */ // violation
class Wrapped {}

/**
  * <pre>
    int value; // violation
  * </pre>
  */
class Example1 {}
// xdoc section -- end
