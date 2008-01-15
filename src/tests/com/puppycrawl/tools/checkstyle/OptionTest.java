package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.puppycrawl.tools.checkstyle.checks.AbstractOption;
import com.puppycrawl.tools.checkstyle.checks.blocks.BlockOption;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyOption;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapOption;
import com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption;
import org.junit.Test;

/**
 * @author Rick Giles
 * @version 14-Nov-2002
 */
public class OptionTest
{
    @Test public void testBlockOption()
    {
        BlockOption stmtOpt = BlockOption.STMT;
        assertEquals("STMT", "stmt", stmtOpt.toString());
        BlockOption textOpt = BlockOption.TEXT;
        assertEquals("TEXT", "text", textOpt.toString());
        BlockOption stmtDecode = (BlockOption)(stmtOpt.decode("stmt"));
        assertTrue("STMT decode", stmtOpt == stmtDecode);
        BlockOption textDecode = (BlockOption)(stmtOpt.decode("text"));
        assertTrue("TEXT decode", textOpt == textDecode);
    }

    @Test public void testLeftCurlyOption()
    {
        LeftCurlyOption eolOpt = LeftCurlyOption.EOL;
        assertEquals("EOL", "eol", eolOpt.toString());
        LeftCurlyOption nlOpt = LeftCurlyOption.NL;
        assertEquals("NL", "nl", nlOpt.toString());
        LeftCurlyOption nlowOpt = LeftCurlyOption.NLOW;
        assertEquals("NLOW", "nlow", nlowOpt.toString());
        LeftCurlyOption eolDecode = (LeftCurlyOption)(eolOpt.decode("eol"));
        assertTrue("EOL decode", eolOpt == eolDecode);
        LeftCurlyOption nlDecode = (LeftCurlyOption)(nlOpt.decode("nl"));
        assertTrue("NL decode", nlOpt == nlDecode);
        LeftCurlyOption nlowDecode = (LeftCurlyOption)(nlowOpt.decode("nlow"));
        assertTrue("NL decode", nlowOpt == nlowDecode);
    }

    @Test public void testOperatorWrapOption()
    {
        OperatorWrapOption eolOpt = OperatorWrapOption.EOL;
        assertEquals("EOL", "eol", eolOpt.toString());
        OperatorWrapOption nlOpt = OperatorWrapOption.NL;
        assertEquals("NL", "nl", nlOpt.toString());
        OperatorWrapOption eolDecode = (OperatorWrapOption)(eolOpt.decode("eol"));
        assertTrue("EOL decode", eolOpt == eolDecode);
        OperatorWrapOption nlDecode = (OperatorWrapOption)(nlOpt.decode("nl"));
        assertTrue("NL decode", nlOpt == nlDecode);
    }

    @Test public void testPadOption()
    {
        PadOption nospaceOpt = PadOption.NOSPACE;
        assertEquals("NOSPACE", "nospace", nospaceOpt.toString());
        PadOption spaceOpt = PadOption.SPACE;
        assertEquals("SPACE", "space", spaceOpt.toString());
        PadOption nospaceDecode = (PadOption)(nospaceOpt.decode("nospace"));
        assertTrue("NOSPACE decode", nospaceOpt == nospaceDecode);
        PadOption spaceDecode = (PadOption)(spaceOpt.decode("space"));
        assertTrue("SPACE decode", spaceOpt == spaceDecode);
    }

    @Test public void testRightCurlyOption()
    {
        RightCurlyOption aloneOpt = RightCurlyOption.ALONE;
        assertEquals("ALONE", "alone", aloneOpt.toString());
        RightCurlyOption sameOpt = RightCurlyOption.SAME;
        assertEquals("SAME", "same", sameOpt.toString());
        RightCurlyOption aloneDecode = (RightCurlyOption)(aloneOpt.decode("alone"));
        assertTrue("ALONE decode", aloneOpt == aloneDecode);
        RightCurlyOption sameDecode = (RightCurlyOption)(sameOpt.decode("same"));
        assertTrue("SAME decode", sameOpt == sameDecode);
    }

    @Test public void testEqualKeys()
    {
        LeftCurlyOption eolLeftCurl = LeftCurlyOption.EOL;
        LeftCurlyOption eolLeftCurlDecode =
            (LeftCurlyOption)(eolLeftCurl.decode("eol"));
        OperatorWrapOption eolOpWrap = OperatorWrapOption.EOL;
        OperatorWrapOption eolOpWrapDecode =
            (OperatorWrapOption)(eolOpWrap.decode("eol"));
        assertTrue("eol",
                   (AbstractOption)eolLeftCurlDecode
                        != (AbstractOption)eolOpWrapDecode);

        LeftCurlyOption nlLeftCurl = LeftCurlyOption.NL;
        LeftCurlyOption nlLeftCurlDecode =
            (LeftCurlyOption)(nlLeftCurl.decode("nl"));
        OperatorWrapOption nlOpWrap = OperatorWrapOption.NL;
        OperatorWrapOption nlOpWrapDecode =
            (OperatorWrapOption)(nlOpWrap.decode("eol"));
        assertTrue("nl",
                   (AbstractOption)nlLeftCurlDecode
                        != (AbstractOption)nlOpWrapDecode);
    }
}
