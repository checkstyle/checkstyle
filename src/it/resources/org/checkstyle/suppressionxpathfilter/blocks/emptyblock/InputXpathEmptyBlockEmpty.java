package org.checkstyle.suppressionxpathfilter.blocks.emptyblock;

public class InputXpathEmptyBlockEmpty {
    private void emptyLoop() {
        for (int i = 0; i < 10; i++) {} // warn

    }
}
