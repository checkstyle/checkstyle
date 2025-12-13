package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

/** Some javadoc. */
public sealed class InputFormattedModifierOrderSealed
    permits InputFormattedModifierOrderSealed.Four1,
        InputFormattedModifierOrderSealed.Three1,
        InputFormattedModifierOrderSealed.Two1 {
  final class Two1 extends InputFormattedModifierOrderSealed implements Five1 {}

  sealed class Four1 extends InputFormattedModifierOrderSealed {}

  sealed class Three1 extends InputFormattedModifierOrderSealed implements Five1 {

    private sealed class OtherSquare3 extends Three1 {}

    private final class OtherSquare4 extends OtherSquare3 {}
  }

  /** Some javadoc. */
  public final class FilledFour1 extends Four1 {}

  abstract sealed interface Five1 permits Two1, Three1 {}
}
