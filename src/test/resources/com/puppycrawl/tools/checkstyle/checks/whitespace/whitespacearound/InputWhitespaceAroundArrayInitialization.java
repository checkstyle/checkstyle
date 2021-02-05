package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundArrayInitialization {

    public void arrayInitTest() {

        final int[] COLORS = new int[]{5 }; // missing WS before and after "{"

        final int[] COLORS1 = new int[] {5 }; // missing WS after "{"

        final String[][] COLORS2 = {{"Green"}, {"Red"}}; //missing WS before and after "{"

        final String[][] COLORS21 = { {"Green", "Red"},{"White"} }; // missing WS between ","
                                                                   // and "{"
        final String[][] COLORS22 = { {"White", "Yellow"}, {"Pink"} }; // valid

        final String[][][] COLORS31 = { { {"Black", "Blue"}, {"Gray", "White"}}, // missing WS
                                       { {"Green", "Brown", "Magneta"}}, // after "{"
                                       { {"Red", "Purple", "Violet"}} };

        final String[][][] COLORS32 = { {{"Red", "Green"},{"Pink"}} }; //missing WS
                                                                      // after and before "{"
        final String[][][] COLORS33 = {{{"White"}}}; //missing WS before and after "{"

        final String[][][][] COLORS41 = { { { {"Green"}}, { {"Purple"}}}, { { {"Yellow"}}} };
                                                            // missing WS before and after "{"
        final String[][][][] COLORS42 = { { {{"White", "Blue"}, // missing WS before and after "{"
                                             {"Gray", "Black"},},},
                                             { { {"Red"}}}};

    }

}
