package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.*;

public  class  BadCheck{

    public static void  main( String[]args ){
        System.out.println( "Hello" );

        if(true){System.out.println("bad");}

        for(int i=0;i<10;i++){ System.out.println(i);}

    }

}
