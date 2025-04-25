/**some javadoc.*/
public class InputSwitchAfterAssignmentOrSwitchRuleCorrect {
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
          case 1, 3, 7 -> 1;
          case 2, 4, 6 -> 2;
          default -> 0;
        };
  }

  String testMethod4Invalid(int x, int y) {
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
