package org.checkstyle.checks.suppressionxpathfilter.avoiddoublebraceinitialization;

import java.util.*;

public class InputXpathAvoidDoubleBraceInitializationClassFields {
    List list = new ArrayList<Object>() { //warn
        {}
    };
}
