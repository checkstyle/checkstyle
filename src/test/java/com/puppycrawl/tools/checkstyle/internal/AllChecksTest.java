////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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

public class AllChecksTest extends AbstractModuleTestSupport {

    private static final Locale[] ALL_LOCALES = {
        Locale.GERMAN,
        new Locale("es"),
        new Locale("fi"),
        Locale.FRENCH,
        Locale.JAPANESE,
        new Locale("pt"),
        new Locale("tr"),
        Locale.CHINESE,
        Locale.ENGLISH,
    };

    private static final Map<String, Set<String>> CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE =
            new HashMap<>();
    private static final Map<String, Set<String>> GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE =
            new HashMap<>();

    static {
        // checkstyle

        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceBefore", Stream.of(
                // we use GenericWhitespace for this behavior
                "GENERIC_START", "GENERIC_END").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AbbreviationAsWordInName", Stream.of(
                // enum values should be uppercase, we use EnumValueNameCheck instead
                "ENUM_CONSTANT_DEF").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("FinalLocalVariable", Stream.of(
                // we prefer all parameters be effectively final as to not damage readability
                // we use ParameterAssignmentCheck to enforce this
                "PARAMETER_DEF").collect(Collectors.toSet()));
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
                        "STRING_LITERAL", "ARRAY_DECLARATOR", "LITERAL_CASE").collect(
                        Collectors.toSet()));
        // we have no need to block specific token text
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("IllegalTokenText",
                Stream.of("NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG", "IDENT",
                    "COMMENT_CONTENT", "STRING_LITERAL", "CHAR_LITERAL")
                    .collect(Collectors.toSet()));
        // we do not use this check as it is deprecated
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WriteTag",
                Stream.of("ENUM_CONSTANT_DEF", "METHOD_DEF", "CTOR_DEF", "ANNOTATION_FIELD_DEF")
                        .collect(Collectors.toSet()));
        // state of the configuration when test was made until reason found in
        // https://github.com/checkstyle/checkstyle/issues/3730
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AnnotationLocation",
                Stream.of("CLASS_DEF", "CTOR_DEF", "ENUM_DEF", "INTERFACE_DEF",
                        "METHOD_DEF", "VARIABLE_DEF").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoLineWrap", Stream.of(
                // method declaration could be long due to "parameters/exceptions", it is ok to
                // be not strict there
                "METHOD_DEF", "CTOR_DEF",
                // type declaration could be long due to "extends/implements", it is ok to
                // be not strict there
                "CLASS_DEF", "ENUM_DEF", "INTERFACE_DEF")
                .collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceAfter", Stream.of(
                // whitespace after is preferred
                "TYPECAST", "LITERAL_SYNCHRONIZED").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("SeparatorWrap", Stream.of(
                // needs context to decide what type of parentheses should be separated or not
                // which this check does not provide
                "LPAREN", "RPAREN").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NeedBraces", Stream.of(
                // we prefer no braces here as it looks unusual even though they help avoid sharing
                // scope of variables
                "LITERAL_DEFAULT", "LITERAL_CASE").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("FinalParameters", Stream.of(
                // we prefer these to be effectively final as to not damage readability
                "FOR_EACH_CLAUSE", "LITERAL_CATCH").collect(Collectors.toSet()));
        CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WhitespaceAround", Stream.of(
                // we prefer no spaces on one side or both for these tokens
                "ARRAY_INIT",
                "ELLIPSIS",
                // these are covered by GenericWhitespaceCheck
                "WILDCARD_TYPE", "GENERIC_END", "GENERIC_START").collect(Collectors.toSet()));

        // google
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AnnotationLocation", Stream.of(
                // state of the configuration when test was made until reason found in
                // https://github.com/checkstyle/checkstyle/issues/3730
                "ANNOTATION_DEF", "ANNOTATION_FIELD_DEF", "ENUM_CONSTANT_DEF", "PACKAGE_DEF")
                .collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("AbbreviationAsWordInName", Stream.of(
                // enum values should be uppercase
                "ENUM_CONSTANT_DEF").collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoLineWrap", Stream.of(
                // method declaration could be long due to "parameters/exceptions", it is ok to
                // be not strict there
                "METHOD_DEF", "CTOR_DEF", "CLASS_DEF", "ENUM_DEF", "INTERFACE_DEF")
                .collect(Collectors.toSet()));
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
                "LPAREN", "RPAREN").collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NeedBraces", Stream.of(
                // google doesn't require or prevent braces on these
                "LAMBDA", "LITERAL_DEFAULT", "LITERAL_CASE").collect(Collectors.toSet()));
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
                "LITERAL_WHILE", "STATIC_INIT").collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("WhitespaceAround", Stream.of(
                //  allowed via '4.8.3 Arrays'
                "ARRAY_INIT",
                //  '...' is almost same as '[]' by meaning
                "ELLIPSIS",
                // google prefers no spaces on one side or both for these tokens
                "GENERIC_START", "GENERIC_END", "WILDCARD_TYPE")
                .collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("IllegalTokenText", Stream.of(
                // all other java tokens and text are allowed
                "NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG", "IDENT",
                "COMMENT_CONTENT", "STRING_LITERAL", "CHAR_LITERAL")
                .collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("OperatorWrap", Stream.of(
                // specifically allowed via '4.5.1 Where to break' because the following are
                // assignment operators and they are allowed to break before or after the symbol
                "DIV_ASSIGN", "BOR_ASSIGN", "SL_ASSIGN", "ASSIGN", "BSR_ASSIGN", "BAND_ASSIGN",
                "PLUS_ASSIGN", "MINUS_ASSIGN", "SR_ASSIGN", "STAR_ASSIGN", "BXOR_ASSIGN",
                "MOD_ASSIGN",
                // state of the configuration when test was made until
                // https://github.com/checkstyle/checkstyle/issues/4122
                "COLON", "TYPE_EXTENSION_AND").collect(Collectors.toSet()));
        GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE.put("NoWhitespaceBefore", Stream.of(
                // google uses GenericWhitespace for this behavior
                "GENERIC_START", "GENERIC_END").collect(Collectors.toSet()));
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
            final Checker checker;
            if (module.equals(ImportControlCheck.class)) {
                // ImportControlCheck must have the import control configuration file to avoid
                // violation.
                moduleConfig.addAttribute("file", getPath(
                        "InputAllChecksImportControl.xml"));
            }
            checker = createChecker(moduleConfig);
            verify(checker, inputFilePath, expected);
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

                if (!isSubset(defaultTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's default tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
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

                if (!isSubset(requiredTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
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

                if (!isSubset(requiredTokens, defaultTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of default tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
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

            Assert.assertTrue(
                    "module '" + module.getSimpleName()
                            + "' must contain a multi-thread annotation",
                    module.isAnnotationPresent(GlobalStatefulCheck.class)
                            || module.isAnnotationPresent(FileStatefulCheck.class)
                            || module.isAnnotationPresent(StatelessCheck.class));
        }
    }

    @Test
    public void testAllModulesAreReferencedInConfigFile() throws Exception {
        final Set<String> modulesReferencedInConfig = CheckUtil.getConfigCheckStyleModules();
        final Set<String> moduleNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules());

        moduleNames.stream().filter(check -> !modulesReferencedInConfig.contains(check))
            .forEach(check -> {
                final String errorMessage = String.format(Locale.ROOT,
                    "%s is not referenced in checkstyle_checks.xml", check);
                Assert.fail(errorMessage);
            });
    }

    @Test
    public void testAllCheckTokensAreReferencedInCheckstyleConfigFile() throws Exception {
        final Configuration configuration = ConfigurationUtil
                .loadConfiguration("config/checkstyle_checks.xml");

        validateAllCheckTokensAreReferencedInConfigFile("checkstyle", configuration,
                CHECKSTYLE_TOKENS_IN_CONFIG_TO_IGNORE);
    }

    @Test
    public void testAllCheckTokensAreReferencedInGoogleConfigFile() throws Exception {
        final Configuration configuration = ConfigurationUtil
                .loadConfiguration("src/main/resources/google_checks.xml");

        validateAllCheckTokensAreReferencedInConfigFile("google", configuration,
                GOOGLE_TOKENS_IN_CONFIG_TO_IGNORE);
    }

    private static void validateAllCheckTokensAreReferencedInConfigFile(String configName,
            Configuration configuration, Map<String, Set<String>> tokensToIgnore) throws Exception {
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

            if (instance instanceof AbstractCheck) {
                final AbstractCheck check = (AbstractCheck) instance;

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
                    configTokens.addAll(Arrays.asList(checkConfig.getAttribute("tokens").trim()
                            .split(",\\s*")));
                }
                catch (CheckstyleException ex) {
                    // no tokens defined, so it is using default
                    configTokens.addAll(CheckUtil.getTokenNameSet(check.getDefaultTokens()));
                }
            }
        }

        for (Entry<String, Set<String>> entry : checkTokens.entrySet()) {
            Assert.assertEquals("'" + entry.getKey()
                    + "' should have all acceptable tokens from check in " + configName
                    + " config or specify an override to ignore the specific tokens",
                    entry.getValue(), configCheckTokens.get(entry.getKey()));
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

        checkstyleModulesNames.stream()
            .filter(moduleName -> !modulesNamesWhichHaveXdocs.contains(moduleName))
            .forEach(moduleName -> {
                final String missingModuleMessage = String.format(Locale.ROOT,
                    "Module %s does not have xdoc documentation.",
                    moduleName);
                Assert.fail(missingModuleMessage);
            });
    }

    @Test
    public void testAllCheckstyleModulesInCheckstyleConfig() throws Exception {
        final Set<String> configChecks = CheckUtil.getConfigCheckStyleModules();
        final Set<String> moduleNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleModules());

        for (String moduleName : moduleNames) {
            Assert.assertTrue("checkstyle_checks.xml is missing module: " + moduleName,
                    configChecks.contains(moduleName));
        }
    }

    @Test
    public void testAllCheckstyleChecksHaveMessage() throws Exception {
        for (Class<?> module : CheckUtil.getCheckstyleChecks()) {
            final String name = module.getSimpleName();

            Assert.assertFalse(name
                    + " should have at least one 'MSG_*' field for error messages", CheckUtil
                    .getCheckMessages(module).isEmpty());
        }
    }

    @Test
    public void testAllCheckstyleMessages() throws Exception {
        final Map<String, List<String>> usedMessages = new TreeMap<>();

        // test validity of messages from modules
        for (Class<?> module : CheckUtil.getCheckstyleModules()) {
            for (Field message : CheckUtil.getCheckMessages(module)) {
                Assert.assertEquals(module.getSimpleName() + "." + message.getName()
                        + " should be 'public static final'", Modifier.PUBLIC | Modifier.STATIC
                        | Modifier.FINAL, message.getModifiers());

                // below is required for package/private classes
                if (!message.isAccessible()) {
                    message.setAccessible(true);
                }

                verifyCheckstyleMessage(usedMessages, module, message);
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

                Assert.assertTrue("property '" + key + "' isn't used by any check in package '"
                        + entry.getKey() + "'", entry.getValue().contains(key.toString()));
            }
        }
    }

    private static void verifyCheckstyleMessage(Map<String, List<String>> usedMessages,
            Class<?> module, Field message) throws Exception {
        final String messageString = message.get(null).toString();
        final String packageName = module.getPackage().getName();
        List<String> packageMessages = usedMessages.get(packageName);

        if (packageMessages == null) {
            packageMessages = new ArrayList<>();
            usedMessages.put(packageName, packageMessages);
        }

        packageMessages.add(messageString);

        for (Locale locale : ALL_LOCALES) {
            String result = null;

            try {
                result = CheckUtil.getCheckMessage(module, locale, messageString);
            }
            catch (IllegalArgumentException ex) {
                Assert.fail(module.getSimpleName() + " with the message '" + messageString
                        + "' in locale '" + locale.getLanguage() + "' failed with: "
                        + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            }

            Assert.assertNotNull(
                    module.getSimpleName() + " should have text for the message '"
                            + messageString + "' in locale " + locale.getLanguage() + "'",
                    result);
            Assert.assertFalse(
                    module.getSimpleName() + " should have non-empty text for the message '"
                            + messageString + "' in locale '" + locale.getLanguage() + "'",
                    result.trim().isEmpty());
            Assert.assertFalse(
                    module.getSimpleName() + " should have non-TODO text for the message '"
                            + messageString + "' in locale " + locale.getLanguage() + "'",
                    !"todo.match".equals(messageString)
                            && result.trim().startsWith("TODO"));
        }
    }

    /**
     * Checks that an array is a subset of other array.
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
