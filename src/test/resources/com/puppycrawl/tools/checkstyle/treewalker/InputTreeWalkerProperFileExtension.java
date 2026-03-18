/*
com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

*/

package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerProperFileExtension {

  public static final int k = 5 + 4;
  // violation above, "Name 'k' must match pattern"
}
