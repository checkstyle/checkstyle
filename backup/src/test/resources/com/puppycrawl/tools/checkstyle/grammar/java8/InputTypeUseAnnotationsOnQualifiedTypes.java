/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.awt.geom.Rectangle2D;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputTypeUseAnnotationsOnQualifiedTypes { // ok
    /* Causes parse failure */
    Rectangle2D.@Ann Double rect = null;

    /* Causes parse failure */
    public final Rectangle2D.@Ann Double getRect1() {
        return new Rectangle2D.Double();
    }

    /* Causes parse failure */
    public final Rectangle2D.Double getRect2() {
        return new Rectangle2D.@Ann Double();
    }

    /* Amazingly does not cause parse failure */
    public final Rectangle2D.Double getRect3() {
        Rectangle2D.@Ann Double rect = null;
        return rect;
    }
}

@Target({ ElementType.TYPE_USE })
@interface Ann {
}
