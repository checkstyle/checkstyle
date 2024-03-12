/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.OutputStream;

public class InputVariableDeclarationUsageDistance3 {
     private void encodeLine(
         byte[] data, int offset, int length, OutputStream out)
         throws IOException {
         out.write((byte) ((length & 0x3F) + ' '));
         byte a;  // ok
         byte b; // ok
         byte c;

         for (int i = 0; i < length;) {
             b = 1;
             c = 1;
             a = data[offset + i++];
             if (i < length) {
                 b = data[offset + i++];
                 if (i < length) {
                     c = data[offset + i++];
                 }
             }

             byte d1 = (byte) (((a >>> 2) & 0x3F) + ' ');
             byte d2 = (byte) ((((a << 4) & 0x30) | ((b >>> 4) & 0x0F)) + ' ');
             byte d3 = (byte) ((((b << 2) & 0x3C) | ((c >>> 6) & 0x3)) + ' ');
             byte d4 = (byte) ((c & 0x3F) + ' ');

             out.write(d1);
             out.write(d2);
             out.write(d3);
             out.write(d4);
         }
         out.write('\n');
     }

    public void test() {
          char char1 = 0;
          Tester tester = new Tester();
          Preconditions.checkNotNull(char1);
          Preconditions.checkNotNull(tester.hasId());
          Preconditions.checkNotNull(tester.hasId(), "Must have ID!");
          Preconditions.checkNotNull(tester.hasId(), "Must have %s!", "ID");
    }
}

class Tester2 { }

class Tester {
    public Object hasId() {
        return null;
    }
}
