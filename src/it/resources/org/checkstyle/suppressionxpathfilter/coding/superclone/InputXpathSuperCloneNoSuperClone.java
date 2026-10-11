package org.checkstyle.suppressionxpathfilter.coding.superclone;

public class InputXpathSuperCloneNoSuperClone {
    class NoSuperClone
    {
        public Object clone() // warn
        {
            return null;
        }
    }
}
