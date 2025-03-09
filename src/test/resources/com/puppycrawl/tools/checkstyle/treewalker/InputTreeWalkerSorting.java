package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSorting {

    private final int foo = 0;
    private final String Bar = "Test";

    void myMethod ( ) {
        System.out.println( "Hello" );
    }

      public void anotherMethod() {
        if (foo == 0) {
        System.out.println("Foo is zero");
        }
    }

    public void checkMethod(){
        if(foo==0) {
            System.out.println("Checkstyle test");
        }
    }
}
