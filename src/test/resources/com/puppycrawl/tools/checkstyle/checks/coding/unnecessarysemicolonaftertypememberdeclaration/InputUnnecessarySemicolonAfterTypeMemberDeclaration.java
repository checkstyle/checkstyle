/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public class InputUnnecessarySemicolonAfterTypeMemberDeclaration {
    ; // violation, 'Unnecessary semicolon.'

    {/*init block*/}; // violation, 'Unnecessary semicolon.'

    static {}; // violation, 'Unnecessary semicolon.'

    InputUnnecessarySemicolonAfterTypeMemberDeclaration(){}; // violation, 'Unnecessary semicolon.'

    class B{}; // violation, 'Unnecessary semicolon.'

    void method(){}; // violation, 'Unnecessary semicolon.'

    interface aa{}; // violation, 'Unnecessary semicolon.'

    enum aa1{}; // violation, 'Unnecessary semicolon.'

    @interface anno {}; // violation, 'Unnecessary semicolon.'

    int field;; // violation, 'Unnecessary semicolon.'

    enum c{
        B,C;; // violation, 'Unnecessary semicolon.'
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
    ; // violation, 'Unnecessary semicolon.'
};
interface i {
    ; // violation, 'Unnecessary semicolon.'
};
@interface annotation {
    String value();; // violation, 'Unnecessary semicolon.'
}
enum e1 {}
enum e2 {;}
enum e3 {E;}
enum e4 {E,;}
@interface an1 {}
interface i1 {}
class c {}
