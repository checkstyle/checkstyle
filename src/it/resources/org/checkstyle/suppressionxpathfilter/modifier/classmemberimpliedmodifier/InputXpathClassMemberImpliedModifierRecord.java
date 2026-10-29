/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

package org.checkstyle.suppressionxpathfilter.modifier.classmemberimpliedmodifier;

public class InputXpathClassMemberImpliedModifierRecord {
    // violation below "Implied modifier 'static' should be explicit."
    public record Point(int x, int y) {
    }
}
