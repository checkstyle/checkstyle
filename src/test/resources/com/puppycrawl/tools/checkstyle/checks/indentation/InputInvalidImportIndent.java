package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

import java.util //indent:0 exp:0
  .RandomAccess; import java.util.RandomAccess; //indent:2 exp:4 warn
 import java.util.RandomAccess; //indent:1 exp:0 warn
import java.util //indent:0 exp:0
                   .RandomAccess; //indent:19 exp:>=8

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 8                                                            //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidImportIndent implements RandomAccess {} //indent:0 exp:0
