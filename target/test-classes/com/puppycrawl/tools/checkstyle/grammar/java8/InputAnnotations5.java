/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;



public class InputAnnotations5 {


    void monitorTemperature() throws @Critical Exception {  }
    void monitorTemperature2() throws NullPointerException, @Critical Exception {  }

    @Target(ElementType.TYPE_USE)
    @interface Critical {

    }
}
