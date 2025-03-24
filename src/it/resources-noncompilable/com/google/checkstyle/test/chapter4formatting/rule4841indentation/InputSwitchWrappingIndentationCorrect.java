/**some javadoc.*/
public class InputSwitchWrappingIndentationCorrect {
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
          case 1, 3, 7 -> 1;
          case 2, 4, 6 -> 2;
          default -> 0;
        };
  }

  String testMethod5Invalid(int x, int y) {
    return switch (x) {
      case 1 ->
          switch (y) {
            case 2 -> "test";
            default -> "inner default";
          };
      default -> "outer default";
    };
  }
}
