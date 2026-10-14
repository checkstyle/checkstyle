/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package org.checkstyle.suppressionxpathfilter.naming.abstractclassname;

public class InputXpathAbstractClassNameNoModifier {
    // violation below 'Class 'AbstractMyClass' must be declared as 'abstract'.'
    class AbstractMyClass {
    }
}
