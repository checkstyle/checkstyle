/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class InputVariableDeclarationUsageDistanceIssue32A {

    public void testIssue32_1() {
        Option srcDdlFile = OptionBuilder.create("f");
        Option logDdlFile = OptionBuilder.create("o");
        Option help = OptionBuilder.create("h");

        Options options = new Options();
        options.something();
        options.something();
        options.something();
        options.something();
        options.addOption(srcDdlFile, logDdlFile, help);
    }

    public void testIssue32_2() {
        int mm = Integer.parseInt("2");
        long timeNow = 0;
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        cal.setTimeInMillis(timeNow);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, mm);
        cal.set(Calendar.MINUTE, mm);
    }

    public void testIssue32_3(MyObject[] objects) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        for (int i = 0; i < objects.length; i++) {
            objects[i].setEnabled(true);
            objects[i].setColor(0x121212);
            objects[i].setUrl("http://google.com");
            objects[i].setSize(789);
            objects[i].setCalendar(cal);
        }
    }

    public String testIssue32_4(boolean flag) {
        StringBuilder builder = new StringBuilder();
        builder.append("flag is ");
        builder.append(flag);
        final String line = "";
        if (flag) {
            builder.append("line of AST is:");
            builder.append("\n");
            builder.append(String.valueOf(line));
            builder.append("\n");
        }
        return builder.toString();
    }

    public void testIssue32_5() {
        Option a = null;
        Option b = null;
        Option c = null;
        boolean isCNull = isNull(c);
        boolean isBNull = isNull(b);
        boolean isANull = isNull(a);
    }

    public void testIssue32_6() {
        Option aOpt = null;
        Option bOpt = null;
        Option cOpt = null;
        isNull(cOpt);
        isNull(bOpt);
        isNull(aOpt);
    }

    public void testIssue32_7() {
        String line = "abc";
        otherWriter.write(line);
        line.charAt(1);
        builder.append(line);
        test(line, line, line);
    }

    static class Option {}
    static class Options {
        public void something() {}
        public void addOption(Option a, Option b, Option c) {}
    }
    static class OptionBuilder {
        public static Option create(String s) { return null; }
    }
    static class MyObject {
        public void setEnabled(boolean b) {}
        public void setColor(int c) {}
        public void setUrl(String s) {}
        public void setSize(int s) {}
        public void setCalendar(Calendar c) {}
    }
    static boolean isNull(Option o) { return o == null; }
    static class otherWriter { public static void write(String s) {} }
    static class builder { public static void append(String s) {} }
    static void test(String s1, String s2, String s3) {}
}
