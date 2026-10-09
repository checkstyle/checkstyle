/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.ArrayList;
import java.util.List;

class InputGenericWhitespaceMultipleViolations
{
    void meth()
    {
        List<Integer> x = new ArrayList<Integer>();
        List<List<Integer>> y = new ArrayList<List<Integer>>();
        List < Integer > a = new ArrayList < Integer > ();
        List < List < Integer > > b = new ArrayList < List < Integer > > ();
        // 7 violations 2 lines above:
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''>' is preceded with whitespace.'
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''>' is followed by whitespace.'
        //  ''>' is preceded with whitespace.'
        // 15 violations 9 lines above:
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''>' is followed by whitespace.'
        //  ''>' is preceded with whitespace.'
        //  ''>' is preceded with whitespace.'
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''<' is followed by whitespace.'
        //  ''<' is preceded with whitespace.'
        //  ''>' is followed by whitespace.'
        //  ''>' is preceded with whitespace.'
        //  ''>' is followed by whitespace.'
        //  ''>' is preceded with whitespace.'
    }
}
