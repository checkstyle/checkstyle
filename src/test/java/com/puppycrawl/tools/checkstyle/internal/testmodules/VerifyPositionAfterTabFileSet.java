package com.puppycrawl.tools.checkstyle.internal.testmodules;

import java.io.File;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class VerifyPositionAfterTabFileSet extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) {
            int lineNumber = 0;
            for (String line : getFileContents().getLines()) {
                final int position = line.lastIndexOf('\t');
                lineNumber++;

                if (position != -1) {
                    log(lineNumber, position + 1, "violation");
                }
            }
        }

    }
