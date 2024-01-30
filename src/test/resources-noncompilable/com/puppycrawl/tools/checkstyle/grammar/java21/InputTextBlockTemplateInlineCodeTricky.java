//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputTextBlockTemplateInlineCodeTricky {
    String genSource() {
        return STR. """
            import java.util.FormatProcessor;
            import java.util.Locale;

            public class StringTemplateTest$ {
                static final FormatProcessor FMT = FormatProcessor.create(Locale.US);
                static String STR = "this is static String";
                static char C = 'c';
                static Character CHAR = 'C';
                static long L = -12345678910l;
                static Long LONG = 9876543210l;
                static int I = 42;
                static Integer INT = -49;
                static boolean BO = true;
                static Boolean BOOL = false;
                static short S = 13;
                static Short SHORT = -17;
                static byte BY = -3;
                static Byte BYTE = 12;
                static float F = 4.789f;
                static Float FLOAT = -0.000006f;
                static double D = 6545745.6734654563;
                static Double DOUBLE = -4323.7645676574;

                public static void run(java.util.List<String> log) {
                    runGeneral(log);
                    runCharacter(log);
                    runIntegral(log);
                    runBigInt(log);
                    runFloating(log);
                    runBigFloat(log);
                    runDate(log);
                }
                public static void runGeneral(java.util.List<String> log) {
                    \{ genFragments(Category.GENERAL) }
                }
                public static void runCharacter(java.util.List<String> log) {
                    \{ genFragments(Category.CHARACTER) }
                }
                public static void runIntegral(java.util.List<String> log) {
                    \{ genFragments(Category.INTEGRAL) }
                }
                public static void runBigInt(java.util.List<String> log) {
                    \{ genFragments(Category.BIG_INT) }
                }
                public static void runFloating(java.util.List<String> log) {
                    \{ genFragments(Category.FLOATING) }
                }
                public static void runBigFloat(java.util.List<String> log) {
                    \{ genFragments(Category.BIG_FLOAT) }
                }
                public static void runDate(java.util.List<String> log) {
                    \{ genFragments(Category.DATE) }
                }
                static void test(String fmt, String format, String expression, Object value, java.util.List<String> log) {
                    var formatted = String.format(java.util.Locale.US, format, value);
                    if (!fmt.equals(formatted)) {
                        log.add("  format: '%s' expression: '%s' value: '%s' expected: '%s' found: '%s'".formatted(format, expression, value, formatted, fmt));
                    }
                }
            }
            """ ;
    }

    private String genFragments(Category category) {
        return "whatever";
    }

    private static enum Category {
        GENERAL, CHARACTER, INTEGRAL, BIG_INT, FLOATING, BIG_FLOAT, DATE;
    }
}
