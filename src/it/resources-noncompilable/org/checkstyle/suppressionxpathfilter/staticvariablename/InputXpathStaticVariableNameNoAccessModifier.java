// non-compiled with javac: compilable with java21
package org.checkstyle.suppressionxpathfilter.staticvariablename;

public class InputXpathStaticVariableNameNoAccessModifier {
    {
       static int NUM3; //warn
    }
 }
