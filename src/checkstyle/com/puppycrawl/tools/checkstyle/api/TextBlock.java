////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

/**
 * A block of text from an inputfile that does not necessarily
 * have any grammatical structure.
 *
 * @author lkuehne
 */
public interface TextBlock
{
    /**
     * The actual text.
     * @return the text content of the text block.
     */
    String[] getText();

    /**
     * The line in the inputfile where the text block. Counting starts at 1.
     * @return first line of the text block
     */
    int getStartLineNo();

    /**
     * The first line of the text block. Counting starts from 1.
     * @return first line of the text block
     */
    int getEndLineNo();

    // TODO: add column information
}
