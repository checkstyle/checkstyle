/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package com . puppycrawl
        .tools.
        checkstyle.checks.whitespace.nowhitespacebefore;

/**
 * Class for testing whitespace issues.
 * violation missing author tag
 **/
class InputNoWhitespaceBeforeDefault
{
    /** ignore assignment **/
    private int mVar1=1;
    /** ignore assignment **/
    private int mVar2 =1;
    /** Should be ok **/
    private int mVar3 = 1;

    /** method **/
    void method1()
    {
        final int a = 1;
        int b= 1; // Ignore 1
        b=1; // Ignore 1
        b+=1; // Ignore 1
        b -=- 1 + (+ b); // Ignore 2
        b = b ++ + b --; // 2 violations
        b = ++ b - -- b; // Ignore 1
    }

    /** method **/
    void method2()
    {
        synchronized(this) {
        }
        try{
        }
        catch(RuntimeException e){
        }
    }

    /**
     skip blank lines between comment and code,
     should be ok
     **/


    private int mVar4 = 1;


    /** test WS after void return */
    private void fastExit()
    {
        boolean complicatedStuffNeeded = true;
        if( !complicatedStuffNeeded )
        {
            return; // should not complain about missing WS after return
        }
        else
        {
            // do complicated stuff
        }
    }


    /** test WS after non void return
     @return 2
     */
    private int nonVoid()
    {
        if ( true )
        {
            return(2); // should complain about missing WS after return
        }
        else
        {
            return 2; // this is ok
        }
    }

    /** test casts **/
    private void testCasts()
    {
        Object o = (Object) new Object();
        o = (Object)o;
        o = ( Object ) o;
        o = (Object)
                o;
    }

    /** test questions **/
    private void testQuestions()
    {
        boolean b = (1 == 2)?true:false;
        b = (1==2) ? false : true;
    }

    /** star test **/
    private void starTest()
    {
        int x = 2 *3* 4;
    }

    /** boolean test **/
    private void boolTest()
    {
        boolean a = true;
        boolean x = ! a;
        int z = ~1 + ~ 2;
    }

    /** division test **/
    private void divTest()
    {
        int a = 4 % 2;
        int b = 4% 2;
        int c = 4 %2;
        int d = 4%2;
        int e = 4 / 2;
        int f = 4/ 2;
        int g = 4 /2;
        int h = 4/2;
    }

    /** @return dot test **/
    private java .lang.  String dotTest()
    {
        Object o = new java.lang.Object();
        o.
            toString();
        o
            .toString();
        o . toString();
        return o.toString();
    }

    /** assert statement test */
    public void assertTest()
    {

        assert true;


        assert true : "Whups";

        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";

        // missing WS around assert
        assert(true);

        // missing WS around colon
        assert true:"Whups";
    }

    /** another check */
    void donBradman(Runnable aRun)
    {
        donBradman(new Runnable() {
            public void run() {
            }
        });

        final Runnable r = new Runnable() {
            public void run() {
            }
        };
    }

    /** rfe 521323, detect whitespace before ';' */
    void rfe521323()
    {
        doStuff() ; // violation
        //       ^ whitespace
        for (int i = 0 ; i < 5; i++) { // violation
            //        ^ whitespace
        }
    }


    /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    private int i ; // violation
    //           ^ whitespace
    private int i1, i2, i3 ; // violation
    //                    ^ whitespace
    private int i4, i5, i6;

    /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    void bug806243()
    {
        Object o = new InputNoWhitespaceBeforeDefault() {
            private int j ; // violation
            //           ^ whitespace
        };
    }

    void doStuff() {
    }
}

/**
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 * @author o_sukhodolsky
 * @version 1.0
 */
interface IFoo_NoWhitespaceBeforeDefault
{
    void foo() ; // violation
    //        ^ whitespace
}

/**
 * Avoid Whitespace violations in for loop.
 * @author lkuehne
 * @version 1.0
 */
class SpecialCasesInForLoop_NoWhitespaceBeforeDefault
{
    void forIterator()
    {
        // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
        for (int i = 0; i++ < 5;) {
            //                  ^ no whitespace
        }

        // bug 895072
        // avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
        int i = 0;
        for ( ; i < 5; i++ ) {
            //   ^ whitespace
        }
        for (int anInt : getSomeInts()) {
            //Should be ignored
        }
    }

    int[] getSomeInts() {
        int i = (int) ( 2 / 3 );
        return null;
    }

    public void myMethod() {
        new Thread() {
            public void run() {
            }
        }.start();
    }

    public void foo(java.util.List<? extends String[]> bar, Comparable<? super Object[]> baz) { }

    public void mySuperMethod() {
        Runnable[] runs = new Runnable[] {new Runnable() {
                public void run() {
                }
            },
            new Runnable() {
                public void run() {
                }
            }};
        runs[0]
.
 run()
; // violation
    }

    public void testNullSemi() {
        return ; // violation
    }

    public void register(Object obj) { }

    public void doSomething(String args[]) {
        register(boolean[].class);
        register( args );
    }

    public void parentheses() {
        testNullSemi
(
)
; // violation
    }

    public static void testNoWhitespaceBeforeEllipses(String ... args) { // violation
    }

    {
        label1 : // violation
        for(int i = 0; i < 10; i++) {}
    }

    public void foo() {
        label2: // no violation
        while (true) {}
    }

        public void test() {
        // Valid cases
        " ".equals("");
        "".equals(" ");
        "".equals("");

        // Violations
        "".equals(""); // violation
        "".equals(""  + ""); // violation
        "".equals("" +  ""); // violation
         System.out.println(""); // violation
        System .out.println(""); // violation
        System. out.println(""); // violation
        System.out .println(""); // violation
        System.out. println(""); // violation
        System.out. println(""); // violation
        System.out.println (""); // violation
        "" .equals(""); // violation
        "". equals(""); // violation
        "".equals (""); // violation
        "".equals( ""); // violation
        "".equals("" ); // violation
        "".equals("") ; // violation
    }

    /** Test variable assignment violations */
    public void testSpaceViolationVarAssignment() {
        // Valid
        boolean eq1 = " ".equals("");
        boolean eq2 = "".equals("");

        // Violations
        eq2 = "".equals(""); // violation
        eq2  = "".equals(""); // violation
        eq2 =  "".equals(""); // violation
    }

    /** Test variable declaration violations */
    public void testSpaceViolationVarDeclaration() {
        // Valid
        boolean e = "".equals("");
        boolean e3 = "".equals("");
        e3 = "".equals("");

        // Violations
        boolean e4 = "".equals(""); // violation
        boolean e1  = "".equals(""); // violation
        boolean  e2 = "".equals(""); // violation
        e3 = "".equals(""); // violation
        e3  = "".equals(""); // violation
        e3 =  "".equals(""); // violation
    }

    /** Test array access violations */
    public void testArrayAccess() {
        int[] arr = new int[10];

        // Valid
        int a = arr[0];

        // Violations
        int x = arr [0]; // violation
        int y = arr[ 0]; // violation
        int z = arr [ 0]; // violation
    }

    /** Test generics violations */
    public void testGenerics() {
        java.util.List<String> list = new java.util.ArrayList<>();

        // Valid
        list.add("test");

        // Violations
        list .add("test"); // violation
        list. add("test"); // violation
    }

    /** Test lambda violations */
    public void testLambda() {
        // Valid
        Runnable r = () -> System.out.println();

        // Violation
        Runnable r2 = () -> System.out .println(); // violation
    }

    /** Test method reference violations */
    public void testMethodReference() {
        // Valid
        java.util.function.Function<String, String> f1 = String::valueOf;

        // Violation
        java.util.function.Function<String, String> f2 = String ::valueOf; // violation
    }

    /** Test nested calls violations */
    public void testNestedCalls() {
        String s = "hello";

        // Valid
        s.substring(1).trim();
        s.substring(1).trim();

        // Violations
        s.substring(1). trim(); // violation
        s.substring(1 ).trim(); // violation
    }

    /** Test multiple dots violations */
    public void testMultipleDots() {
        String s = "hello";

        // Valid
        s.substring(1).substring(1).substring(1);

        // Violations
        s.substring(1).substring(1). substring(1); // violation
        s.substring(1) .substring(1).substring(1); // violation
    }

    /** Test with other operators */
    public void testWithOtherOperators() {
        // Valid
        String s1 = "a" + "b".toString();
        int x = 1 + 2 * 3;

        // Violation
        String s2 = "a" + "b". toString(); // violation
    }

    /** Test in control structures */
    public void testInControlStructures() {
        // Valid
        if ("test".equals("test")) {
            System.out.println();
        }

        // Violations
        if ("test". equals("test")) { // violation
            System.out.println();
        }

        while (true) {
            break ;
        }
    }

    /** Test in try-catch */
    public void testInTryCatch() {
        // Valid
        try {
            // do something
        } catch (Exception e) {
            // handle
        }

        // Violation
        try {
            // do something
        } catch (Exception e ) { // violation
            // handle
        }
    }

    /** Test in annotations */
    public void testInAnnotations() {
        // Valid
        @SuppressWarnings("unchecked")
        Object o1 = new Object();

        // Violation
        @SuppressWarnings ("unchecked") // violation
        Object o2 = new Object();
    }

    /** Test in type cast */
    public void testInTypeCast() {
        Object o = "test";

        // Valid
        String s1 = (String) o;

        // Violation
        String s2 = (String ) o; // violation
    }

    /** Test in switch */
    public void testInSwitch() {
        // Valid
        switch (1) {
            case 1:
                break;
        }

        // Violation
        switch (1 ) { // violation
            case 1:
                break;
        }
    }

    /** Test in synchronized */
    public void testInSynchronized() {
        // Valid
        synchronized (this) {
            // do something
        }

        // Violation
        synchronized (this ) { // violation
            // do something
        }
    }

    /** Test in assert */
    public void testInAssert() {
        // Valid
        assert true : "message";

        // Violation
        assert true : "message" ; // violation
    }

    /** Test in return */
    public void testInReturn() {
        // Valid (no return value)
        return;
    }

    public void testInReturn2() {
        // Violation
        return ; // violation
    }

    /** Test in throw */
    public void testInThrow() throws Exception {
        // Valid
        throw new Exception();
    }

    public void testInThrow2() throws Exception {
        // Violation
        throw new Exception() ; // violation
    }

    /** Test in array initializer */
    public void testInArrayInitializer() {
        // Valid
        int[] arr1 = new int[] {1, 2, 3};

        // Violation
        int[] arr2 = new int[] {1, 2, 3 } ; // violation
    }
}
