/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public class InputUnnecessarySemicolonAfterTypeMemberDeclaration {
    ; //violation

    {/*init block*/}; // violation

    static {}; // violation

    InputUnnecessarySemicolonAfterTypeMemberDeclaration(){}; // violation

    class B{}; // violation

    void method(){}; // violation

    interface aa{}; //violation

    enum aa1{}; // violation

    @interface anno {}; // violation

    int field;; //violation

    enum c{
        B,C;; // violation
    }

    void ignoreEmptyStatements(){
        int a = 10;;
    }
};
enum e {
    ;
    int enumField;
};
@interface an {
    ; //violation
};
interface i {
    ; //violation
};
@interface annotation {
    String value();; // violation
}
enum e1 {}
enum e2 {;}
enum e3 {E;}
enum e4 {E,;}
@interface an1 {}
interface i1 {}
class c {}
