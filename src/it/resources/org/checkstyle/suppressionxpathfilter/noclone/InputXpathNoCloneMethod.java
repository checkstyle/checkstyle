package org.checkstyle.suppressionxpathfilter.noclone;

public class InputXpathNoCloneMethod {

    public Object clone() { return null; } // warn

    public <T> T clone(T t) { return null; }

}
