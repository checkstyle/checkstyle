package org.checkstyle.suppressionxpathfilter.prefermethodreference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SuppressionXpathRegressionPreferMethodReference {
    Function<String, String> good = arg -> arg.concat(arg);
    Function<Integer, List> bad = arg -> new ArrayList(arg); //warn
}
