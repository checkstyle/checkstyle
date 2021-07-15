package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* Config:
 * max = "2"
 */
public class InputClassFanOutComplexityRemoveMultiCatchBitwiseOr { // ok
    public static void main(String[] args) {
        try {
            System.out.println(args[7]);
            File myFile = new File("myfile.txt");
            InputStream stream = myFile.toURL().openStream();
        } catch (ArrayIndexOutOfBoundsException | IOException e) {

        }
    }

}
