/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

public interface InputDefaultMethods { // ok

    default public void doSomething(){
        String.CASE_INSENSITIVE_ORDER.equals("Something done.");
    }

    public void doOneMoreThing();

}
