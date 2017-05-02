package com.puppycrawl.tools.checkstyle.utils;

import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.LineColumn;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nnaze on 4/28/17.
 */
public class InlineTagUtils {

    /**
     * Inline tag pattern.
     */
    private static final Pattern INLINE_TAG_PATTERN = Pattern.compile(
            ".*?\\{@(\\p{Alpha}+)\\b(.*?)\\}", Pattern.DOTALL);

    private static final Pattern JAVADOC_PREFIX_PATTERN = Pattern.compile("^\\s*\\*", Pattern.MULTILINE);

    static ImmutableList<TagUtils.Tag> extractInlineTags(String[] text) {
        for (String line : text) {
            Preconditions.checkState(!line.contains("\n"), "comment lines cannot contain newlines");
        }

        final String commentText = convertLinesToString(text);
        final Matcher inlineTagMatcher = INLINE_TAG_PATTERN.matcher(commentText);

        ImmutableList.Builder<TagUtils.Tag> tagsBuilder = ImmutableList.builder();

        while (inlineTagMatcher.find()) {
            final String tagName = inlineTagMatcher.group(1);

            // Remove the leading asterisks (in case the tag spans a line) and collapse
            // the whitespace.
            String matchedTagValue = inlineTagMatcher.group(2);
            matchedTagValue = removeLeadingJavaDoc(matchedTagValue);
            matchedTagValue = collapseWhitespace(matchedTagValue);

            final String tagValue = matchedTagValue;

            int startIndex = inlineTagMatcher.start(1);
            LineColumn position = getLineColumnOfIndex(commentText,
                /* correct start index offset */
                    startIndex - 1);

            tagsBuilder.add(TagUtils.Tag.create(tagName, tagValue, position));
        }

        return tagsBuilder.build();
    }

    @VisibleForTesting
    static String convertLinesToString(String[] text) {
        StringBuffer buffer = new StringBuffer();
        for (String line : text) {
            buffer.append(line);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    @VisibleForTesting
    static LineColumn getLineColumnOfIndex(String source, int index) {
        Preconditions.checkArgument(index >= 0, "index must be positive");
        Preconditions.checkArgument(index < source.length(), "index must be less than length of source");

        String precedingText = source.subSequence(0, index).toString();
        String[] precedingLines = precedingText.split("\\n");
        String lastLine = precedingLines[precedingLines.length - 1];
        return new LineColumn(precedingLines.length, lastLine.length());
    }


    @VisibleForTesting
    static String collapseWhitespace(String str) {
        return str.replaceAll("\\s+", " ").trim();
    }

    public static String removeLeadingJavaDoc(String source) {
        Matcher matcher = JAVADOC_PREFIX_PATTERN.matcher(source);
        return matcher.replaceAll("");
    }
}
