/*
SuperClone


*/

package org.checkstyle.suppressionxpathfilter.coding.superclone;

public class InputXpathSuperCloneNoSuperClone {
    class NoSuperClone
    {
        public Object clone() // violation "Method 'clone' should call 'super.clone'."
        {
            return null;
        }
    }
}
