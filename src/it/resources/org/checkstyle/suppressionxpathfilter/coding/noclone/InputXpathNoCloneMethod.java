/*
NoClone


*/

package org.checkstyle.suppressionxpathfilter.coding.noclone;

public class InputXpathNoCloneMethod {

    public Object clone() { return null; } // violation 'Avoid using clone method.'

    public <T> T clone(T t) { return null; }

}
