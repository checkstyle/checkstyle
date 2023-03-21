/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

public class InputVariableDeclarationUsageDistanceGeneral2 {
    private void o() {
        boolean first = true; // violation 'Distance .* is 5.'
        do {
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            System.lineSeparator();
            if (first) {}
        } while(true);
    }

    private static void checkInvariants() {
        Set<Integer> allInvariants = new HashSet<Integer>(); // violation 'Distance .* is 2.'
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    System.lineSeparator();

                    allInvariants.add(k);
                }
            }
    }

    private void p() {
        float wet_delta = 0;

        if (wet_delta != 0) {
            for (int i = 0; i < 10; i++) {
                System.lineSeparator();
                System.lineSeparator();
                System.lineSeparator();
                System.lineSeparator();
                float wet = 0;
                wet += wet_delta;
            }
        } else if (false) {
        } else {
        }
    }

    public class MyResource implements AutoCloseable {
        public void close() throws Exception {
            System.out.println("Closing!");
        }
    }

    void method() throws Exception {
        byte[] buf=new byte[10]; // violation 'Distance .* is 3.'

        File b = new File("");
        try(MyResource myResource= new MyResource();
            InputStream finstr = Files.newInputStream(b.toPath());
            BufferedInputStream in = new BufferedInputStream(finstr,buf.length)){
            int length;
            while((length=in.read(buf))!=-1){
                System.out.println(buf.toString());
            }
        }
    }
}
