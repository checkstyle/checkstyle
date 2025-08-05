package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

/** some javadoc. */
public sealed class InputFormattedModifierOrderSealed
    permits Two1, Three1, Four1 {
}

// violation below 'Top-level class Two1 has to reside in its own source file'
final class Two1 extends InputFormattedModifierOrderSealed
        implements Five1 {
}

// violation below 'Top-level class Four1 has to reside in its own source file'
sealed class Four1 extends InputFormattedModifierOrderSealed
    implements Cloneable
    permits TransparentFour1, FilledFour1 {
}

// violation below 'Top-level class TransparentFour1 has to reside in its own source file'
final class TransparentFour1 extends Four1 {
}

// violation below 'Top-level class Three1 has to reside in its own source file'
sealed class Three1 extends InputFormattedModifierOrderSealed
        implements Five1 {
  private sealed class OtherSquare3 extends Three1 permits OtherSquare4 {
  }

  private final class OtherSquare4 extends OtherSquare3 {
  }
}

// violation below 'Top-level class FilledFour1 has to reside in its own source file'
final class FilledFour1 extends Four1 {
}

// violation below 'Top-level class Five1 has to reside in its own source file'
abstract sealed interface Five1 permits Two1, Three1 {
}
