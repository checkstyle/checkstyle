package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.util.function.Function;

class InputJavadocTagContinuationIndentationGuavaFalsePositive {

    /**
     * This class implements the GWT serialization of {@link HashMultimap}.
     *
     * @author Jord Sonneveld
     * 
     */
  public static <T extends Enum<T>> Function<String, T> valueOfFunction(
      Class<T> enumClass) {
    return null;
  }
}
