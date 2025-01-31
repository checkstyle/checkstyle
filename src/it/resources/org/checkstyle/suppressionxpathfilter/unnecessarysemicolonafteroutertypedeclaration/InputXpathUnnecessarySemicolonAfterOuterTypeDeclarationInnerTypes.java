package org.checkstyle.suppressionxpathfilter.unnecessarysemicolonafteroutertypedeclaration;

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
