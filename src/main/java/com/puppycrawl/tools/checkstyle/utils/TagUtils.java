package com.puppycrawl.tools.checkstyle.utils;

import com.google.auto.value.AutoValue;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * Created by nnaze on 5/2/17.
 */
public class TagUtils {
    @AutoValue
    abstract static class Tag {
        abstract String name();

        abstract String value();

        abstract LineColumn position();

        static Tag create(String name, String value, LineColumn position) {
            return new AutoValue_TagUtils_Tag(name, value, position);
        }
    }
}
