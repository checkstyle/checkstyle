///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.ConfigurationUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class AllChecksTest extends AbstractModuleTestSupport {

    private static final Locale[] ALL_LOCALES = {
        Locale.CHINESE,
        Locale.ENGLISH,
        new Locale("es"),
        new Locale("fi"),
        Locale.FRENCH,
        Locale.GERMAN,
        Locale.JAPANESE,
        new Locale("pt"),
        new Locale("ru"),
        new Locale("tr"),
    };

    private static final Map<String, Set<String>> CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE =
            new HashMap<>();
    private static final Map<String, Set<String>> GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE =
            new HashMap<>();
    private static final Set<String> INTERNAL_MODULES;

    static {
        // checkstyle

        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceBefore", Stream.of(
                // we use GenericWhitespace for this behavior
                "GENERIC_START", "GENERIC_END").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AbbreviationAsWordInName", Stream.of(
                // enum values should be uppercase, we use EnumValueNameCheck instead
                "ENUM_CONSTANT_DEF").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("FinalLocalVariable", Stream.of(
                // we prefer all parameters be effectively final as to not damage readability
                // we use ParameterAssignmentCheck to enforce this
                "PARAMETER_DEF").collect(Collectors.toUnmodifiableSet()));
        // we have no need to block these specific tokens
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("IllegalToken",
                Stream.of("LITERAL_SUPER", "LITERAL_ASSERT", "ENUM_CONSTANT_DEF",
                        "TYPE_PARAMETERS", "TYPE_UPPER_BOUNDS", "NUM_DOUBLE", "LITERAL_SWITCH",
                        "ANNOTATIONS", "LITERAL_SHORT", "LITERAL_PROTECTED", "FOR_CONDITION",
                        "FOR_INIT", "LITERAL_LONG", "MINUS", "OBJBLOCK", "LITERAL_NULL",
                        "ANNOTATION", "LITERAL_TRUE", "COMMENT_CONTENT", "LITERAL_CHAR",
                        "PARAMETER_DEF", "POST_DEC", "ANNOTATION_FIELD_DEF", "BLOCK_COMMENT_END",
                        "TYPE", "LITERAL_INT", "BSR", "ENUM", "ANNOTATION_MEMBER_VALUE_PAIR",
                        "TYPECAST", "LITERAL_SYNCHRONIZED", "PLUS_ASSIGN", "DOT", "LPAREN",
                        "LITERAL_IF", "LITERAL_CATCH", "BAND", "INTERFACE_DEF", "LOR", "BNOT",
                        "METHOD_CALL", "AT", "ELLIPSIS", "ARRAY_INIT", "FOR_EACH_CLAUSE",
                        "LITERAL_THROWS", "CHAR_LITERAL", "CASE_GROUP", "POST_INC", "SEMI",
                        "LITERAL_FINALLY", "ASSIGN", "RESOURCE_SPECIFICATION", "STATIC_IMPORT",
                        "GENERIC_START", "IMPORT", "SL", "VARIABLE_DEF", "LITERAL_DOUBLE",
                        "RCURLY", "RESOURCE", "SR", "COMMA", "BAND_ASSIGN", "METHOD_DEF",
                        "LITERAL_VOID", "NUM_LONG", "LITERAL_TRANSIENT", "LITERAL_THIS", "LCURLY",
                        "MINUS_ASSIGN", "TYPE_LOWER_BOUNDS", "TYPE_ARGUMENT", "LITERAL_CLASS",
                        "INSTANCE_INIT", "DIV", "STAR", "UNARY_MINUS", "FOR_ITERATOR", "NOT_EQUAL",
                        "LE", "LITERAL_INTERFACE", "LITERAL_FLOAT", "LITERAL_INSTANCEOF",
                        "BOR_ASSIGN", "LT", "SL_ASSIGN", "ELIST", "ANNOTATION_ARRAY_INIT",
                        "MODIFIERS", "LITERAL_BREAK", "EXTENDS_CLAUSE", "TYPE_PARAMETER",
                        "LITERAL_DEFAULT", "STATIC_INIT", "BSR_ASSIGN", "TYPE_EXTENSION_AND",
                        "BOR", "LITERAL_PRIVATE", "LITERAL_THROW", "LITERAL_BYTE", "BXOR",
                        "WILDCARD_TYPE", "FINAL", "PARAMETERS", "RPAREN", "SR_ASSIGN",
                        "UNARY_PLUS", "EMPTY_STAT", "LITERAL_STATIC", "LITERAL_CONTINUE",
                        "STAR_ASSIGN", "LAMBDA", "RBRACK", "BXOR_ASSIGN", "CTOR_CALL",
                        "LITERAL_FALSE", "DO_WHILE", "LITERAL_PUBLIC", "LITERAL_WHILE", "PLUS",
                        "INC", "CTOR_DEF", "GENERIC_END", "DIV_ASSIGN", "SLIST", "LNOT", "LAND",
                        "LITERAL_ELSE", "ABSTRACT", "STRICTFP", "QUESTION", "LITERAL_NEW",
                        "LITERAL_RETURN", "SINGLE_LINE_COMMENT", "INDEX_OP", "EXPR",
                        "BLOCK_COMMENT_BEGIN", "PACKAGE_DEF", "IMPLEMENTS_CLAUSE", "NUM_FLOAT",
                        "LITERAL_DO", "EOF", "GE", "RESOURCES", "MOD", "DEC", "EQUAL",
                        "LITERAL_BOOLEAN", "CLASS_DEF", "COLON", "LITERAL_TRY", "ENUM_DEF", "GT",
                        "NUM_INT", "ANNOTATION_DEF", "METHOD_REF", "TYPE_ARGUMENTS",
                        "DOUBLE_COLON", "IDENT", "MOD_ASSIGN", "LITERAL_FOR", "SUPER_CTOR_CALL",
                        "STRING_LITERAL", "ARRAY_DECLARATOR", "LITERAL_CASE",
                        "PATTERN_VARIABLE_DEF", "RECORD_DEF", "LITERAL_RECORD",
                        "RECORD_COMPONENTS", "RECORD_COMPONENT_DEF", "COMPACT_CTOR_DEF",
                        "TEXT_BLOCK_LITERAL_BEGIN", "TEXT_BLOCK_CONTENT", "TEXT_BLOCK_LITERAL_END",
                        "LITERAL_YIELD", "SWITCH_RULE")
                        .collect(Collectors.toUnmodifiableSet()));
        // we have no need to block specific token text
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("IllegalTokenText",
                Stream.of("NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG", "IDENT",
                    "COMMENT_CONTENT", "STRING_LITERAL", "CHAR_LITERAL", "TEXT_BLOCK_CONTENT")
                    .collect(Collectors.toUnmodifiableSet()));
        // we do not use this check as it is deprecated
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WriteTag",
                Stream.of("ENUM_CONSTANT_DEF", "METHOD_DEF", "CTOR_DEF",
                    "ANNOTATION_FIELD_DEF", "RECORD_DEF", "COMPACT_CTOR_DEF")
                    .collect(Collectors.toUnmodifiableSet()));
        // state of the configuration when test was made until reason found in
        // https://github.com/checkstyle/checkstyle/issues/3730
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AnnotationLocation",
                Stream.of("CLASS_DEF", "CTOR_DEF", "ENUM_DEF", "INTERFACE_DEF",
                        "METHOD_DEF", "VARIABLE_DEF",
                        "RECORD_DEF", "COMPACT_CTOR_DEF")
                        .collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoLineWrap", Stream.of(
                // method/constructor declaration could be long due to "parameters/exceptions", it
                // is ok to be not strict there
                "METHOD_DEF", "CTOR_DEF", "COMPACT_CTOR_DEF",
                // type declaration could be long due to "extends/implements", it is ok to
                // be not strict there
                "CLASS_DEF", "ENUM_DEF", "INTERFACE_DEF", "RECORD_DEF")
                .collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceAfter", Stream.of(
                // whitespace after is preferred
                "TYPECAST", "LITERAL_SYNCHRONIZED").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("SeparatorWrap", Stream.of(
                // needs context to decide what type of parentheses should be separated or not
                // which this check does not provide
                "LPAREN", "RPAREN").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NeedBraces", Stream.of(
                // we prefer no braces here as it looks unusual even though they help avoid sharing
                // scope of variables
                "LITERAL_DEFAULT", "LITERAL_CASE").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("FinalParameters", Stream.of(
                // we prefer these to be effectively final as to not damage readability
                "FOR_EACH_CLAUSE", "LITERAL_CATCH").collect(Collectors.toUnmodifiableSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WhitespaceAround", Stream.of(
                // we prefer no spaces on one side or both for these tokens
                "ARRAY_INIT",
                "ELLIPSIS",
                // these are covered by GenericWhitespaceCheck
                "WILDCARD_TYPE", "GENERIC_END", "GENERIC_START")
            .collect(Collectors.toUnmodifiableSet()));

        // google
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AnnotationLocation", Stream.of(
                // state of the configuration when test was made until reason found in
                // https://github.com/checkstyle/checkstyle/issues/3730
                "ANNOTATION_DEF", "ANNOTATION_FIELD_DEF", "ENUM_CONSTANT_DEF", "PACKAGE_DEF")
                .collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AbbreviationAsWordInName", Stream.of(
                // enum values should be uppercase
                "ENUM_CONSTANT_DEF").collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoLineWrap", Stream.of(
                // method declaration could be long due to "parameters/exceptions", it is ok to
                // be not strict there
                "METHOD_DEF", "CTOR_DEF", "CLASS_DEF", "ENUM_DEF", "INTERFACE_DEF", "RECORD_DEF",
                "COMPACT_CTOR_DEF")
                .collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("SeparatorWrap", Stream.of(
                // location could be any to allow writing expressions for indexes evaluation
                // on new line, see https://github.com/checkstyle/checkstyle/issues/3752
                "RBRACK",
                // for some targets annotations can be used without wrapping, as described
                // in https://google.github.io/styleguide/javaguide.html#s4.8.5-annotations
                "AT",
                // location could be any to allow using for line separation in enum values,
                // see https://github.com/checkstyle/checkstyle/issues/3752
                "SEMI",
                // needs context to decide what type of parentheses should be separated or not
                // which this check does not provide
                "LPAREN", "RPAREN").collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NeedBraces", Stream.of(
                // google doesn't require or prevent braces on these
                "LAMBDA", "LITERAL_DEFAULT", "LITERAL_CASE")
            .collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("EmptyBlock", Stream.of(
                // google doesn't specifically mention empty braces at the start of a case/default
                "LITERAL_DEFAULT", "LITERAL_CASE",
                // can be empty for special cases via '6.2 Caught exceptions: not ignored'
                "LITERAL_CATCH",
                // specifically allowed via '5.2.4 Constant names'
                "ARRAY_INIT",
                // state of the configuration when test was made until
                // https://github.com/checkstyle/checkstyle/issues/4121
                "INSTANCE_INIT", "LITERAL_DO", "LITERAL_FOR", "LITERAL_SYNCHRONIZED",
                "LITERAL_WHILE", "STATIC_INIT").collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WhitespaceAround", Stream.of(
                //  allowed via '4.8.3 Arrays'
                "ARRAY_INIT",
                //  '...' is almost same as '[]' by meaning
                "ELLIPSIS",
                // google prefers no spaces on one side or both for these tokens
                "GENERIC_START", "GENERIC_END", "WILDCARD_TYPE")
                .collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("IllegalTokenText", Stream.of(
                // numerical types should not be included
                "NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG",
                // identifiers are covered by other checks
                "IDENT",
                // comments should be skipped as nobody write in octal or unicode code style
                "COMMENT_CONTENT",
                // until #14291
                "TEXT_BLOCK_CONTENT"
                )
                .collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("OperatorWrap", Stream.of(
                // specifically allowed via '4.5.1 Where to break' because the following are
                // assignment operators and they are allowed to break before or after the symbol
                "DIV_ASSIGN", "BOR_ASSIGN", "SL_ASSIGN", "ASSIGN", "BSR_ASSIGN", "BAND_ASSIGN",
                "PLUS_ASSIGN", "MINUS_ASSIGN", "SR_ASSIGN", "STAR_ASSIGN", "BXOR_ASSIGN",
                "MOD_ASSIGN",
                // COLON token ignored in check config, explained in
                // https://github.com/checkstyle/checkstyle/issues/4122
                "COLON").collect(Collectors.toUnmodifiableSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceBefore", Stream.of(
                // google uses GenericWhitespace for this behavior
                "GENERIC_START", "GENERIC_END",
                // whitespace is necessary between a type annotation and ellipsis
                // according '4.6.2 Horizontal whitespace point 9'
                "ELLIPSIS").collect(Collectors.toUnmodifiableSet()));
        INTERNAL_MODULES = Definitions.INTERNAL_MODULES.stream()
                .map(moduleName -> {
                    final String[] packageTokens = moduleName.split("\\.");
                    return packageTokens[packageTokens.length - 1];
                })
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/internal/allchecks";
    }

    @Test
    public void testAllModulesWithDefaultConfiguration() throws Exception {
        final String inputFilePath = getPath("InputAllChecksDefaultConfig.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        for (Class<?> module : CheckUtil.getCheckstyleModules()) {
            if (ModuleReflectionUtil.isRootModule(module)) {
                continue;
            }

            final DefaultConfiguration moduleConfig = createModuleConfig(module);
            if (module.equals(ImportControlCheck.class)) {
                // ImportControlCheck must have the import control configuration file to avoid
                // violation.
                moduleConfig.addProperty("file", getPath(
                        "InputAllChecksImportControl.xml"));
            }
            verify(moduleConfig, inputFilePath, expected);
        }
    }

    @Test
    public void testDefaultTokensAreSubsetOfAcceptableTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                assertWithMessage("%s's default tokens must be a subset of acceptable tokens.",
                            check.getName())
                        .that(isSubset(defaultTokens, acceptableTokens))
                        .isTrue();
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfAcceptableTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] requiredTokens = testedCheck.getRequiredTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                assertWithMessage("%s's required tokens must be a subset of acceptable tokens.",
                            check.getName())
                        .that(isSubset(requiredTokens, acceptableTokens))
                        .isTrue();
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfDefaultTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] requiredTokens = testedCheck.getRequiredTokens();

                assertWithMessage("%s's required tokens must be a subset of default tokens.",
                            check.getName())
                        .that(isSubset(requiredTokens, defaultTokens))
                        .isTrue();
            }
        }
    }

    @Test
    public void testAllModulesHaveMultiThreadAnnotation() throws Exception {
        for (Class<?> module : CheckUtil.getCheckstyleModules()) {
            if (ModuleReflectionUtil.isRootModule(module)
                    || ModuleReflectionUtil.isFilterModule(module)
                    || ModuleReflectionUtil.isFileFilterModule(module)
                    || ModuleReflectionUtil.isTreeWalkerFilterModule(module)) {
                continue;
            }

            assertWithMessage("module '" + module.getSimpleName()
                    + "' must contain a multi-thread annotation")
                            .that(module.isAnnotationPresent(GlobalStatefulCheck.class)
                                    || module.isAnnotationPresent(FileStatefulCheck.class)
                                    || module.isAnnotationPresent(StatelessCheck.class))
                            .isTrue();
        }
    }

    @Test
    public void testAllModulesAreReferencedInConfigFile() throws Exception {
        final Set<String> modulesReferencedInConfig = CheckUtil.getConfigCheckStyleModules();
        final Set<String> moduleNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules());

        moduleNames.removeAll(INTERNAL_MODULES);
        moduleNames.stream().filter(check -> !modulesReferencedInConfig.contains(check))
            .forEach(check -> {
                final String errorMessage = String.format(Locale.ROOT,
                    "%s is not referenced in checkstyle-checks.xml", check);
                assertWithMessage(errorMessage).fail();
            });
    }

    @Test
    public void testAllCheckTokensAreReferencedInCheckstyleConfigFile() throws Exception {
        final Configuration configuration = ConfigurationUtil
                .loadConfiguration("config/checkstyle-checks.xml");

        validateAllCheckTokensAreReferencedInConfigFile("checkstyle", configuration,
                CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE, false);
    }

    @Test
    public void testAllCheckTokensAreReferencedInGoogleConfigFile() throws Exception {
        final Configuration configuration = ConfigurationUtil
                .loadConfiguration("src/main/resources/google_checks.xml");

        validateAllCheckTokensAreReferencedInConfigFile("google", configuration,
                GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE, true);
    }

    private static void validateAllCheckTokensAreReferencedInConfigFile(String configName,
            Configuration configuration, Map<String, Set<String>> tokensToIgnore,
            boolean defaultTokensMustBeExplicit) throws Exception {
        final ModuleFactory moduleFactory = TestUtil.getPackageObjectFactory();
        final Set<Configuration> configChecks = ConfigurationUtil.getChecks(configuration);

        final Map<String, Set<String>> configCheckTokens = new HashMap<>();
        final Map<String, Set<String>> checkTokens = new HashMap<>();

        for (Configuration checkConfig : configChecks) {
            final String checkName = checkConfig.getName();
            final Object instance;

            try {
                instance = moduleFactory.createModule(checkName);
            }
            catch (CheckstyleException ex) {
                throw new CheckstyleException("Couldn't find check: " + checkName, ex);
            }
            final AbstractCheck check;
            if (instance instanceof AbstractCheck
                    && !isAllTokensAcceptable((AbstractCheck) instance)) {
                check = (AbstractCheck) instance;
            }
            else {
                // we can not have in our config test for all tokens
                continue;
            }

            Set<String> configTokens = configCheckTokens.get(checkName);

            if (configTokens == null) {
                configTokens = new HashSet<>();

                configCheckTokens.put(checkName, configTokens);

                // add all overridden tokens
                final Set<String> overrideTokens = tokensToIgnore.get(checkName);

                if (overrideTokens != null) {
                    configTokens.addAll(overrideTokens);
                }

                configTokens.addAll(CheckUtil.getTokenNameSet(check.getRequiredTokens()));
                checkTokens.put(checkName,
                        CheckUtil.getTokenNameSet(check.getAcceptableTokens()));
            }

            try {
                configTokens.addAll(Arrays.asList(checkConfig.getProperty("tokens").trim()
                        .split(",\\s*")));
            }
            catch (CheckstyleException ex) {
                // no tokens defined, so it is using default
                if (defaultTokensMustBeExplicit) {
                    validateDefaultTokens(checkConfig, check, configTokens);
                }
                else {
                    configTokens.addAll(CheckUtil.getTokenNameSet(check.getDefaultTokens()));
                }
            }
        }
        for (Entry<String, Set<String>> entry : checkTokens.entrySet()) {
            final Set<String> actual = configCheckTokens.get(entry.getKey());
            assertWithMessage("'" + entry.getKey()
                    + "' should have all acceptable tokens from check in " + configName
                    + " config or specify an override to ignore the specific tokens")
                .that(actual)
                .isEqualTo(entry.getValue());
        }
    }

    private static boolean isAllTokensAcceptable(AbstractCheck check) {
        return Arrays.equals(check.getAcceptableTokens(), TokenUtil.getAllTokenIds());
    }

    private static void validateDefaultTokens(Configuration checkConfig, AbstractCheck check,
                                              Set<String> configTokens) {

        final BitSet defaultTokensSet = TokenUtil.asBitSet(check.getDefaultTokens());
        final BitSet requiredTokensSet = TokenUtil.asBitSet(check.getRequiredTokens());

        if (defaultTokensSet.equals(requiredTokensSet)) {
            configTokens.addAll(
                    CheckUtil.getTokenNameSet(check.getDefaultTokens()));
        }
        else {
            assertWithMessage("All default tokens should be used in config for "
                    + checkConfig.getName()).fail();
        }
    }

    @Test
    public void testAllCheckstyleModulesHaveXdocDocumentation() throws Exception {
        final Set<String> checkstyleModulesNames = CheckUtil.getSimpleNames(CheckUtil
                .getCheckstyleModules());
        final Set<String> modulesNamesWhichHaveXdocs = XdocUtil.getModulesNamesWhichHaveXdoc();

        // these are documented on non-'config_' pages
        checkstyleModulesNames.remove("TreeWalker");
        checkstyleModulesNames.remove("Checker");
        // temporarily hosted in test folder
        checkstyleModulesNames.removeAll(INTERNAL_MODULES);
        checkstyleModulesNames.stream()
            .filter(moduleName -> !modulesNamesWhichHaveXdocs.contains(moduleName))
            .forEach(moduleName -> {
                final String missingModuleMessage = String.format(Locale.ROOT,
                    "Module %s does not have xdoc documentation.",
                    moduleName);
                assertWithMessage(missingModuleMessage).fail();
            });
    }

    @Test
    public void testAllCheckstyleModulesInCheckstyleConfig() throws Exception {
        final Set<String> configChecks = CheckUtil.getConfigCheckStyleModules();
        final Set<String> moduleNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules());
        moduleNames.removeAll(INTERNAL_MODULES);
        for (String moduleName : moduleNames) {
            assertWithMessage("checkstyle-checks.xml is missing module: " + moduleName)
                    .that(configChecks)
                    .contains(moduleName);
        }
    }

    @Test
    public void testAllCheckstyleChecksHaveMessage() throws Exception {
        for (Class<?> module : CheckUtil.getCheckstyleChecks()) {
            final String name = module.getSimpleName();
            final Set<Field> messages = CheckUtil.getCheckMessages(module, false);

            // No messages in just module
            if ("SuppressWarningsHolder".equals(name)) {
                assertWithMessage(name + " should not have any 'MSG_*' fields for error messages")
                        .that(messages)
                        .isEmpty();
            }
            else {
                assertWithMessage(
                        name + " should have at least one 'MSG_*' field for error messages")
                                .that(messages)
                                .isNotEmpty();
            }
        }
    }

    @Test
    public void testAllCheckstyleMessages() throws Exception {
        final Map<String, List<String>> usedMessages = new TreeMap<>();

        // test validity of messages from modules
        for (Class<?> module : CheckUtil.getCheckstyleModules()) {
            for (Field message : CheckUtil.getCheckMessages(module, true)) {
                assertWithMessage(module.getSimpleName() + "." + message.getName()
                                + " should be 'public static final'")
                    .that(message.getModifiers())
                    .isEqualTo(Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);

                // below is required for package/private classes
                message.trySetAccessible();

                if (!INTERNAL_MODULES.contains(module.getSimpleName())) {
                    verifyCheckstyleMessage(usedMessages, module, message);
                }
            }
        }

        // test properties for messages not used by checks
        for (Entry<String, List<String>> entry : usedMessages.entrySet()) {
            final Properties pr = new Properties();
            pr.load(AllChecksTest.class.getResourceAsStream(
                    "/" + entry.getKey().replace('.', '/') + "/messages.properties"));

            for (Object key : pr.keySet()) {
                // hidden exception messages
                if ("translation.wrongLanguageCode".equals(key)) {
                    continue;
                }

                assertWithMessage("property '" + key + "' isn't used by any check in package '"
                                      + entry.getKey() + "'")
                        .that(entry.getValue())
                        .contains(key.toString());
            }
        }
    }

    private static void verifyCheckstyleMessage(Map<String, List<String>> usedMessages,
            Class<?> module, Field message) throws Exception {
        final String messageString = message.get(null).toString();
        final String packageName = module.getPackage().getName();
        final List<String> packageMessages =
                usedMessages.computeIfAbsent(packageName, key -> new ArrayList<>());

        packageMessages.add(messageString);

        for (Locale locale : ALL_LOCALES) {
            String result = null;

            try {
                result = CheckUtil.getCheckMessage(module, locale, messageString);
            }
            // -@cs[IllegalCatch] There is no other way to deliver filename that was used
            catch (Exception ex) {
                assertWithMessage(module.getSimpleName() + " with the message '" + messageString
                        + "' in locale '" + locale.getLanguage() + "' failed with: "
                        + ex.getClass().getSimpleName() + " - " + ex.getMessage()).fail();
            }

            assertWithMessage(module.getSimpleName() + " should have text for the message '"
                    + messageString + "' in locale " + locale.getLanguage() + "'")
                .that(result)
                .isNotNull();
            assertWithMessage("%s should have non-empty text for the message '%s' in locale '%s'",
                            module.getSimpleName(), messageString, locale.getLanguage())
                    .that(result.trim())
                    .isNotEmpty();
            assertWithMessage(
                    module.getSimpleName() + " should have non-TODO text for the message '"
                            + messageString + "' in locale " + locale.getLanguage() + "'")
                                    .that(!"todo.match".equals(messageString)
                                            && result.trim().startsWith("TODO"))
                                    .isFalse();
        }
    }

    /**
     * Checks that an array is a subset of other array.
     *
     * @param array to check whether it is a subset.
     * @param arrayToCheckIn array to check in.
     * @return {@code true} if all elements in {@code array} are in {@code arrayToCheckIn}.
     */
    private static boolean isSubset(int[] array, int... arrayToCheckIn) {
        Arrays.sort(arrayToCheckIn);
        boolean result = true;
        for (final int element : array) {
            if (Arrays.binarySearch(arrayToCheckIn, element) < 0) {
                result = false;
                break;
            }
        }
        return result;
    }

}
