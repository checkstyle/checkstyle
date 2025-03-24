/**some javadoc.*/
public class InputSwitchAfterAssignmentOrSwitchRule {
  String testMethod1(int i) {
    String str = "";
    str =
        switch (i) {
          case 1 -> "one";
          case 2 -> "two";
          default -> "zero";
        };
    return s;
  }

  String testMethod2(int x, int y) {
    return switch (x) {
      case 1 ->
          switch (y) {
            case 2 -> "test";
            default -> "inner default";
          };
      default -> "outer default";
    };
  }

  void testMethod3Invalid(int num) {
    int odd = 0;
    odd =
      switch (num) {
      // violation above '.* incorrect indentation level 6, expected .* 8.'
        case 1, 3, 7 -> 1;
        // violation above '.* incorrect indentation level 8, expected .* 10.'
        case 2, 4, 6 -> 2;
        // violation above '.* incorrect indentation level 8, expected .* 10.'
        default -> 0;
        // violation above '.* incorrect indentation level 8, expected .* 10.'
      };
    // violation above '.* incorrect indentation level 6, expected .* 8.'
  }

  String testMethod4Invalid(int x, int y) {
    return switch (x) {
      case 1 ->
        switch (y) {
        // violation above '.* incorrect indentation level 8, expected .* 10.'
          case 2 -> "test";
            // violation above '.* incorrect indentation level 10, expected .* 12.'
            default -> "inner default";
          };
      default -> "outer default";
    };
  }
}
