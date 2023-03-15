/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Represents either a {@link Field}, a {@link Method} or a {@link Constructor}.
 * Provides convenience methods such as {@link #isPublic} and {@link #isPackagePrivate}.
 *
 */
class InputCommentsIndentationNoNpe { // ok

}
/* The Check should not throw NPE here! */
