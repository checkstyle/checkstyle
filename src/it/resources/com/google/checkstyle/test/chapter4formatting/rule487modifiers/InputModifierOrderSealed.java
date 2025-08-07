package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

// violation 2 lines below ''public' modifier out of order with the JLS suggestions'
/** some javadoc. */
sealed public class InputModifierOrderSealed
    permits Two, Three, Four {
}

// violation below 'Top-level class Two has to reside in its own source file'
final class Two extends InputModifierOrderSealed
        implements Five {
}

// violation below 'Top-level class Four has to reside in its own source file'
sealed class Four extends InputModifierOrderSealed
    implements Cloneable {
}

// violation below 'Top-level class Three has to reside in its own source file'
sealed class Three extends InputModifierOrderSealed
        implements Five {
  // violation below ''private' modifier out of order with the JLS suggestions'
  sealed private class OtherSquare extends Three {
  }

  private final class OtherSquare2 extends OtherSquare {
  }
}

// violation below 'Top-level class FilledFour has to reside in its own source file'
final class FilledFour extends Four {
}

// violation 2 lines below 'Top-level class Five has to reside in its own source file'
// violation below ''abstract' modifier out of order with the JLS suggestions'
sealed abstract interface Five permits Two, Three {
}
