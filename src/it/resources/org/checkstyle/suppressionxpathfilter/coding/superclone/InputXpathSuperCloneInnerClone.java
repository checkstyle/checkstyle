package org.checkstyle.suppressionxpathfilter.coding.superclone;

public class InputXpathSuperCloneInnerClone {
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
