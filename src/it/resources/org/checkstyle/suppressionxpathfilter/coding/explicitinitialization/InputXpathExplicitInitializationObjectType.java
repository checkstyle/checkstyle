/*
ExplicitInitialization
onlyObjectReferences = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.explicitinitialization;

public class InputXpathExplicitInitializationObjectType {
    private int a;

    private Object bar = null; // violation 'Variable 'bar' explicitly initialized to 'null' (default value for its type).'
}
