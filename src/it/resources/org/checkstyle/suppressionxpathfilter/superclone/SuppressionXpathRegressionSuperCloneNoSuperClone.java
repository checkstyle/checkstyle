package org.checkstyle.suppressionxpathfilter.superclone;

public class SuppressionXpathRegressionSuperCloneNoSuperClone {
    class NoSuperClone
    {
        public Object clone() // warn
        {
            return null;
        }
    }
}
