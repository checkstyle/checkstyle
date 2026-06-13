public class InputEmptyLineSeparatorMutationTest {

    int a;


    int b; // should be OK (has empty line before)

    int c; // violation (no empty line before)
    List<String> d; // no empty line before, generic type forces loop exhaustion

    List<String> e; // no empty line before, generic type forces loop exhaustion
    List<

            String> f; // blank line inside, no violation expected
}
