/*
AvoidDoubleBraceInitialization


*/

package org.checkstyle.suppressionxpathfilter.coding.avoiddoublebraceinitialization;

import java.util.*;

public class InputXpathAvoidDoubleBraceInitializationClassFields {
    List list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
        {}
    };
}
