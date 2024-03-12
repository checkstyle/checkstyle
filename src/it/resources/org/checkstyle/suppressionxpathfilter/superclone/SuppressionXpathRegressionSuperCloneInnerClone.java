package org.checkstyle.suppressionxpathfilter.superclone;

public class SuppressionXpathRegressionSuperCloneInnerClone {
    class InnerClone
    {
        public Object clone() // warn
        {
            class Inner
            {
                public Object clone() throws CloneNotSupportedException
                {
                    return super.clone();
                }
            }
            return null;
        }
    }
}
