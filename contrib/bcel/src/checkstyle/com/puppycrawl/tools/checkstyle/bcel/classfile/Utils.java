package com.puppycrawl.tools.checkstyle.bcel.classfile;

import java.util.Set;

import org.apache.bcel.classfile.FieldOrMethod;

import com.puppycrawl.tools.checkstyle.api.Scope;

/**
 * Utility methods for BCEL classfile package
 * @author Rick Giles
 */
public class Utils
{
    /**
     * Determines whether the declared scope of a field or method is in
     * a set of scopes.
     * @param aFieldOrMethod the field or method to test.
     * @param aScopes the set of scopes to test against.
     * @return true if the declared scope of aFieldOrMethod is in aScopes.
     */
    public static boolean inScope(FieldOrMethod aFieldOrMethod, Set aScopes)
    {
        if (aFieldOrMethod.isPrivate()) {
            return (aScopes.contains(Scope.PRIVATE));
        }
        else if (aFieldOrMethod.isProtected()) {
            return (aScopes.contains(Scope.PROTECTED));
        }
        else if (aFieldOrMethod.isPublic()) {
            return (aScopes.contains(Scope.PUBLIC));
        }
        else {
            return (aScopes.contains(Scope.PACKAGE));
        }
    }


}
