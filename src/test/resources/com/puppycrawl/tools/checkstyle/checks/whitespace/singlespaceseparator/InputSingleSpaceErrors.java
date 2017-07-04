package  com.puppycrawl.   tools.checkstyle.checks.whitespace.singlespaceseparator;

import java.util.List;
import  java.util.Vector;

public class      InputSingleSpaceErrors  {
    int             number; //violation
int i =    99  ;
{
i=1;
i  =2;
 i=  3;
  i =  4;
    i =	5; // A tab between = and 5.
}

    private  void foo  (int     i) {
        if (i  > 10)  {
            if  (bar(  )) {
                i  ++;
                foo  (i);
            }
        }
    }

    private boolean  bar(  ) {
        List  <Double  > list  = new Vector<  >();
        int a	= 0;  // Multiple whitespaces before comment
		int b = 1; // Multiple tabs as indentation are ok.
        return  Math.random() <  0.5;
    }  }
