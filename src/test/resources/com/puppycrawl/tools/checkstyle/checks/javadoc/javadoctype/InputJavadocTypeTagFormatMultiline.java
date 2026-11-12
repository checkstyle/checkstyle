/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="authorFormat" value="First Line\s+Second Line"/>
      <property name="versionFormat" value="^1\.\s+0$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * 1. Multiline author with single asterisk matching regex across newline - valid.
 *
 * @author First Line
 *         Second Line
 * @version 1.
 *          0
 */
class InputJavadocTypeTagFormatMultiline {}

/**
 * 2. Multiline author with multiple asterisks matching regex across newline - valid.
 *
 ** @author First Line
 **        Second Line
 ** @version 1.
 **         0
 */
class InputJavadocTypeTagFormatMultilineDoubleAsterisk {}

/**
 * 3. Literal asterisk inside content does not match pattern - invalid.
 *
 * @author First Line * Second Line
 * @version 1.0
 */
class InputJavadocTypeTagFormatMultilineInvalid {}
// 2 violations above:
//    'tag @author must match pattern'
//    'tag @version must match pattern'
