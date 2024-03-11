package org.checkstyle.suppressionxpathfilter.noclone;

public class InputXpathNoCloneOne {

    public Object clone() { return null; } // warn

    public <T> T clone(T t) { return null; }

}
