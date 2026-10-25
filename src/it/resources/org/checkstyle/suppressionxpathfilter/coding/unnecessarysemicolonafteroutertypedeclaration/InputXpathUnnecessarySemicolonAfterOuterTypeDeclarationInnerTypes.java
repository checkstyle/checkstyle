/*
UnnecessarySemicolonAfterOuterTypeDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicolonafteroutertypedeclaration;

interface InputXpathUnnecessarySemicolonAfterOuterTypeDeclarationInnerTypes {

    class Inner1 {

    };

    enum Inner2 {

    };

    interface Inner3 {

    };

    @interface Inner4 {

    };

}; // violation 'Unnecessary semicolon\.'

class NoSemicolonAfter {

}
