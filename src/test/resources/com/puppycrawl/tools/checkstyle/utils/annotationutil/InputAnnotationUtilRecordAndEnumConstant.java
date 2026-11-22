package com.puppycrawl.tools.checkstyle.utils.annotationutil;

public class InputAnnotationUtilRecordAndEnumConstant {

    public static final int VALUE = 5;

    public record Point(@Deprecated int x, int y) {
    }

    public enum Old {
        @Deprecated
        LEGACY,
        CURRENT
    }
}
