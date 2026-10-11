/*
ExplicitInitialization
onlyObjectReferences = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.explicitinitialization;

public enum InputXpathExplicitInitializationEnum {
    A;
    int a = 0; // violation 'Variable 'a' explicitly initialized to '0' (default value for its type).'
}
