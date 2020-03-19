package org.checkstyle.suppressionxpathfilter.unnecessarysemicolonafteroutertypedeclaration;

interface SuppressionXpathRegressionUnnecessarySemicolonAfterOuterTypeDeclarationInnerTypes {

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
