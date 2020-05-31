package org.checkstyle.suppressionxpathfilter.hiddenfield;

import java.util.Arrays;
import java.util.List;

public class SuppressionXpathRegressionHiddenFieldOne {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = Integer.valueOf(1);
    {
        numbers.forEach((Integer value) -> String.valueOf(value)); //warn
    }
}
