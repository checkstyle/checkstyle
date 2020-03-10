package com.puppycrawl.tools.checkstyle;

public class M {

    public void myTest() {

        String name1="ABC";
        String name2="CDE";
        String name3="GFH";
        String name4="ABC";  //violation
    /*more useful in a long
    list of names to find r repetition*/

        String r="A"+"B"+"C";   //no violation

        String x=", ";
        String y=", ";
    /*no violation since comma(followed by a space) is ignored while
    checking for repetitions*/

        String z=". ";
        String h=". ";
    /*no violation since dot(followed by a space) is ignored while
    checking for repetitions*/

    /*
    value='^((". ")|(", "))$'/>
    */
        int y1=1;
        if(y1==1)
        {
            String d="Hello";
        }
        if(y1==1)
        {
            String d1="Hello";
        }
        /*no violation because of the keyword LITERAL_IF which ignores the duplicates in if statements*/


    }

}