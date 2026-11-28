package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public sealed interface InputRareSyntaxNoExceptionSealed permits
        InputRareSyntaxNoExceptionSealed.First, InputRareSyntaxNoExceptionSealed.Second {
    String name();

    final class First implements InputRareSyntaxNoExceptionSealed {
        @Override
        public String name() {
            return "first";
        }
    }

    non-sealed class Second implements InputRareSyntaxNoExceptionSealed {
        @Override
        public String name() {
            return "second";
        }
    }
}
