/*
HideUtilityClassConstructor
ignoreAnnotatedBy = java.lang.Deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// violation below 'Utility classes should not have a public or default constructor.'
@Deprecated
public class InputHideUtilityClassConstructorIgnoreAnnotationByFullyQualifiedName {
  public static void func() {}
}

@java.lang.Deprecated
class DeprecatedClass {
  public static void func() {}
}

@interface Deprecated {}
