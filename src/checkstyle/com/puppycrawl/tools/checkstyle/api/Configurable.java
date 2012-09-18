////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
 * A component that can be configured.  The general idea of
 * Configuration/Configurable was taken from <a target="_top"
 * href="http://jakarta.apache.org/avalon/">Jakarta's Avalon framework</a>.
 * @author lkuehne
 */
public interface Configurable
{
    /**
     * Configures this component.
     * @param aConfiguration the configuration to use.
     * @throws CheckstyleException if there is a configuration error.
     */
    void configure(Configuration aConfiguration) throws CheckstyleException;
}
