package com.puppycrawl.tools.checkstyle.utils;

import com.google.auto.value.AutoValue;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
<<<<<<< HEAD
 * Common utilities for paring Javadoc tags.
 */
class TagUtils {

    /**
     * Value object for storing data about a parsed tag.
     */
=======
 * Created by nnaze on 5/2/17.
 */
public class TagUtils {
>>>>>>> ce2e1b46c279e0454a84b0830bc7a335b4cb69e7
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
