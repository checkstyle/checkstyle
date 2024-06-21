package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/**
 * Test for illegal tokens
 */
public class InputSpecialEscapeSequences
{

    public void methodWithLiterals()
    {
        final String ref = "<a href=\"";
        final String refCase = "<A hReF=\"";
    }

    public String wrongEscapeSequences()
    {
        final String r1 = "\u0008"; //ok
        final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
        final String r3 = "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
        final String r4  = "\u1111sdfsd\444"; // ok

        final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
        final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
        final char r7 = '\b'; //ok
        return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
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
        String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
        String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
        String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
        String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
        String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
        String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
        String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
    }

    public void specialCharsWithWarn2()
    {
        String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
        String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
        String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
        String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
        String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
        String r6 = "\\042"; // violation 'Consider using special escape sequence .*'
        String r7 = "\\047"; // violation 'Consider using special escape sequence .*'
        String r8 = "\\134"; // violation 'Consider using special escape sequence .*'
    }

    class Inner
    {
        public String wrongEscapeSequences()
        {
            final String r1 = "\u0008"; //ok
            final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
            final String r3 = "\u000csssdfsd";
            // violation above 'Consider using special escape sequence .*'
            final String r4  = "\u1111sdfsd\444"; //ok

            final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
            final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
            final char r7 = '\b'; //ok
            return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
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
            String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
            String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
            String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
            String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
            String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
            String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
            String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
        }

        public void specialCharsWithWarn2()
        {
            String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
            String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
            String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
            String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
            String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
            String r6 = "\\042"; // violation 'Consider using special escape sequence .*'
            String r7 = "\\047"; // violation 'Consider using special escape sequence .*'
            String r8 = "\\134"; // violation 'Consider using special escape sequence .*'
        }

        Inner anoInner = new Inner(){
            public String wrongEscapeSequences()
            {
                final String r1 = "\u0008"; //ok
                final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
                final String r3 = "\u000csssdfsd";
                // violation above 'Consider using special escape sequence .*'
                final String r4  = "\u1111sdfsd\444"; //ok

                final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
                final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
                final char r7 = '\b'; //ok
                return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
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
                String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
                String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
                String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
                String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
                String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
                String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
                String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
            }

            public void specialCharsWithWarn2()
            {
                String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
                String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
                String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
                String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
                String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
                String r6 = "\\042"; // violation 'Consider using special escape sequence .*'
                String r7 = "\\047"; // violation 'Consider using special escape sequence .*'
                String r8 = "\\134"; // violation 'Consider using special escape sequence .*'
            }
        };
    }
}
