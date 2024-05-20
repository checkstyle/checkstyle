//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.staticvariablename;

public class InputXpathStaticVariableNameNoAccessModifier {
    {
       static int NUM3; //warn
    }
 }
