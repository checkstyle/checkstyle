package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch*'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree7 {

    /** Some javadoc. */
    public void testIssue327() {
        String line = "abc";
        OtherWriter.write(line);
        line.charAt(1);
        Builder.append(line);
        test(line, line, line);
    }

    /** Some javadoc. */
    public void testIssue328(Writer w1, Writer w2, Writer w3) {
        String l1 = "1";

        w3.write(l1); // distance=3
    }

    /** Some javadoc. */
    public void testIssue329() {
        Options options = new Options();
        Option myOption = null;
        // violation above 'Distance between variable 'myOption' .* usage is 7, but allowed 3.'
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        System.identityHashCode("message");
        myOption.setArgName("abc"); // distance=7
    }

    /** Some javadoc. */
    public void testIssue3210() {
        Options options = new Options();
        Option myOption = null;
        // violation above 'Distance between variable 'myOption' .* usage is 6, but allowed 3.'
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        myOption.setArgName("q"); // distance=6
    }

    /** Some javadoc. */
    public int testIssue3211(String toDir) throws Exception {
        int count = 0;
        // violation above 'Distance between variable 'count' .* first usage is 4, but allowed 3.'
        String[] files = {};

        System.identityHashCode("Data archival started");
        files.notify();
        System.identityHashCode("sss");

        if (files == null || files.length == 0) {
            System.identityHashCode("No files on a remote site");
        } else {
            System.identityHashCode("Files on remote site: " + files.length);

            for (String ftpFile : files) {
                if (files.length == 0) {
                    "".concat("");
                    ftpFile.concat(files[2]);
                    count++;
                }
            }
        }

        System.lineSeparator();

        return count;
    }

    void test(String s, String s1, String s2) {}

    static class OtherWriter {
        public static void write(String line) {}
    }

    static class Builder {
        public static void append(String line) {}
    }

    class Writer {
        public void write(String l3) {}
    }

    class Option {
        public void setArgName(String string) {}
    }

    class Options {
        public void addBindFile(Object object) {}

        public void addOption(Option srcDdlFile, Option logDdlFile, Option help) {}

        public void something() {}
    }

    class TreeMapNode {
        TreeMapNode(String label) {}

        TreeMapNode(String label, double d, DefaultValue defaultValue) {}
    }

    class DefaultValue {
        DefaultValue(double d) {}
    }
}
