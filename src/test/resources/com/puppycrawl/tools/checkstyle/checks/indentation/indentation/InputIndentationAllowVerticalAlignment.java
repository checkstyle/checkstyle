import java.io.FileInputStream;                                          //indent:0 exp:0
import java.io.InputStream;                                              //indent:0 exp:0
import java.util.Arrays;                                                 //indent:0 exp:0
import java.util.function.BinaryOperator;                                //indent:0 exp:0

public class InputIndentationAllowVerticalAlignment {                    //indent:0 exp:0

    // 16. Constructor declaration with wrapped parameters               //indent:4 exp:4
    InputIndentationAllowVerticalAlignment(int a,                        //indent:4 exp:4
                                           int b,                        //indent:43 exp:43
                                           int c) {}                     //indent:43 exp:43

    InputIndentationAllowVerticalAlignment() {                           //indent:4 exp:4
        this(0,                                                       //indent:8 exp:8
             0,                                                          //indent:13 exp:13
             0);                                                         //indent:13 exp:13
    }                                                                    //indent:4 exp:4

    // 1. Method definition parameters                                   //indent:4 exp:4
    void method(int a,                                                   //indent:4 exp:4
                int b,                                                   //indent:16 exp:16
                int c) {}                                                //indent:16 exp:16

    // 2. Method call arguments                                          //indent:4 exp:4
    void methodCallArgs() {                                              //indent:4 exp:4
        method(1,                                                     //indent:8 exp:8
               2,                                                        //indent:15 exp:15
               3);                                                       //indent:15 exp:15
    }                                                                    //indent:4 exp:4

    static class Pair {                                                  //indent:4 exp:4
        Pair(int a, int b) {}                                            //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    // 3. Constructor call arguments                                     //indent:4 exp:4
    void constructorArgs() {                                             //indent:4 exp:4
        Object unused = new Pair("hello".length(),                       //indent:8 exp:8
                                 0);                                     //indent:33 exp:33
    }                                                                    //indent:4 exp:4

    // 4. Annotation member value pairs                                  //indent:4 exp:4
    @Deprecated(since = "9",                                             //indent:4 exp:4
                forRemoval = true)                                       //indent:16 exp:16
    void annotationPairs() {}                                            //indent:4 exp:4

    // 5. Arithmetic expression                                          //indent:4 exp:4
    void arithmeticExpr() {                                              //indent:4 exp:4
        int b = 1, c = 2, d = 3, e = 4;                                  //indent:8 exp:8
        int var = b + c * d                                              //indent:8 exp:8
                  - e;                                                   //indent:18 exp:18
    }                                                                    //indent:4 exp:4

    // 6. String concatenation                                           //indent:4 exp:4
    void stringConcat() {                                                //indent:4 exp:4
        String s = "hello "                                              //indent:8 exp:8
                   + "world "                                            //indent:19 exp:19
                   + "checkstyle";                                       //indent:19 exp:19
    }                                                                    //indent:4 exp:4

    // 7. Boolean logic chains                                           //indent:4 exp:4
    void booleanChain() {                                                //indent:4 exp:4
        int a = 1, b = 2, c = 3, d = 4, e = 5, f = 6;                    //indent:8 exp:8
        boolean flag = a > b                                             //indent:8 exp:8
                       && c > d                                          //indent:23 exp:23
                       && e > f;                                         //indent:23 exp:23
    }                                                                    //indent:4 exp:4

    // 8. Ternary expression                                             //indent:4 exp:4
    void ternaryExpr() {                                                 //indent:4 exp:4
        boolean condition = true;                                        //indent:8 exp:8
        int firstValue = 1, secondValue = 2;                             //indent:8 exp:8
        int var = condition                                              //indent:8 exp:8
                  ? firstValue                                           //indent:18 exp:18
                  : secondValue;                                         //indent:18 exp:18
    }                                                                    //indent:4 exp:4

    // 9. Chained method calls (Variant 4)                               //indent:4 exp:4
    void chainedCalls() {                                                //indent:4 exp:4
        int[] numbers = {1, 2, 3};                                       //indent:8 exp:8
        Arrays.stream(numbers)                                           //indent:8 exp:8
              .map(x -> x + 1)                                       //indent:14 exp:14
              .count();                                                  //indent:14 exp:14
    }                                                                    //indent:4 exp:4

    // 10. Builder pattern chaining                                      //indent:4 exp:4
    void builderChain() {                                                //indent:4 exp:4
        StringBuilder sb = new StringBuilder().append("a")               //indent:8 exp:8
                                              .append("b");              //indent:46 exp:46
    }                                                                    //indent:4 exp:4

    // 11. Field access chain                                            //indent:4 exp:4
    void fieldChain() {                                                  //indent:4 exp:4
        String s = "hello".concat(" world")                          //indent:8 exp:8
                          .toUpperCase();                                //indent:26 exp:26
    }                                                                    //indent:4 exp:4

    // 12. Nested expression inside method call                          //indent:4 exp:4
    void nestedInArgs() {                                                //indent:4 exp:4
        int a = 1, b = 2, c = 3, d = 4, e = 5;                           //indent:8 exp:8
        method(1,                                                     //indent:8 exp:8
               a * b + c * d - e,                                        //indent:15 exp:15
               0);                                                       //indent:15 exp:15
    }                                                                    //indent:4 exp:4

    // 13. if condition chain (negative example)                         //indent:4 exp:4
    void ifConditionChain() {                                            //indent:4 exp:4
        int a = 1, b = 2, c = 3, d = 4, e = 5, f = 6;                    //indent:8 exp:8
        if (a > b                                                        //indent:8 exp:8
            && c > d                                                     //indent:12 exp:12
            && e > f) {                                                  //indent:12 exp:12
            System.currentTimeMillis();                                  //indent:12 exp:12
        }                                                                //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    // 14. Lambda inside chained method call                             //indent:4 exp:4
    void lambdaInChain() {                                               //indent:4 exp:4
        int[] arr = {1, 2};                                              //indent:8 exp:8
        Arrays.stream(arr)                                               //indent:8 exp:8
              .map(i -> new StringBuilder().append(i)                //indent:14 exp:14
                                          .hashCode())                   //indent:42 exp:42
              .count();                                                  //indent:14 exp:14
    }                                                                    //indent:4 exp:4

    // 15. Complex method call args with operators                       //indent:4 exp:4
    void complexArgs() {                                                 //indent:4 exp:4
        int b = 1, c = 2, x = 3, y = 4;                                  //indent:8 exp:8
        int a = b + Math.addExact(c * 2,                                 //indent:8 exp:8
                                  x);                                    //indent:34 exp:34
        method(1,                                                     //indent:8 exp:8
               0,                                                        //indent:15 exp:15
               0);                                                       //indent:15 exp:15
    }                                                                    //indent:4 exp:4

    int g() { return 0; }                                                //indent:4 exp:4

    // 17. Lambda definition with wrapped parameters                     //indent:4 exp:4
    void lambdaParams() {                                                //indent:4 exp:4
        BinaryOperator<Integer> op = (x,                          //indent:8 exp:8
                                      y) -> x + y;                //indent:38 exp:38
    }                                                                    //indent:4 exp:4

    // 18. Array initializer with wrapped elements                       //indent:4 exp:4
    void arrayInit() {                                                   //indent:4 exp:4
        int[] arr = {1, 2,                                               //indent:8 exp:8
                     3, 4};                                              //indent:21 exp:21
    }                                                                    //indent:4 exp:4

    // 19. For loop with vertically aligned parts                        //indent:4 exp:4
    void forLoopAligned() {                                              //indent:4 exp:4
        for (int i = 0;                                                  //indent:8 exp:8
             i < 10;                                                     //indent:13 exp:13
             i++) {}                                                     //indent:13 exp:13
    }                                                                    //indent:4 exp:4

    // 20. Comma-separated variable declarations                         //indent:4 exp:4
    void commaDeclarations() {                                           //indent:4 exp:4
        int a = 1,                                                       //indent:8 exp:8
            b = 2,                                                       //indent:12 exp:12
            c = 3;                                                       //indent:12 exp:12
    }                                                                    //indent:4 exp:4

    // 21. For loop with comma-separated iterator                        //indent:4 exp:4
    void forLoopCommaIterator() {                                        //indent:4 exp:4
        for (int i = 0, j = 0; i < 10; i++,                              //indent:8 exp:8
                                       j++) {}                           //indent:39 exp:39
    }                                                                    //indent:4 exp:4

    // 23. Try-with-resources multi-resource                              //indent:4 exp:4
    void tryResource() {                                                  //indent:4 exp:4
        try (InputStream a = new FileInputStream("a");                    //indent:8 exp:8
             InputStream b = new FileInputStream("b")) {                 //indent:13 exp:13
        }                                                                 //indent:8 exp:8
    }                                                                     //indent:4 exp:4
}                                                                        //indent:0 exp:0
