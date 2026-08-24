/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocEndCommentDelimiter"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

// xdoc section - start
/**
 * Valid Javadoc.
 */
class ValidMultiLine {}

/** Valid single-line Javadoc. */
class ValidSingleLine {}

/*
 * Normal block comments are ignored.
 **/
class BlockComment {}

// violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
/**
 * Invalid Javadoc.
 **/
class ExtraAsterisk {}

// violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
/** Invalid single-line Javadoc. **/
class SingleLineExtraAsterisk {}

// violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
/**
 * Invalid Javadoc.
 ***/
class MultipleExtraAsterisks {}
// xdoc section - end
