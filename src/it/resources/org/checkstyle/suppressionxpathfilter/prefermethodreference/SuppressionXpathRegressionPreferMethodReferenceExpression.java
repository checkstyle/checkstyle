package org.checkstyle.suppressionxpathfilter.prefermethodreference;

import java.util.function.DoubleToIntFunction;

public class SuppressionXpathRegressionPreferMethodReferenceExpression {
    DoubleToIntFunction lambda = arg -> Double.valueOf(Math.PI).compareTo(arg); //warn
}
