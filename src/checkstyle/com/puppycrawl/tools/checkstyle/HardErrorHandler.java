////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
