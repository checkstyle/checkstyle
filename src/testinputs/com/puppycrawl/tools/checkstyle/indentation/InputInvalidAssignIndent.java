public class InputInvalidAssignIndent
{
    void foo(String[] args)
    {
        String line = mIndentCheck[
          getLineNo()];
        String line1 =
          getLineNo();
        line1 =
          getLineNo();
        int i 
         =
          1;
        // TODO: this should be illegal.
        i =
            3;
        // TODO: add more testing
    }

}
