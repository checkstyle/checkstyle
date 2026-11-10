/*
AnnotationOnSameLine
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputAnnotationOnSameLineTypecast {

    void cast(Object obj) {
        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        Object first = (@Ann
            String) obj;

        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        Runnable second = (Runnable & @Ann
            Serializable) () -> { };

        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        Runnable both = (@Ann
            // violation below 'Annotation 'Ann2' should be on the same line with its target.'
            Runnable & @Ann2
            Serializable) () -> { };

        Runnable valid = (@Ann Runnable & @Ann2 Serializable) () -> { };

        Runnable wrapped = (@Ann Runnable
            & @Ann2 Serializable) () -> { };
    }

    @Target(ElementType.TYPE_USE)
    @interface Ann {
    }

    @Target(ElementType.TYPE_USE)
    @interface Ann2 {
    }
}
