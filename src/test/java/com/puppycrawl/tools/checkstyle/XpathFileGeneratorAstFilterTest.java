////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class XpathFileGeneratorAstFilterTest {

    @Test
    public void testAcceptNoToken() {
        final LocalizedMessage message = new LocalizedMessage(0, 0, 0, null, null, null, null,
                null, XpathFileGeneratorAstFilterTest.class, null);
        final TreeWalkerAuditEvent event = new TreeWalkerAuditEvent(null, null, message, null);
        final XpathFileGeneratorAstFilter filter = new XpathFileGeneratorAstFilter();

        Assert.assertTrue("filter accepted", filter.accept(event));

        final AuditEvent auditEvent = new AuditEvent(this, "Test.java", message);

        Assert.assertNull("filter has no queries",
                XpathFileGeneratorAstFilter.findCorrespondingXpathQuery(auditEvent));
    }

    @Test
    public void test() throws Exception {
        final LocalizedMessage message = new LocalizedMessage(3, 47, TokenTypes.LCURLY,
                "messages.properties", null, null, SeverityLevel.ERROR, null, LeftCurlyCheck.class,
                null);
        final TreeWalkerAuditEvent event = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAstFilter.java", message);
        final XpathFileGeneratorAstFilter filter = new XpathFileGeneratorAstFilter();

        Assert.assertTrue("filter accepted", filter.accept(event));

        final AuditEvent auditEvent = new AuditEvent(this,
                getPath("InputXpathFileGeneratorAstFilter.java"), message);

        Assert.assertEquals("expected xpath",
                "/CLASS_DEF[@text='InputXpathFileGeneratorAstFilter']/OBJBLOCK/LCURLY",
                XpathFileGeneratorAstFilter.findCorrespondingXpathQuery(auditEvent));
    }

    @Test
    public void testTabWidth() throws Exception {
        final LocalizedMessage message = new LocalizedMessage(6, 7, TokenTypes.LITERAL_RETURN,
                "messages.properties", null, null, SeverityLevel.ERROR, null,
                XpathFileGeneratorAstFilterTest.class, null);
        final TreeWalkerAuditEvent event = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAstFilter.java", message);
        final XpathFileGeneratorAstFilter filter = new XpathFileGeneratorAstFilter();
        filter.setTabWidth(6);

        Assert.assertTrue("filter accepted", filter.accept(event));

        final AuditEvent auditEvent = new AuditEvent(this,
                getPath("InputXpathFileGeneratorAstFilter.java"), message);

        Assert.assertEquals("expected xpath",
                "/CLASS_DEF[@text='InputXpathFileGeneratorAstFilter']/OBJBLOCK"
                        + "/METHOD_DEF[@text='tabMethod']/SLIST/LITERAL_RETURN",
                XpathFileGeneratorAstFilter.findCorrespondingXpathQuery(auditEvent));
    }

    /**
     * We cannot reproduce situation when {@code finishLocalSetup} is called
     * twice. So, we have to use reflection to be sure that even in such
     * situation state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClearState() throws Exception {
        final LocalizedMessage message = new LocalizedMessage(3, 47, TokenTypes.LCURLY,
                "messages.properties", null, null, SeverityLevel.ERROR, null, LeftCurlyCheck.class,
                null);
        final TreeWalkerAuditEvent event = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAstFilter.java", message);

        final XpathFileGeneratorAstFilter filter = new XpathFileGeneratorAstFilter();

        Assert.assertTrue("State is not cleared on finishLocalSetup", TestUtil
                .isStatefulFieldClearedDuringLocalSetup(filter, event, "MESSAGE_QUERY_MAP",
                    variableStack -> ((Map<LocalizedMessage, String>) variableStack).isEmpty()));
    }

    private static TreeWalkerAuditEvent createTreeWalkerAuditEvent(String fileName,
            LocalizedMessage message) throws Exception {
        final File file = new File(getPath(fileName));
        final FileText fileText = new FileText(file.getAbsoluteFile(), System.getProperty(
                "file.encoding", StandardCharsets.UTF_8.name()));
        final FileContents fileContents = new FileContents(fileText);
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);

        return new TreeWalkerAuditEvent(fileContents, fileName, message, rootAst);
    }

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/xpathfilegeneratorastfilter/"
                + filename;
    }

}
