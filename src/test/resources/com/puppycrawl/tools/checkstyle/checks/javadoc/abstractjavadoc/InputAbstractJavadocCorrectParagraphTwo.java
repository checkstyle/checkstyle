/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @deprecated, @exception, @param, @return, \
           @see, @serial, @serialData, @serialField, @since, @throws, @version

com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * Some Javadoc.
 * 
 * <p>Some Javadoc.
 * 
 */
class InputAbstractJavadocCorrectParagraphTwo {}

/*
 *  This comment has paragraph without '<p>' tag. // ok
 *  It's fine, because this is plain comment.
 */
class ClassWithPlainComment {}
