/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

@Deprecated // violation 'should not be line-wrapped'
public class
  InputNoLineWrapBad4 {

    private String field;

    @Deprecated // violation 'should not be line-wrapped'
    public
      InputNoLineWrapBad4(String field) {
        this.field = field;
    }

    @Deprecated // violation 'should not be line-wrapped'
    public void
      method1() {}

    @Deprecated
    public void method2() {}
}

@Deprecated // violation 'should not be line-wrapped'
interface
  InterfaceNoLineWrapBad3 {}

@Deprecated // violation 'should not be line-wrapped'
enum
  EnumNoLineWrapBad3 {}

@Deprecated // violation 'should not be line-wrapped'
record
  RecordNoLineWrapBad3(int field) {

    @Deprecated // violation 'should not be line-wrapped'
    public
      RecordNoLineWrapBad3 {}
}
