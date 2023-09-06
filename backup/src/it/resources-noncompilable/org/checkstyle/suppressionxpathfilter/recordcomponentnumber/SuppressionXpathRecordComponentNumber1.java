//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 *
 * max = 8
 */
public record SuppressionXpathRecordComponentNumber1(int x, int y, int z, // warn
                                                int a, int b, int c,
                                                int d, int e, int f,
                                                int g, int h, int i,
                                                int j, int k,
                                                 String... myVarargs) {

}
