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

public interface InputXpathInterfaceMemberImpliedModifierInner {
    // violation below "Implied modifier 'static' should be explicit."
    public interface Data {}
}
