/*
ParenPad
option = (default)nospace
tokens = ENUM_CONSTANT_DEF, CTOR_DEF, CTOR_CALL


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForEnum extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public enum ErrorType {
        ROOM_ALREADY_EXISTS,
        USER_ALREADY_EXISTS,
        NO_SUCH_ROOM_EXISTS
    }

    public InputParenPadForEnum(String description, ErrorType type) {
        super(description);
    }
}
