package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0

public class InputIndentationClassDeclarationWrapped {//indent:0 exp:0

  public class A {};//indent:2 exp:2

  public//indent:2 exp:2
      class B {};//indent:6 exp:6

  final//indent:2 exp:2
      static class C {};//indent:6 exp:6

  abstract//indent:2 exp:2
      strictfp class G {};//indent:6 exp:6

  public//indent:2 exp:2
      class T {};//indent:6 exp:6

  public//indent:2 exp:2
      @X class D {};//indent:6 exp:6

  abstract @Y//indent:2 exp:2
      @Z strictfp class E {};//indent:6 exp:6

  @X class F {};//indent:2 exp:2

  @interface X{}//indent:2 exp:2
  @interface Y{}//indent:2 exp:2
  @interface Z{}//indent:2 exp:2
}//indent:0 exp:0
