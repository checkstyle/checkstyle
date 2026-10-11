package org.checkstyle.suppressionxpathfilter.modifier.classmemberimpliedmodifier;

public class InputXpathClassMemberImpliedModifierRecord {
    public record Point(int x, int y) { // warn
    }
}
