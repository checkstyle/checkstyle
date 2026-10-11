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

}; //warn

class NoSemicolonAfter {

}
