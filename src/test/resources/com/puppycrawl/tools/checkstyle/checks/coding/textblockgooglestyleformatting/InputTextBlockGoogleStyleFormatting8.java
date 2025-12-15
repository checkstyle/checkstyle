/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

import java.io.Serial;

public class InputTextBlockGoogleStyleFormatting8 extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5009606293886748304L;

    public InputTextBlockGoogleStyleFormatting8(
        final String recordBatchEntry,
        final int entryLength,
        final int recordBatchEntriesSize,
        final int batchSize) {
        super(
          """
              Can't append entry: '%s' with size: %d this would exceed the maximum batch size. \
              [ currentBatchEntryCount: %d, currentBatchSize: %d]"""
                  // 2 violations above:
                  //   'Closing quotes (""") of text-block should not be preceded by non'
                  //   'Text-block quotes are not vertically aligned'
                  .formatted(
                          recordBatchEntry,
                          entryLength,
                          batchSize,
                          recordBatchEntriesSize
                  ));
    }

    enum SlExample {
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text indentation is less than opening quotes indentation'
        CA_RSA("RSA", """
                -----BEGIN CERTIFICATE-----
                """), // violation 'Text-block quotes are not vertically aligned'

        SERVER_EXAMPLE_RSA("RSA",
                """
                        -----BEGIN CERTIFICATE-----
                   """), // violation 'Text-block quotes are not vertically aligned'
        CLIENT_EXAMPLE_RSA("RSA",
             """
               -----BEGIN CERTIFICATE-----
             """), // violation 3 lines below 'Text indentation is less than opening quotes indentation'
        SIMPLE_REQUEST_RSA("RSA",
                         """
               -----BEGIN CERTIFICATE-----
             """, // violation 'Text-block quotes are not vertically aligned'
                """
                -----BEGIN CERTIFICATE-----
                """);

        SlExample(String rsa, String s) {}
        SlExample(String rsa, String s1, String s2) {}
    }
}
