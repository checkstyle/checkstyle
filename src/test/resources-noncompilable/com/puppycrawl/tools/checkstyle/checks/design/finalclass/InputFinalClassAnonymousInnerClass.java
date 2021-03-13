//compilable only if present in the default package.

/* Config:
 *
 * default
 */

public class InputFinalClassAnonymousInnerClass { // ok
    public static final Test ONE = new InputFinalClassAnonymousInnerClass() {
        @Override
        public int value() {
            return 1;
        }
    };

    private InputFinalClassAnonymousInnerClass() {
    }

    public int value() {
        return 0;
    }
}
