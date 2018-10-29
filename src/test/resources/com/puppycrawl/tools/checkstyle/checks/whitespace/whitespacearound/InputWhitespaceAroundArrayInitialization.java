package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundArrayInitialization {

    public void arrayInitTest() {

        final int[] COLORS = new int[]{5 }; // missing WS before "{"

        final int[] COLORS1 = new int[] {5 }; // valid

        final String[][] COLORS2 = {{"Green"}, {"Red"}}; //missing WS before inner array

        final String[][] COLORS21 = { {"Green", "Red"},{"White"} }; //missing WS between "," and "{"

        final String[][] COLORS22 = { {"White", "Yellow"}, {"Pink"} }; //valid

        final String[][][] COLORS31 = { { {"Black", "Blue"}, {"Gray", "White"}},
        		                       { {"Green", "Brown", "Magneta"}},
        		                       { {"Red", "Purple", "Violet"}} }; //valid

        final String[][][] COLORS32 = { {{"Red", "Green"},{"Pink"}} }; //missing before Red and Pink

        final String[][][] COLORS33 = {{{"White"}}}; //missing WS before "{"

        final String[][][][] COLORS41 = { { { {"Green"}}, { {"Purple"}}}, { { {"Yellow"}}} };//valid

        final String[][][][] COLORS42 = { { {{"White", "Blue"}, //missing WS between "{"
                                             {"Gray", "Black"},},},
                                             { { {"Red"}}}};

    }

}
