/*
UnnecessarySemicolonAfterOuterTypeDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

public class InputUnnecessarySemicolonAfterOuterTypeDeclaration {

    enum innerEnum {

    }; // ok, nested enum

    @interface innerAnnotation {

    }; // ok, nested annotation

    interface innerInterface {

    }; // ok, nested interface

    class innerClass {

    };  // ok, nested class

}; // violation 'Unnecessary semicolon'

enum e {

}; // violation 'Unnecessary semicolon'

@interface an {

}; // violation 'Unnecessary semicolon'

interface i {

}; // violation 'Unnecessary semicolon'


enum okEnum {}

@interface okAnnotation {}

interface okInterface {}

class okClass {}
