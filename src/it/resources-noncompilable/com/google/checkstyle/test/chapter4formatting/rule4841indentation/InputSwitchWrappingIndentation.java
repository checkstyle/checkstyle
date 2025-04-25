/**some javadoc.*/
public class InputSwitchWrappingIndentation {
  String testMethod1(int i) {
    String name = "";
    name =
        switch (i) {
          case 1 -> "one";
          case 2 -> "two";
          default -> "zero";
        };
    return name;
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

  String testMethod3(int x, int y) {
    return operations.stream()
        .map(
            op ->
                switch (op) {
                  case 1 -> "test";
                  default -> "TEST";
                })
        .findFirst().orElse("defaultValue");
  }

  void testMethod4Invalid(int num) {
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

  String testMethod5Invalid(int x, int y) {
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
