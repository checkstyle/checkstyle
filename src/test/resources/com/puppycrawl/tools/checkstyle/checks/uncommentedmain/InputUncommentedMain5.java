package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

public class InputUncommentedMain5
{
    public static void main(String[] args)
    {
        System.identityHashCode("InputUncommentedMain.main()");
    }
}

class PC {

    // uncommented main with depth 2
    public static void main(String[] args)
    {
        System.identityHashCode("PC.main()");
    }

    //lets go deeper
    private class PC2 {

        // uncommented main with depth 3
        public void main(String[] args)
        {
            System.identityHashCode("PC.main()");
        }


    }
}
