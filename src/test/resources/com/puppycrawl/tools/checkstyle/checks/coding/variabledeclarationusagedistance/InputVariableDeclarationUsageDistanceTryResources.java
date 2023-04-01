/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.io.*;
import java.nio.file.Files;

public class InputVariableDeclarationUsageDistanceTryResources {
    public int methodTry() {
        String a = ""; // violation 'Distance .* is 2.'
        String b = "abc"; // violation 'Distance .* is 2.'
        System.out.println();
        try (AutoCloseable j = new java.io.StringReader(b);
             final AutoCloseable i = new java.io.StringReader(a);
             final AutoCloseable k = new java.io.StringReader(b);) {
            b.replace(a.charAt(0), 'b');
            String c = b.toString();
        } catch (Exception e) {
            System.out.println(a);
            throw new RuntimeException(e);
        }
        return 0;
    }

    String method2() throws Exception {
        String b = "";
        FileReader fr = new FileReader(b);
        BufferedReader br = new BufferedReader(fr);
        try {
            return br.readLine();
        } finally {
            br.close();
            fr.close();
        }
    }

    void method() {
        byte[] buf=new byte[10];

        File b = new File("");
        try(AutoCloseable j = new java.io.StringReader(b.toString());
            InputStream finstr = Files.newInputStream(b.toPath());
            BufferedInputStream in = new BufferedInputStream(finstr,buf.length)){
            int length;
            while((length=in.read(buf))!=-1){
                System.out.println(buf.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
