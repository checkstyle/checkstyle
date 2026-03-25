/*
com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck
default property: max = 1
*/
package com.puppycrawl.tools.checkstyle.treewalker;
/*comment*/
public class InputTreeWalker {
}
// violation below,'Top-level class InputTreeWalkerInner has to reside in its own source file'
class InputTreeWalkerInner {
}
