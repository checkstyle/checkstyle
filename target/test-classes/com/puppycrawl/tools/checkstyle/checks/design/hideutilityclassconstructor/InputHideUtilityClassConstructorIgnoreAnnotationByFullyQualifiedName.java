/*
HideUtilityClassConstructor
ignoreAnnotatedBy = java.lang.Deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

@Deprecated // violation, should not have a public or default constructor
public class InputHideUtilityClassConstructorIgnoreAnnotationByFullyQualifiedName {
  public static void func() {}
}

@java.lang.Deprecated
class DeprecatedClass {
  public static void func() {}
}

@interface Deprecated {}
