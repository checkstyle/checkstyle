public class InputValidAssignIndent
{
    void foo(String[] args)
    {
        i = 1 +
            2 +
            3;
        String line = mIndentCheck[
            getLineNo()];
        String line1 =
            getLineNo();
        line1 =
            getLineNo();
        int i 
            =
            1;
        i = 3;

        brace =
            (candidate == SLIST)
            ? candidate : null;

        AnInterfaceFooWithALongName f =
            new AnInterfaceFooWithALongName() {
                public void bar() {
                }
            };

        AnInterfaceFooWithALongName f1
            = new AnInterfaceFooWithALongName() {
                public void bar() {
                }
            };

        function.lastArgument() = parameters;
        function.lastArgument()
            =
            parameters;
        // TODO: add more testing
    }

    private interface AnInterfaceFooWithALongName {
        void bar();
    }
}
