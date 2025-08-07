package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicolonafteroutertypedeclaration;

interface InputXpathUnnecessarySemicolonAfterOuterTypeDeclarationInnerTypes {

    class Inner1 {

    }; // OK

    enum Inner2 {

    }; // OK

    interface Inner3 {

    }; // OK

    @interface Inner4 {

    }; // OK

}; //warn

class NoSemicolonAfter {

}
