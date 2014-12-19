package com.puppycrawl.tools.checkstyle.indentation;

class InputAndroidStyleCorrect
        extends FooForExtend { //correct

    String string = foo("fooooooooooooooo", 0, false);

    String string1 =
            foo("fooooooooooooooo", 0, false); //correct

    String foo (String aStr,
            int aNnum, boolean aFlag) { //correct

        if (true && true &&
                true && true) { //correct
            String string2 = foo("fooooooo"
                    + "oooooooo", 0, false); //correct
            if (false &&
                    false && false) { //correct
                
            }
        }
        return "string";
    }
}

class InputAndroidStyleIncorrect
   extends FooForExtend { //incorrect

   String string = foo("fooooooooooooooo", 0, false); //incorrect

    String string1 =
        foo("fooooooooooooooo", 0, false); //incorrect

    String foo (String aStr,
        int aNnum, boolean aFlag) { //incorrect

        if (true && true &&
             true && true) { //incorrect

            String string2 = foo("fooooooo"
                + "oooooooo", 0, false); //incorrect
        if (false &&
                  false && false) { //incorrect
                
           }  //incorrect
        }
       return "string";  //incorrect
    }
}

class FooForExtend {}