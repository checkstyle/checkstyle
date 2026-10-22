/*
SuperClone


*/

package org.checkstyle.suppressionxpathfilter.coding.superclone;

class InputXpathSuperClonePlainAndSubclasses {
    public Object clone() { // violation "Method 'clone' should call 'super.clone'."
        return null;
    }
}
