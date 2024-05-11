/*
HideUtilityClassConstructor
ignoreAnnotatedBy = Skip, SkipWithParam, SkipWithAnnotationAsParam

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

@Skip
public class InputHideUtilityClassConstructorIgnoreAnnotationBy {
  public static void func() {}
}

@SkipWithParam(name = "tool1")
class ToolClass1 {
  public static void func() {}
}

@SkipWithAnnotationAsParam(skip = @Skip)
class ToolClass2 {
  public static void func() {}
}

@CommonAnnot
@Skip
class ToolClass3 {
  public static void func() {}
}

@CommonAnnot // violation
class ToolClass4 {
  public static void func() {}
}


@interface Skip {}

@interface SkipWithParam {
  String name();
}

@interface SkipWithAnnotationAsParam {
  Skip skip();
}

@interface CommonAnnot {}
