/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.List;
import java.util.function.Predicate;

public class InputOneStatementPerLineSwitchAsExpression {

    List<String> getThrowsTrees(Object input) {
        return getBlockTags(input,
                kind -> switch (kind) {
                    case "EXCEPTION", "THROWS" -> true;
                    default -> false;
                },
                String.class);
    }

    List<String> getParamTrees(Object input) {
        return getBlockTags(input,
                kind -> switch (kind) {
                    case "PARAM", "TYPE_PARAM" -> true;
                    default -> false;
                },
                String.class);
    }

    List<String> getReturnTrees(Object input) {
        return getBlockTags(input,
                kind -> switch (kind) {
                    case "RETURN" -> true;
                    default -> false;
                },
                String.class);
    }

    List<String> getSeeOrSinceTrees(Object input) {
        return getBlockTags(input,
                kind -> switch (kind) {
                    case "SEE", "SINCE", "AUTHOR" -> true;
                    default -> false;
                },
                String.class);
    }

    int classifyTag(String kind) {
        return switch (kind) {
            case "EXCEPTION", "THROWS" -> 1;
            case "PARAM", "TYPE_PARAM" -> 2;
            case "RETURN" -> 3;
            case "SEE", "SINCE", "AUTHOR" -> 4;
            default -> 0;
        };
    }

    <T> List<T> getBlockTags(Object input, Predicate<String> tagFilter, Class<T> type) {
        return List.of();
    }
}
