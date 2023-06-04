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
import java.util.*;

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
        byte[] buf = new byte[10];
        File b = new File("");
        try (AutoCloseable j = new java.io.StringReader(b.toString());
             InputStream finstr = Files.newInputStream(b.toPath());
             BufferedInputStream in = new BufferedInputStream(finstr, buf.length)) {
            int length;
            while ((length = in.read(buf)) != -1) {
                System.out.println(buf.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    String method3() throws Exception {
        String b = "";
        FileReader fr = new FileReader("");
        BufferedReader br = new BufferedReader(fr);
        try {
            return br.readLine();
        } catch (Exception e) {
            FileReader t = new FileReader(b);
        } finally {
            br.close();
            fr.close();
            return b;
        }
    }
    class myClass {
        String m, n;
        myClass(String a, String b) {
            this.m = a;
            this.n = b;
        }
        AutoCloseable getAutoCloseable(int m) {
            return new AutoCloseable() {
                @Override
                public void close() throws Exception {

                }
            };
        }
    }
    void method4() throws Exception {
        String b = "abc";
        myClass a = new myClass("", b);
        Scanner scanner = new Scanner(System.in);
        scanner.nextBigInteger(100);
        try (final AutoCloseable t = a.getAutoCloseable(3);) {
            Integer number = 10;
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
            b.substring(0, a.toString().length());
        }
    }
    private void method5(OutputStream out, HashMap<String, myClass> foo) throws IOException {
        final boolean[] states = {foo.isEmpty(), foo.isEmpty()};
        Object[] keys = foo.keySet().toArray(new String[0]);

        // Until https://github.com/checkstyle/checkstyle/issues/13154
        Object[] values = foo.values().toArray(new myClass[0]);
                                                              // violation above 'Distance .* is 2.'
        for (int i = 0; i < states.length; i++) {
            if (states[i]) {
                out.write(((String) keys[i]).getBytes());
                myClass statistics = (myClass) values[i];
                assert statistics.hashCode() >= 0;
                out.write(statistics.hashCode());
                out.write(statistics.m.getBytes());
            }
        }
    }
}
