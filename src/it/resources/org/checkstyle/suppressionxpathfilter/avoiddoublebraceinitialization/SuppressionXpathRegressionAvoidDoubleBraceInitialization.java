package org.checkstyle.suppressionxpathfilter.avoiddoublebraceinitialization;

import java.util.*;

public class SuppressionXpathRegressionAvoidDoubleBraceInitialization {
    List list = new ArrayList<Object>() { //warn
        {}
    };
}
