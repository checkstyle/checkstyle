package com.puppycrawl.tools.checkstyle;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class implements the error handling policy for Checkstyle. The policy
 * can be summarised as being austere, dead set, disciplinary, dour, draconian,
 * exacting, firm, forbidding, grim, hard, hard-boiled, harsh, harsh, in line,
 * iron-fisted, no-nonsense, oppressive, persnickety, picky, prudish,
 * punctilious, puritanical, rigid, rigorous, scrupulous, set, severe, square,
 * stern, stickler, straight, strait-laced, stringent, stuffy, stuffy, tough,
 * unpermissive, unsparing or uptight.
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 */
class HardErrorHandler
    implements ErrorHandler
{
    /** the instance for use */
    static final HardErrorHandler INSTANCE = new HardErrorHandler();

    /** Prevent instances */
    private HardErrorHandler()
    {
    }
    
    /** @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException) */
    public void warning(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    /** @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException) */
    public void error(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    /** @see org.xml.sax.ErrorHandler#fatalError */
    public void fatalError(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }
}
