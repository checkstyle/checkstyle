//non-compiled with javac: Compilable with Java17                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 */                                                                           //indent:1 exp:1


public class InputIndentationTextBlock { //indent:0 exp:0
    private static final String EXAMPLE = """//indent:4 exp:4
        Example string""";//indent:8 exp:8

    private static final String GO = """//indent:4 exp:4
    GO                                   //indent:4 exp:4
""";//indent:0 exp:0

    public void textBlockNoIndent() { //indent:4 exp:4
        if (""" //indent:8 exp:8
              one more string""".equals(s)) { //indent:14 exp:14
            System.out.println("This is one more string"); //indent:12 exp:12
        } //indent:8 exp:8
        if (myString.equals(""" //indent:8 exp:8
          stuff""")) { //indent:10 exp:10
        } //indent:8 exp:8

        String a = //indent:8 exp:8
""" //indent:0 exp:0
          3 //indent:10 exp:10
          4 //indent:10 exp:10
          5 //indent:10 exp:10
          6 //indent:10 exp:10
          7 //indent:10 exp:10
 //indent:1 exp:1
""" //indent:0 exp:0

              ; //indent:14 exp:14
    } //indent:4 exp:4
    private static void fooBlockAsArgument() { //indent:4 exp:4
        String main = //indent:8 exp:8
        """ //indent:8 exp:8
        package hello; //indent:8 exp:8
        import java.io.ByteArrayOutputStream; //indent:8 exp:8
        public class Main { //indent:8 exp:8
          public static void main(String... args) throws Exception { //indent:10 exp:10
            System.out.println("hello, world!"); //indent:12 exp:12
            if (args.length > 0) { //indent:12 exp:12
              long pid = ProcessHandle.current().pid(); //indent:14 exp:14
              String jcmd = args[0]; //indent:14 exp:14
              String[] cmds = { jcmd, Long.toString(pid), "JFR.start" }; //indent:14 exp:14
              Process process = new ProcessBuilder(cmds).start(); //indent:14 exp:14
              process.waitFor(); //indent:14 exp:14
              var baos = new ByteArrayOutputStream(); //indent:14 exp:14
              process.getInputStream().transferTo(baos); //indent:14 exp:14
              System.out.println(baos.toString()); //indent:14 exp:14
              System.exit(process.exitValue()); //indent:14 exp:14
            } //indent:12 exp:12
          } //indent:10 exp:10
        } //indent:8 exp:8
        """; //indent:8 exp:8
        Log.warn(format(""" //indent:8 exp:8
                    The following settings  //indent:20 exp:20
                    {} //indent:20 exp:20
                    Therefore returning error result.""", //indent:20 exp:20
            commandKey), this); //indent:12 exp:12

        LOG.warn( //indent:8 exp:8
              """ //indent:14 exp:14
              Failed to lidation; will ignore for now, //indent:14 exp:14
              but it may fail later during runtime""", //indent:14 exp:14
            error); //indent:12 exp:12

    } //indent:4 exp:4

    public void fooBlockAsCondition() { //indent:4 exp:4
        if (s.equalsIgnoreCase(""" //indent:8 exp:8
              my other string""" + """ //indent:14 exp:14
              plus this string""" + """ //indent:14 exp:14
              and also this one.""")) { //indent:14 exp:14
            System.out.println("this is my other string");} //indent:12 exp:12
        if (""" //indent:8 exp:8
              one more string""".equals(s)) { //indent:14 exp:14
            System.out.println("This is one more string");} //indent:12 exp:12

        if (""" //indent:8 exp:8
                  a""" + """ //indent:18 exp:18
                  bc""" == s) { //indent:18 exp:18
        } //indent:8 exp:8
        else { //indent:8 exp:8
            System.out.printf(""" //indent:12 exp:12
          {$$$ Passed: Rolled: "%s" by 1 week where the first day of the week //indent:10 exp:10
              is: %s with a week: %s and successfully got: "%s"}, //indent:14 exp:14
                """, originalDate, calendar.getFirstDayOfWeek(), //indent:16 exp:16
                calendar.getMinimalDaysInFirstWeek(), rolledDate); //indent:16 exp:16
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
