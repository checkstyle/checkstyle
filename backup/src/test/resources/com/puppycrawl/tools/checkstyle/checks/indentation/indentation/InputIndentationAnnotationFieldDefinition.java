package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0
//indent:0 exp:0
import java.util.List;//indent:0 exp:0
//indent:0 exp:0
/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 8                                                         //indent:1 exp:1
 * forceStrictCondition = true                                             //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationAnnotationFieldDefinition {//indent:0 exp:0
    public @interface HasDefaultsAnnotation {//indent:4 exp:4
        char[] g() default {0, 0xCAFE, 'z', '€', 'ℕ', '"', '\'', '\t', '\n'};//indent:8 exp:8
    int[] m() default {1, 2, 3};//indent:4 exp:8 warn
            int[] x() default {1, 2, 3};//indent:12 exp:8 warn
    }//indent:4 exp:4
    int[] method() {//indent:4 exp:4
        String[] args;//indent:8 exp:8
        return new int [2];//indent:8 exp:8
    }//indent:4 exp:4
    private static class Test<T> {//indent:4 exp:4
    InputIndentationAnnotationFieldDefinition.HasDefaultsAnnotation anno1;//indent:4 exp:8 warn
    List<T>[] matrix1;//indent:4 exp:8 warn
        InputIndentationAnnotationFieldDefinition.HasDefaultsAnnotation anno2;//indent:8 exp:8
        List<T>[] matrix2;//indent:8 exp:8
    }//indent:4 exp:4
}//indent:0 exp:0
