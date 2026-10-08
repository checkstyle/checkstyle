/*
SuppressWithPlainTextCommentFilter
offCommentFormat = CSOFF
onCommentFormat = CSON
checkFormat = \\.IndentationCheck$
messageFormat = (default)(null)
idFormat = (default)(null)


com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
throwsIndent = (default)4


com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

// CSOFF
    // violation 'Comment has incorrect indentation level 4, expected is 0, indentation should be'
@SuppressWarnings("all")
public class InputSuppressWithPlainTextCommentFilterIndentationAnchored {
   int wrongIndent; // filtered violation ''member def type' has incorrect indentation level 3'
}
// CSON
