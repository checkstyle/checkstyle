package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputWhitespaceAroundArrayInitialization {
    
    public void arrayInitTest() {
        // missing WS before "{"
        final int[] COLORS = new int[]{5 };
        // valid
        final int[] COLORS1 = new int[] {5 };
        //missing WS before inner array
        final String[][] COLORS2 = {{"Green"}, {"Red"}};
        //missing WS between "," and "{"
        final String[][] COLORS21 = { {"Green", "Red"},{"White"} };
        //valid
        final String[][] COLORS22 = { {"White", "Yellow"}, {"Pink"} };
        //valid
        final String[][][] COLORS3 = { { {"Black", "Blue"}, {"Gray", "White"}},
        		                       { {"Green", "Brown", "Magneta"}},
        		                       { {"Red", "Purple", "Violet"}} };
        //valid
        final String[][][][] COLORS4 = { { { {"Orange"}, {"Green"}}, { {"Purple"}}}, { { {"Yellow"}}} };
    }

}

