/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocType">
      <property name="authorFormat" value="\S+@\S+"/>
      <property name="versionFormat" value="\d+\.\d+"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * 1. Email inside HTML element - valid.
 *
 * @author <a href="mailto:john@example.com">John Doe</a>
 * @version 1.0
 */
class InputJavadocTypeTagFormat {}

/**
 * 2. Plain text author followed by HTML email - valid.
 *
 * @author John Doe, <a href="mailto:john@example.com">email</a>
 * @version 1.0
 */
class InputJavadocTypeTagFormatValidMixedTextHtml {}

/**
 * 3. HTML email followed by plain text author - valid.
 *
 * @author <a href="mailto:john@example.com">email</a>, John Doe
 * @version 1.0
 */
class InputJavadocTypeTagFormatValidHtmlFirst {}

/**
 * 4. Plain text author matching email - valid.
 *
 * @author john@example.com
 * @version 1.0
 */
class InputJavadocTypeTagFormatValidPlainText {}

/**
 * 5. Version with HTML markup - valid.
 *
 * @author john@example.com
 * @version <b>1.0</b>
 */
class InputJavadocTypeTagFormatValidVersionMarkup {}

/**
 * 6. Author does not match pattern - invalid.
 *
 * @author <a href="http://example.com">John Doe</a>
 * @version 1.0
 */
// violation below 'tag @author must match pattern'
class InputJavadocTypeTagFormatInvalidAuthor {}

/**
 * 7. Version does not match pattern - invalid.
 *
 * @author john@example.com
 * @version <b>beta</b>
 */
// violation below 'tag @version must match pattern'
class InputJavadocTypeTagFormatInvalidVersion {}
