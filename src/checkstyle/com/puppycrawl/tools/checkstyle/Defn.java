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

/**
 * Contains definitions common to the package.
 * Very important that all boolean properties default to false.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public interface Defn
{
    // TODO: Change to a class - follow the advice of Bloch.
    /** name of resource bundle for Checkstyle */
    String CHECKSTYLE_BUNDLE = "com.puppycrawl.tools.checkstyle.messages";

    /** property name for allowing protected data **/
    String TAB_WIDTH_PROP = "checkstyle.tab.width";
    /** property name for cache file **/
    String CACHE_FILE_PROP = "checkstyle.cache.file";

    /** property name for the base directory **/
    String BASEDIR_PROP = "checkstyle.basedir";

    /** property name for the locale language for reporting **/
    String LOCALE_LANGUAGE_PROP = "checkstyle.locale.language";
    /** property name for the locale country for reporting **/
    String LOCALE_COUNTRY_PROP = "checkstyle.locale.country";

    /** All the integer properties */
    String[] ALL_INT_PROPS = new String[]
        {
            TAB_WIDTH_PROP,
        };

    /** All the String properties */
    String[] ALL_STRING_PROPS = new String[]
        {
            BASEDIR_PROP,
            CACHE_FILE_PROP,
            LOCALE_COUNTRY_PROP,
            LOCALE_LANGUAGE_PROP,
        };
}
