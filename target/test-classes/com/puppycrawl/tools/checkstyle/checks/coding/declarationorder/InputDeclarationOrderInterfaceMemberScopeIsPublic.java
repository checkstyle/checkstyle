/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

public interface InputDeclarationOrderInterfaceMemberScopeIsPublic {

  public static final int explicitPublicField1 = 0;

  static final int implicitPublicField1 = 0;

  public static final int explicitPublicField2 = 0;

  void method();

  static final int implicitPublicField2 = 0;// violation 'Static variable definition in wrong order'

}
