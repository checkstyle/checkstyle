//non-compiled with javac: Compilable with Java14
package org.checkstyle.suppressionxpathfilter.staticvariablename;

public class InputXpathStaticVariableNameNoAccessModifier {
    {
       static int NUM3; //warn
    }
 }
