////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Contains definitions common to the package.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
interface Defn
{
    /** property name for the parameter pattern **/
    String PARAMETER_PATTERN_PROP = "checkstyle.pattern.parameter";
    /** property name for the static variable pattern **/
    String STATIC_PATTERN_PROP = "checkstyle.pattern.static";
    /** property name for the static final variable pattern **/
    String CONST_PATTERN_PROP = "checkstyle.pattern.const";
    /** property name for the member variable pattern **/
    String MEMBER_PATTERN_PROP = "checkstyle.pattern.member";
    /** property name for the public member variable pattern **/
    String PUBLIC_MEMBER_PATTERN_PROP = "checkstyle.pattern.publicmember";
    /** property name for the type pattern **/
    String TYPE_PATTERN_PROP = "checkstyle.pattern.type";
    /** property name for the maximum line length **/
    String MAX_LINE_LENGTH_PROP = "checkstyle.maxlinelen";
    /** property name for length of methods **/
    String MAX_METHOD_LENGTH_PROP = "checkstyle.maxmethodlen";
    /** property name for length of constructors **/
    String MAX_CONSTRUCTOR_LENGTH_PROP = "checkstyle.maxconstructorlen";
    /** property name for length of files **/
    String MAX_FILE_LENGTH_PROP = "checkstyle.maxfilelen";
    /** property name for allowing tabs **/
    String ALLOW_TABS_PROP = "checkstyle.allow.tabs";
    /** property name for allowing protected data **/
    String ALLOW_PROTECTED_PROP = "checkstyle.allow.protected";
    /** property name for allowing no author **/
    String ALLOW_NO_AUTHOR_PROP = "checkstyle.allow.noauthor";
    /** property name for header file **/
    String HEADER_FILE_PROP = "checkstyle.header.file";
    /** property name for line in header file to ignore **/
    String HEADER_IGNORE_LINE_PROP = "checkstyle.header.ignoreline";
    /** property name for relaxing checking on Javadoc **/
    String RELAX_JAVADOC_PROP = "checkstyle.javadoc.relax";
    /** property name for ignoring checking on Javadoc **/
    String IGNORE_JAVADOC_PROP = "checkstyle.javadoc.ignore";
    /** property name for ignoring import statements **/
    String IGNORE_IMPORTS_PROP = "checkstyle.ignore.imports";
    /** property name for ignoring whitespace **/
    String IGNORE_WHITESPACE_PROP = "checkstyle.ignore.whitespace";
    /** property name for ignoring braces **/
    String IGNORE_BRACES_PROP = "checkstyle.ignore.braces";
    /** property name for cache file **/
    String CACHE_FILE_PROP = "checkstyle.cache.file";
    /** property name for ignoring line length of import statements **/
    String IGNORE_IMPORT_LENGTH_PROP = "checkstyle.ignore.importlength";
}
