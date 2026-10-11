package com.puppycrawl.tools.checkstyle.grammar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class InputAstRegressionManyAlternativesInMultiCatch {
    public static void main(String[] args) {
        try {
            System.out.println(args[7]);
            File myFile = new File("myfile.txt");
            InputStream stream = myFile.toURL().openStream();
            foo1();
        } catch (ArrayIndexOutOfBoundsException | IOException
                | SQLException | SecurityException | OneMoreException e) {

        }
    }

    private static void foo1() throws RuntimeException, SQLException, OneMoreException {

    }

    private class SQLException extends Exception {

    }


    private class OneMoreException extends Exception {

    }
}
