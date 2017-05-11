package com.puppycrawl.tools.checkstyle.utils;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.LineColumn;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
<<<<<<< HEAD
=======
import javax.sound.sampled.Line;
>>>>>>> ce2e1b46c279e0454a84b0830bc7a335b4cb69e7

/**
 * Tools for parsing block tags from a Javadoc comment.
 */
public class BlockTagUtils {

    /** Block tag pattern for a first line. */
    private static final Pattern BLOCK_TAG_PATTERN_FIRST_LINE = Pattern.compile(
        "/\\*{2,}\\s*@(\\p{Alpha}+)\\s");

    /** Block tag pattern. */
    private static final Pattern BLOCK_TAG_PATTERN = Pattern.compile(
        "^\\s*\\**\\s*@(\\p{Alpha}+)\\s");
<<<<<<< HEAD

=======
>>>>>>> ce2e1b46c279e0454a84b0830bc7a335b4cb69e7
    /**
     * Extract the block tags from a Javadoc comment.
     */
    static ImmutableList<TagUtils.Tag> extractBlockTags(String[] text) {
        ImmutableList.Builder<TagUtils.Tag> tags = ImmutableList.builder();

        for (int i = 0; i < text.length; i++ ) {
            // Starting lines of a comment have a different first line pattern.
            boolean firstLine = i == 0;
            Pattern pattern = firstLine ? BLOCK_TAG_PATTERN_FIRST_LINE : BLOCK_TAG_PATTERN;

            String line = text[i];
            Matcher tagMatcher = pattern.matcher(line);

            if (tagMatcher.find()) {
                final String tagName = tagMatcher.group(1);

                int colNum = tagMatcher.start(1) - 1; // offset of one for the @ character
                int lineNum = i + 1;

                String remainder = line.substring(tagMatcher.end(1));
                String tagValue = remainder.trim();

<<<<<<< HEAD
                // Handle the case where we're on the last line of a Javadoc comment.
                if (tagValue.endsWith("*/")) {
                    tagValue = tagValue.substring(0, tagValue.length() - "*/".length()).trim();
                }

=======
>>>>>>> ce2e1b46c279e0454a84b0830bc7a335b4cb69e7
                LineColumn position = new LineColumn(lineNum, colNum);
                tags.add(TagUtils.Tag.create(tagName, tagValue, position));
            }
        }

        return tags.build();
    }
}
