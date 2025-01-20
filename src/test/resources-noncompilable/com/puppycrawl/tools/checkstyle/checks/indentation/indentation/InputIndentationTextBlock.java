//non-compiled with javac: Compilable with Java17                             //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 */                                                                           //indent:1 exp:1


public class InputIndentationTextBlock {                      //indent:0 exp:0
    private static final String EXAMPLE = """
        Example string""";                                    //indent:8 exp:8

    private static final String GO1 = """
    GO                                                        //indent:4 exp:4
""";                                                          //indent:0 exp:8 warn

    private static final String GO2 = """
    GO                                                        //indent:4 exp:4
        """;                                                  //indent:8 exp:8

    public void textBlockNoIndent() {                         //indent:4 exp:4
        String contentW = ("""
                -----1234--                                   //indent:16 exp:16
                -----1234--h-hh                               //indent:16 exp:16
                """);                                         //indent:16 exp:12 warn

        String contentR = ("""
                -----1234--                                   //indent:16 exp:16
                -----1234----                                 //indent:16 exp:16
            """);                                             //indent:12 exp:12

        if ("""
              one more string""".equals(contentR)) {          //indent:14 exp:14
            System.out.println("This is string");             //indent:12 exp:12
        }                                                     //indent:8 exp:8
        if (contentR.equals("""
          stuff""")) {                                        //indent:10 exp:10
        }                                                     //indent:8 exp:8

        String a =                                            //indent:8 exp:8
"""
          3                                                   //indent:10 exp:10
          4                                                   //indent:10 exp:10
          5                                                   //indent:10 exp:10
          6                                                   //indent:10 exp:10
          7                                                   //indent:10 exp:10
"""                                                           //indent:0 exp:12 warn
              ;                                               //indent:14 exp:14
    }                                                         //indent:4 exp:4
    private static void fooBlockAsArgument() {                //indent:4 exp:4
        String main =                                         //indent:8 exp:8
        """
        public class Main {                                   //indent:8 exp:8
          public void kit(String args) {                      //indent:10 exp:10
            System.out.println("hello, world!");              //indent:12 exp:12
            if (args.length > 0) {                            //indent:12 exp:12
              long pid = ProcessHandle.current().pid();       //indent:14 exp:14
              System.exit(process.exitValue());               //indent:14 exp:14
            }                                                 //indent:12 exp:12
          }                                                   //indent:10 exp:10
        }                                                     //indent:8 exp:8
            """;                                              //indent:12 exp:12
        LOG.warn("""
                    The following settings                    //indent:20 exp:20
                    {}                                        //indent:20 exp:20
                    Therefore returning error result.""",     //indent:20 exp:20
            GO2);                                             //indent:12 exp:12

        LOG.warn(                                             //indent:8 exp:8
              """
              Failed to lidation; will ignore for now,        //indent:14 exp:14
              but it may fail later during runtime""",        //indent:14 exp:14
            GO1);                                             //indent:12 exp:12
    }                                                         //indent:4 exp:4

    public void fooBlockAsCondition() {                       //indent:4 exp:4
        if (GO1.equalsIgnoreCase("""
              my other string""" + """
              plus this string""" + """
              and also this one.""")) {                       //indent:14 exp:14
            System.out.println("this is my other string");}   //indent:12 exp:12
        if ("""
              one more string""".equals(GO2)) {               //indent:14 exp:14
            System.out.println("This is one more string");}   //indent:12 exp:12

        if ("""
                  a""" + """
                  bc""" == GO2) {                             //indent:18 exp:18
        }                                                     //indent:8 exp:8
        else {                                                //indent:8 exp:8
            System.out.printf("""
          day of the week                                     //indent:10 exp:10
              successfully got: "%s"},                        //indent:14 exp:14
                """, LOG.getNumberOfErrors());                //indent:16 exp:16
        }                                                     //indent:8 exp:8
    }                                                         //indent:4 exp:4
    public class LOG {                                        //indent:4 exp:4
        public static void warn(String block, String example){//indent:8 exp:8
        }                                                     //indent:8 exp:8
        static String getNumberOfErrors() {                   //indent:8 exp:8
            return "Example";                                 //indent:12 exp:12
        }                                                     //indent:8 exp:8
    }                                                         //indent:4 exp:4
}                                                             //indent:0 exp:0
