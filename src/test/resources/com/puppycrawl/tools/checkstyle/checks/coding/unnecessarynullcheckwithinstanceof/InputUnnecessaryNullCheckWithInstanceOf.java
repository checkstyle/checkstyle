/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOf {
    public void basicIfStatements(Object obj) {
        if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
            String str = (String) obj;
        }
        if (obj instanceof String && (obj != null)) { // violation, 'Unnecessary nullity check'
            String str = (String) obj;
        }
        if (obj != null) {
            String str = (String) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
        }
        if ((null != obj) && ((obj instanceof String))) { // violation, 'Unnecessary nullity check'
            String str = (String) obj;
        }
    }
    public void separateIfStatements(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
    }
    public void processData(Object data, String s) {
        if (getData() != null && getData() instanceof String) {
            String text = (String) getData();
            System.out.println("Text length: " + text.length());
        }
        if (getData() != null && data instanceof String) {
            String text = (String) getData();
            System.out.println("Text length: " + text.length());
        }
        if (data != null && getData() instanceof String) {
            String text = (String) getData();
            System.out.println("Text length: " + text.length());
        }
    }
    private Object getData() {
        return "Some string data";
    }
}
