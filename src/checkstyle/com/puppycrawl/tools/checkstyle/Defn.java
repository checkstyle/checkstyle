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

    /** property name for the to-do pattern **/
    String TODO_PATTERN_PROP = "checkstyle.pattern.todo";
    /** property name for the static variable pattern **/
    String STATIC_PATTERN_PROP = "checkstyle.pattern.static";
    /** property name for the static final variable pattern **/
    String CONST_PATTERN_PROP = "checkstyle.pattern.const";
    /** property name for the member variable pattern **/
    String MEMBER_PATTERN_PROP = "checkstyle.pattern.member";
    /** property name for the public member variable pattern **/
    String PUBLIC_MEMBER_PATTERN_PROP = "checkstyle.pattern.publicmember";
    /** property name for the method local variable pattern **/
    String LOCAL_VAR_PATTERN_PROP = "checkstyle.pattern.localvar";
    /** property name for the method local final variable pattern **/
    String LOCAL_FINAL_VAR_PATTERN_PROP = "checkstyle.pattern.localfinalvar";
    /** property name for the maximum line length **/
    String MAX_LINE_LENGTH_PROP = "checkstyle.maxlinelen";
    /** property name for length of methods **/
    String MAX_METHOD_LENGTH_PROP = "checkstyle.maxmethodlen";
    /** property name for length of constructors **/
    String MAX_CONSTRUCTOR_LENGTH_PROP = "checkstyle.maxconstructorlen";
    /** property name for maximum number of parameters **/
    String MAX_PARAMETERS_PROP = "checkstyle.maxparameters";
    /** property name for allowing protected data **/
    String TAB_WIDTH_PROP = "checkstyle.tab.width";
    /** property name for allowing protected data **/
    String ALLOW_PROTECTED_PROP = "checkstyle.allow.protected";
    /** property name for allowing package data **/
    String ALLOW_PACKAGE_PROP = "checkstyle.allow.package";
    /** property name for allowing no author **/
    String ALLOW_NO_AUTHOR_PROP = "checkstyle.allow.noauthor";
    /** property name for visibility scope where Javadoc is checked **/
    String JAVADOC_CHECKSCOPE_PROP = "checkstyle.javadoc.scope";
    /** property name for checking for Runtime @throws in Javadoc **/
    String JAVADOC_CHECK_UNUSED_THROWS_PROP =
        "checkstyle.javadoc.checkUnusedThrows";
    /** property name for requiring package documentation */
    String REQUIRE_PACKAGE_HTML_PROP = "checkstyle.require.packagehtml";
    /** property name for requiring a version tag **/
    String REQUIRE_VERSION_PROP = "checkstyle.require.version";
    /** property name for the line length check exclusion pattern **/
    String IGNORE_LINE_LENGTH_PATTERN_PROP = "checkstyle.ignore.maxlinelen";
    /** property name for ignoring whitespace **/
    String IGNORE_WHITESPACE_PROP = "checkstyle.ignore.whitespace";
    /** property name for ignoring whitespace after casts **/
    String IGNORE_CAST_WHITESPACE_PROP = "checkstyle.ignore.whitespace.cast";
    /** property name for ignoring braces **/
    String IGNORE_BRACES_PROP = "checkstyle.ignore.braces";
    /** property name for cache file **/
    String CACHE_FILE_PROP = "checkstyle.cache.file";
    /** property name for ignoring line length of import statements **/
    String IGNORE_IMPORT_LENGTH_PROP = "checkstyle.ignore.importlength";

    /** property name for try block options **/
    String TRY_BLOCK_PROP = "checkstyle.block.try";
    /** property name for catch block options **/
    String CATCH_BLOCK_PROP = "checkstyle.block.catch";
    /** property name for finally block options **/
    String FINALLY_BLOCK_PROP = "checkstyle.block.finally";
    /** property name for the base directory **/
    String BASEDIR_PROP = "checkstyle.basedir";
    /** property name for wrapping lines on operators **/
    String WRAP_OP_PROP = "checkstyle.wrap.operator";

    /** property name for the locale language for reporting **/
    String LOCALE_LANGUAGE_PROP = "checkstyle.locale.language";
    /** property name for the locale country for reporting **/
    String LOCALE_COUNTRY_PROP = "checkstyle.locale.country";

    /** All the properties that are a boolean. They all must default to false */
    String[] ALL_BOOLEAN_PROPS = new String[]
        {
            ALLOW_NO_AUTHOR_PROP,
            ALLOW_PACKAGE_PROP,
            ALLOW_PROTECTED_PROP,
            IGNORE_BRACES_PROP,
            IGNORE_CAST_WHITESPACE_PROP,
            IGNORE_IMPORT_LENGTH_PROP,
            IGNORE_WHITESPACE_PROP,
            JAVADOC_CHECK_UNUSED_THROWS_PROP,
            REQUIRE_PACKAGE_HTML_PROP,
            REQUIRE_VERSION_PROP,
        };

    /** All the properties that are a regulare expression */
    String[] ALL_PATTERN_PROPS = new String[]
        {
            TODO_PATTERN_PROP,
            STATIC_PATTERN_PROP,
            CONST_PATTERN_PROP,
            MEMBER_PATTERN_PROP,
            PUBLIC_MEMBER_PATTERN_PROP,
            LOCAL_VAR_PATTERN_PROP,
            LOCAL_FINAL_VAR_PATTERN_PROP,
            IGNORE_LINE_LENGTH_PATTERN_PROP,
        };

    /** All the integer properties */
    String[] ALL_INT_PROPS = new String[]
        {
            MAX_CONSTRUCTOR_LENGTH_PROP,
            MAX_LINE_LENGTH_PROP,
            MAX_METHOD_LENGTH_PROP,
            MAX_PARAMETERS_PROP,
            TAB_WIDTH_PROP,
        };

    /** All the block properties */
    String[] ALL_BLOCK_PROPS = new String[]
        {
            TRY_BLOCK_PROP,
            CATCH_BLOCK_PROP,
            FINALLY_BLOCK_PROP,
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
