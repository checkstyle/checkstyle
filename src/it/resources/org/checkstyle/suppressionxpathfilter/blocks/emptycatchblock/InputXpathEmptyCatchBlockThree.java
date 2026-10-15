/*
EmptyCatchBlock


*/

package org.checkstyle.suppressionxpathfilter.blocks.emptycatchblock;

public class InputXpathEmptyCatchBlockThree {

    public void process() {
        try {
            String value = "test";
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {} // violation 'Empty catch block.'
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
