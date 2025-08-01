package org.checkstyle.checks.suppressionxpathfilter.superclone;

class InputXpathSuperClonePlainAndSubclasses {
    public Object clone() { // warn
        return null;
    }
}
