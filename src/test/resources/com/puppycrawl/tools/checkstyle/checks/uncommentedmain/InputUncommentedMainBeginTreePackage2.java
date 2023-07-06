public class InputUncommentedMainBeginTreePackage2 {

    public static void main(String[] args)
    {
        System.identityHashCode("InputUncommentedMain.main()");
    }
}

class PC2 {

    // uncommented main with depth 2
    public static void main(String[] args) // violation
    {
        System.identityHashCode("PC.main()");
    }

    //lets go deeper
    private class PC3 {

        // uncommented main with depth 3
        public void main(String[] args)
        {
            System.identityHashCode("PC.main()");
        }


    }
}

