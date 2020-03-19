package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

/**
 * Config = default
 */
public class InputUnnecessarySemicolonAfterOuterTypeDeclaration {

    enum innerEnum {

    }; // OK, nested enum

    @interface innerAnnotation {

    }; // OK, nested annotation

    interface innerInterface {

    }; // OK, nested interface

    class innerClass {

    };  // OK, nested class

}; // violation

enum e {

}; // violation

@interface an {

}; // violation

interface i {

}; // violation


enum okEnum {}

@interface okAnnotation {}

interface okInterface {}

class okClass {}
