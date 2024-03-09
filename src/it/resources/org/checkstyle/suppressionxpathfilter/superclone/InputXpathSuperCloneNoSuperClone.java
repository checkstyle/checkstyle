package org.checkstyle.suppressionxpathfilter.superclone;

public class InputXpathSuperCloneNoSuperClone {
    class NoSuperClone
    {
        public Object clone() // warn
        {
            return null;
        }
    }
}
