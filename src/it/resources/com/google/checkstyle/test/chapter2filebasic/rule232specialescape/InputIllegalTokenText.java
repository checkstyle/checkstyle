package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/**
 * Test for illegal tokens
 */
public class InputIllegalTokenText
{

    public void methodWithLiterals()
    {
        final String ref = "<a href=\"";
        final String refCase = "<A hReF=\"";
    }

    public String wrongEscapeSequences()
    {
        final String r1 = "\u0008"; //ok
        final String r2 = "\u0009"; //warn
        final String r3 = "\u000csssdfsd"; //warn
        final String r4  = "\u1111sdfsd\444";

        final char r5 = '\012'; //warn
        final char r6 = '\u0022'; //warn
        final char r7 = '\b'; //ok
        return "\u000csssdfsd"; //warn
    }

    public void specialCharsWithoutWarn()
    {
        String r1 = "\b"; //ok
        String r2 = "\t"; //ok
        String r3 = "\n"; //ok
        String r4 = "\f"; //ok
        String r5 = "\r"; //ok
        String r6 = "\""; //ok
        String r7 = "\'"; //ok
        String r8 = "\\"; //ok
    }

    public void specialCharsWithWarn()
    {
        String r1 = "\\u0008"; //ok
        String r2 = "\\u0009"; // warn
        String r3 = "\\u000a"; // warn
        String r4 = "\\u000c"; // warn
        String r5 = "\\u000d"; // warn
        String r6 = "\\u0022"; // warn
        String r7 = "\\u0027"; // warn
        String r8 = "\\u005c"; // warn
    }

    public void specialCharsWithWarn2()
    {
        String r1 = "\\010"; // warn
        String r2 = "\\011"; // warn
        String r3 = "\\012"; // warn
        String r4 = "\\014"; // warn
        String r5 = "\\015"; // warn
        String r6 = "\\042"; // warn
        String r7 = "\\047"; // warn
        String r8 = "\\134"; // warn
    }

    class Inner
    {
        public String wrongEscapeSequences()
        {
            final String r1 = "\u0008"; //ok
            final String r2 = "\u0009"; //warn
            final String r3 = "\u000csssdfsd"; //warn
            final String r4  = "\u1111sdfsd\444"; //ok

            final char r5 = '\012'; //warn
            final char r6 = '\u0022'; //warn
            final char r7 = '\b'; //ok
            return "\u000csssdfsd"; //warn
        }

        public void specialCharsWithoutWarn()
        {
            String r1 = "\b"; //ok
            String r2 = "\t"; //ok
            String r3 = "\n"; //ok
            String r4 = "\f"; //ok
            String r5 = "\r"; //ok
            String r6 = "\""; //ok
            String r7 = "\'"; //ok
            String r8 = "\\"; //ok
        }

        public void specialCharsWithWarn()
        {
            String r1 = "\\u0008"; //ok
            String r2 = "\\u0009"; // warn
            String r3 = "\\u000a"; // warn
            String r4 = "\\u000c"; // warn
            String r5 = "\\u000d"; // warn
            String r6 = "\\u0022"; // warn
            String r7 = "\\u0027"; // warn
            String r8 = "\\u005c"; // warn
        }

        public void specialCharsWithWarn2()
        {
            String r1 = "\\010"; // warn
            String r2 = "\\011"; // warn
            String r3 = "\\012"; // warn
            String r4 = "\\014"; // warn
            String r5 = "\\015"; // warn
            String r6 = "\\042"; // warn
            String r7 = "\\047"; // warn
            String r8 = "\\134"; // warn
        }

        Inner anoInner = new Inner(){
            public String wrongEscapeSequences()
            {
                final String r1 = "\u0008"; //ok
                final String r2 = "\u0009"; //warn
                final String r3 = "\u000csssdfsd"; //warn
                final String r4  = "\u1111sdfsd\444"; //ok

                final char r5 = '\012'; //warn
                final char r6 = '\u0022'; //warn
                final char r7 = '\b'; //ok
                return "\u000csssdfsd"; //warn
            }

            public void specialCharsWithoutWarn()
            {
                String r1 = "\b"; //ok
                String r2 = "\t"; //ok
                String r3 = "\n"; //ok
                String r4 = "\f"; //ok
                String r5 = "\r"; //ok
                String r6 = "\""; //ok
                String r7 = "\'"; //ok
                String r8 = "\\"; //ok
            }

            public void specialCharsWithWarn()
            {
                String r1 = "\\u0008"; //ok
                String r2 = "\\u0009"; // warn
                String r3 = "\\u000a"; // warn
                String r4 = "\\u000c"; // warn
                String r5 = "\\u000d"; // warn
                String r6 = "\\u0022"; // warn
                String r7 = "\\u0027"; // warn
                String r8 = "\\u005c"; // warn
            }

            public void specialCharsWithWarn2()
            {
                String r1 = "\\010"; // warn
                String r2 = "\\011"; // warn
                String r3 = "\\012"; // warn
                String r4 = "\\014"; // warn
                String r5 = "\\015"; // warn
                String r6 = "\\042"; // warn
                String r7 = "\\047"; // warn
                String r8 = "\\134"; // warn
            }
        };
    }
}
