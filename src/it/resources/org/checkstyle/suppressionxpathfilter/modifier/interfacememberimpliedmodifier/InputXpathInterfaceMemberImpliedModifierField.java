/*
InterfaceMemberImpliedModifier
violateImpliedAbstractMethod = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticField = (default)true
violateImpliedStaticNested = (default)true


*/

package org.checkstyle.suppressionxpathfilter.modifier.interfacememberimpliedmodifier;

public interface InputXpathInterfaceMemberImpliedModifierField {
    // violation below "Implied modifier 'final' should be explicit."
    public static String str = "random string";
}
