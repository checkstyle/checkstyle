/*
SuperClone


*/

package org.checkstyle.suppressionxpathfilter.coding.superclone;

public class InputXpathSuperCloneInnerClone {
    class InnerClone
    {
        public Object clone() // violation "Method 'clone' should call 'super.clone'."
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
