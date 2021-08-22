/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
tokens = ARRAY_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundArrayInitialization {

    public void arrayInitTest() {

        final int[] COLORS = new int[]{5 }; // violation

        final int[] COLORS1 = new int[] {5 }; // valid

        final String[][] COLORS2 = {{"Green"}, {"Red"}}; // violation

        final String[][] COLORS21 = { {"Green", "Red"},{"White"} }; // violation

        final String[][] COLORS22 = { {"White", "Yellow"}, {"Pink"} }; //valid

        final String[][][] COLORS31 = { { {"Black", "Blue"}, {"Gray", "White"}},
                                       { {"Green", "Brown", "Magneta"}},
                                       { {"Red", "Purple", "Violet"}} }; //valid

        final String[][][] COLORS32 = { {{"Red", "Green"},{"Pink"}} }; // 2 violations

        final String[][][] COLORS33 = {{{"White"}}}; // 2 violations

        final String[][][][] COLORS41 = { { { {"Green"}}, { {"Purple"}}}, { { {"Yellow"}}} };//valid

        final String[][][][] COLORS42 = { { {{"White", "Blue"}, // violation
                                             {"Gray", "Black"},},},
                                             { { {"Red"}}}};

    }

}
