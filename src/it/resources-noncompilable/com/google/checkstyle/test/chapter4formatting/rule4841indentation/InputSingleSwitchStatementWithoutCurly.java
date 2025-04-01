/**some javadoc.*/
public class InputSingleSwitchStatementWithoutCurly { 
  void testCorrectIndentation(int obj) {
    switch (obj) {
      case 0
          ->
            System.out.println("Test");
      case 1 ->
        System.out.println("TEST");
      case 2 -> {
        System.out.println("Test");
      }
      default -> System.out.println("test");
    }
  }
  
  void testIncorrectIndentation(int obj) {
    switch (obj) {
      case 1
      ->                            // violation '.* incorrect indentation level 6, expected .* 10.'
      System.out.println("Test");
      // violation above '.* incorrect indentation level 6, expected .* 12.'
    case 2 ->                        // violation '.* incorrect indentation level 4, expected .* 6.'
System.out.println("Test");          // violation '.* incorrect indentation level 0, expected .* 8.'
      default -> System.out.println("test");
    }
  }

  void testMixedCases(int obj) {
    switch (obj) {
      case 1 -> System.out.println("TEST");
      case 2
          -> System.out.println("Test");
      case 3 ->
                    System.out.println("Test");
      // violation above '.* incorrect indentation level 20, expected .* 8.'
      case 4 -> {
        System.out.println("Test");
      System.out.println("Another statement");
      // violation above '.* incorrect indentation level 6, expected .* 8.'
      }
      default -> System.out.println("test");
    }
  }
  
  private boolean testLineWrapping(int x, int y, int z) {
    return switch (x) {
      case 1 -> true;
      case 2 -> x != y
        && y != 5;
      case 3 ->
        y != 4
          && z != 2
          && y != z;
      default -> false;
    };
  }
}
