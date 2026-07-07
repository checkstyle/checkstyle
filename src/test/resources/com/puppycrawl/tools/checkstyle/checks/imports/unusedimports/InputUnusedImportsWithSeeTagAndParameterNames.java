/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.xml.sax.SAXParseException;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;

/**
 * @see javax.xml.transform.Transformer#transform(Source xmlSource, Result outputTarget)
 * @see javax.xml.validation.SchemaFactory#newSchema(SAXParseException exception)
 * @see com.sun.tools.javac.code.Symbol#method(JCFieldAccess fieldAccess)
 */
public class InputUnusedImportsWithSeeTagAndParameterNames {

    public int calculate() {
        return 0;
    }

}