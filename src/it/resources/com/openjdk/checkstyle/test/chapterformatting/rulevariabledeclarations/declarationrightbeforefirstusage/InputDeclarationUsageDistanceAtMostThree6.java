package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch'

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree6 {

    /** Some javadoc. */
    public void testIssue321() {
        Option srcDdlFile = OptionBuilder.create("f");
        Option logDdlFile = OptionBuilder.create("o");
        Option help = OptionBuilder.create("h");

        Options options = new Options();
        options.something();
        options.something();
        options.addOption(srcDdlFile, logDdlFile, help); // distance=1
    }

    /** Some javadoc. */
    public void testIssue322() {
        int mm = Integer.parseInt("2");
        long timeNow = 0;
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        cal.setTimeInMillis(timeNow);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, mm);
        cal.set(Calendar.MINUTE, mm); // distance=1
    }

    /** Some javadoc. */
    public void testIssue323(MyObject[] objects) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        for (int i = 0; i < objects.length; i++) {
            objects[i].setEnabled(true);
            objects[i].setColor(0x121212);
            objects[i].setUrl("http://google.com");
            objects[i].setSize(789);
            objects[i].setCalendar(cal); // distance=1
        }
    }

    /** Some javadoc. */
    public String testIssue324(boolean flag) {
        StringBuilder builder = new StringBuilder();
        builder.append("flag is ");
        builder.append(flag);
        final String line = "";
        if (flag) {
            builder.append("line of AST is:");
            builder.append("\n");
            builder.append(String.valueOf(line)); // distance=1
            builder.append("\n");
        }
        return builder.toString();
    }

    /** Some javadoc. */
    public void testIssue325() {
        Option a = null;
        Option b = null;
        Option c = null;
        boolean isCnull = isNull(c); // distance=1
        boolean isBnull = isNull(b); // distance=1
        boolean isAnull = isNull(a); // distance=1
    }

    /** Some javadoc. */
    public void testIssue326() {
        Option aopt = null;
        Option bopt = null;
        Option copt = null;
        isNull(copt); // distance = 1
        isNull(bopt); // distance = 2
        isNull(aopt); // distance = 3
    }

    boolean isNull(Option o) {
        return false;
    }

    class Option {

        public void setArgName(String string) {}
    }

    static class OptionBuilder {
        public static Option create(String string) {
            return null;
        }
    }

    class Options {

        public void addBindFile(Object object) {}

        public void addOption(Option srcDdlFile, Option logDdlFile, Option help) {}

        public void something() {}
    }

    class MyObject {

        public void setEnabled(boolean b) {}

        public void setCalendar(Calendar cal) {}

        public void setSize(int i) {}

        public void setUrl(String string) {}

        public void setColor(int i) {}
    }
}
