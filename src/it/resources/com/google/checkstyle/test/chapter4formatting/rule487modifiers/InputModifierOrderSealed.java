package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

// violation 2 lines below ''public' modifier out of order with the JLS suggestions'
/** Some javadoc. */
sealed public class InputModifierOrderSealed permits
        InputModifierOrderSealed.Four,
        InputModifierOrderSealed.Three,
        InputModifierOrderSealed.Two {

  /** Some javadoc. */
  public final class Two extends InputModifierOrderSealed implements Five {}

  sealed class Four extends InputModifierOrderSealed {}

  sealed class Three extends InputModifierOrderSealed implements Five {

    // violation below ''private' modifier out of order with the JLS suggestions'
    sealed private class OtherSquare extends Three {}

    private final class OtherSquare2 extends OtherSquare {}
  }

  /** Some javadoc. */
  public final class FilledFour extends Four {}

  // violation below ''abstract' modifier out of order with the JLS suggestions'
  sealed abstract interface Five permits Three, Two {}
}
