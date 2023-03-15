package org.checkstyle.suppressionxpathfilter.unnecessarysemicolonintrywithresources;

import java.io.PipedReader;
import java.io.Reader;

public class SuppressionXpathRegressionUnnecessarySemicolonInTryWithResources {
    void m() throws Exception {
        try(Reader good = new PipedReader()){}
        try(Reader good = new PipedReader();Reader better = new PipedReader()){}

        try(Reader bad = new PipedReader();){} //warn
        try(Reader bad = new PipedReader();Reader worse = new PipedReader();){} //warn

    }
}
