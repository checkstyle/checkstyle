/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.xml.sax.SAXParseException;

/**
 * @see javax.xml.transform.Transformer#transform(Source xmlSource, Result outputTarget)
 * @see javax.xml.validation.SchemaFactory#newSchema(SAXParseException exception)
 */
public class InputUnusedImportsWithSeeReferenceAndParameterNames {

    public int calculate() {
        return 0;
    }

}