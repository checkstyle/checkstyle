/*
HideUtilityClassConstructor
ignoreAnnotatedBy = java.lang.Deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

@Deprecated
public class InputHideUtilityClassConstructorIgnoreAnnotationByFullyQualifiedName { // violation, 'Utility classes should not have a public or default constructor.'
  public static void func() {}
}

@java.lang.Deprecated
class DeprecatedClass {
  public static void func() {}
}

@interface Deprecated {}
