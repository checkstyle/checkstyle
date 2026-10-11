package org.checkstyle.suppressionxpathfilter.blocks.emptyblock;

public class InputXpathEmptyBlockTryCatch {
    private void emptyTryCatch() {
        try { // warn
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
