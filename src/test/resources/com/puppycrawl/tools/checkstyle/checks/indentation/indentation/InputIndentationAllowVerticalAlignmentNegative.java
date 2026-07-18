import java.io.FileInputStream;                                             //indent:0 exp:0
import java.io.InputStream;                                                 //indent:0 exp:0
import java.util.Arrays;                                                    //indent:0 exp:0
import java.util.function.BinaryOperator;                                   //indent:0 exp:0

public class InputIndentationAllowVerticalAlignmentNegative {               //indent:0 exp:0

    int b, c, d, e;                                                         //indent:4 exp:4

    // N1: Misaligned parameter continuation                                //indent:4 exp:4
    void paramMethod(int a,                                                 //indent:4 exp:4
      int b) {}  // violation                                               //indent:6 exp:12 warn

    // N2: Misaligned method call argument                                  //indent:4 exp:4
    void methodCallArgs() {                                                 //indent:4 exp:4
        method(1,                                                           //indent:8 exp:8
         2);  // violation                                                  //indent:9 exp:12 warn
    }                                                                       //indent:4 exp:4

    void method(int a, int b) {}                                            //indent:4 exp:4

    // N3: Misaligned constructor call argument                             //indent:4 exp:4
    void constructorArgs() {                                                //indent:4 exp:4
        Object unused = new java.util.HashMap<String, String>(              //indent:8 exp:8
                         10);  // violation                                 //indent:25 exp:16 warn
    }                                                                       //indent:4 exp:4

    // N4: Misaligned annotation member value pair                          //indent:4 exp:4
    @Deprecated(since = "9",                                                //indent:4 exp:4
      forRemoval = true)  // violation                                      //indent:6 exp:12 warn
    void annotationPairs() {}                                               //indent:4 exp:4

    // N5: Single 2-line wrap (only 1 continuation, no alignment possible)  //indent:4 exp:4
    void arithmeticExpr() {                                                 //indent:4 exp:4
        int var = b + c * d                                                 //indent:8 exp:8
          - e;  // violation                                                //indent:10 exp:16 warn
    }                                                                       //indent:4 exp:4

    // N6: Value continuation in comma-separated decl — IDENT guard         //indent:4 exp:4
    void valueContinuation() {                                              //indent:4 exp:4
        int a = 3, b =                                                      //indent:8 exp:8
            3;  // violation                                                //indent:12 exp:16 warn
    }                                                                       //indent:4 exp:4

    // N7: Misaligned ternary                                               //indent:4 exp:4
    void ternaryExpr() {                                                    //indent:4 exp:4
        boolean condition = true;                                           //indent:8 exp:8
        int var = condition                                                 //indent:8 exp:8
          ? 1  // violation                                                 //indent:10 exp:16 warn
          : 2;  // violation                                                //indent:10 exp:16 warn
    }                                                                       //indent:4 exp:4

    // N8: Misaligned for loop parts                                        //indent:4 exp:4
    void forLoopAligned() {                                                 //indent:4 exp:4
        for (int i = 0;                                                     //indent:8 exp:8
          i < 10;  // violation                                             //indent:10 exp:12 warn
          i++) {}  // violation                                             //indent:10 exp:12 warn
    }                                                                       //indent:4 exp:4

    // N9: Misaligned comma-separated variable                              //indent:4 exp:4
    void commaDeclarations() {                                              //indent:4 exp:4
        int a = 1,                                                          //indent:8 exp:8
          b = 2,  // violation                                              //indent:10 exp:16 warn
          c = 3;  // violation                                              //indent:10 exp:16 warn
    }                                                                       //indent:4 exp:4

    // N10: Misaligned try-with-resources                                   //indent:4 exp:4
    void tryResource() {                                                    //indent:4 exp:4
        try (InputStream a = new FileInputStream("a");                      //indent:8 exp:8
          InputStream b = new FileInputStream("b")) {                       //indent:10 exp:16 warn
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0
