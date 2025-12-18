package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

import java.io.BufferedWriter;                                             //indent:0 exp:0
import java.nio.charset.Charset;                                           //indent:0 exp:0
import java.nio.file.Files;                                                //indent:0 exp:0
import java.nio.file.Path;                                                 //indent:0 exp:0

/**                                                                        //indent:0 exp:0
 * Test input for checkLeftParen lparen violation in TryHandler.           //indent:1 exp:1
 * basicOffset = 4                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 4                                                          //indent:1 exp:1
 * forceStrictCondition = false                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * tabWidth = 4                                                            //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 */                                                                        //indent:1 exp:1
public class InputIndentationTryResourcesLparenViolation {                 //indent:0 exp:0
    void method(Path filePath, Charset charset) throws Exception {         //indent:4 exp:4

        try                                                                //indent:8 exp:8
(                                                                          //indent:0 exp:8,12 warn
            BufferedWriter writer =                                        //indent:12 exp:12
                    Files.newBufferedWriter(filePath, charset)             //indent:20 exp:20
        ) {                                                                //indent:8 exp:8
            writer.flush();                                                //indent:12 exp:12
        }                                                                  //indent:8 exp:8
    }                                                                      //indent:4 exp:4
}                                                                          //indent:0 exp:0
