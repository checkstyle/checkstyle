package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

public class InputGenericWhitespaceNested {
    interface IntEnum { /*inner enum*/}

    interface NumberEnum<T> { /*inner enum*/}

    static class IntEnumValue implements IntEnum, NumberEnum<Integer> {}

    static class IntEnumValueType<E extends Enum<E
>& IntEnum & NumberEnum<E>> {}
}
