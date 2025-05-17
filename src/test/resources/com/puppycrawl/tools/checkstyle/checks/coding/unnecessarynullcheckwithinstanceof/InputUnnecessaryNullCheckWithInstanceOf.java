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
    public void processData(Object data, String s, String b, String getData) {
        if (getData() != null && getData().toString().equals("a") && getData() instanceof String) {
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

        if (s != null && s instanceof String && s.toString().equals("abc")) { }

        if (b != null  && b.toString().equals("abc") && b instanceof String) { }

        // violation below, 'Unnecessary nullity check'
        if (b != null  && s.toString().equals("abc") && b instanceof String) { }

        // violation below, 'Unnecessary nullity check'
        if (s != null && s instanceof String && b.toString().equals("abc")) { }

        // violation below, 'Unnecessary nullity check'
        if (getData != null  && getData().toString().equals("abc") && getData instanceof String) { }

        Boolean accepted = (Boolean) null;
         if((accepted == null && null == null) || (accepted != null && accepted && !accepted)){}
    }
    private Object getData() {
        return "Some string data";
    }
}
