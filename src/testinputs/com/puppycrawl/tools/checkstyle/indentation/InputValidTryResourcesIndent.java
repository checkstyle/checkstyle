package com.puppycrawl.tools.checkstyle.indentation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

// Forcing a commit
class InputValidTryResourcesIndent
{
    // Taken from JDK7 java.lang.Package src code.
    private static Manifest loadManifest(String fn) {
        try (FileInputStream fis = new FileInputStream(fn);
JarInputStream jis = new JarInputStream(fis, false))
        {
            return jis.getManifest();
        } catch (IOException e)
        {
            return null;
        }
    }
}
