package com.puppycrawl.tools.checkstyle.checks.upperell;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class InputUpperEllInstanceInitializers {
    /* Boolean instantiation in a non-static initializer */
    {
        Boolean x = new Boolean(true);
        Boolean[] y = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    }

    // empty instance initializer
    {
    }

    void otherInstantiations()
    {
        // instantiation of classes in the same package
        Object o1 = new InputUpperEllBraces();
        Object o2 = new InputUpperEllModifier();
        // classes in another package with .* import
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        File f = new File("/tmp");
        // classes in another package with explicit import
        Dimension dim = new Dimension();
        Color col = new Color(0, 0, 0);
    }
}
