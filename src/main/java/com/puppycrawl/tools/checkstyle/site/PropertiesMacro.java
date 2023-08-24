///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /** The string 'charset'. */
    private static final String CHARSET = "charset";

    /** A newline with 10 spaces of indentation. */
    private static final String INDENT_LEVEL_10 = SiteUtil.getNewlineAndIndentSpaces(10);
    /** A newline with 12 spaces of indentation. */
    private static final String INDENT_LEVEL_12 = SiteUtil.getNewlineAndIndentSpaces(12);
    /** A newline with 14 spaces of indentation. */
    private static final String INDENT_LEVEL_14 = SiteUtil.getNewlineAndIndentSpaces(14);

    /** Set of properties that every check has. */
    private static final Set<String> CHECK_PROPERTIES = getProperties(AbstractCheck.class);

    /** Set of properties that every Javadoc check has. */
    private static final Set<String> JAVADOC_CHECK_PROPERTIES =
            getProperties(AbstractJavadocCheck.class);

    /** Set of properties that every FileSet check has. */
    private static final Set<String> FILESET_PROPERTIES = getProperties(AbstractFileSetCheck.class);

    /** Set of properties that are undocumented. Those are internal properties. */
    private static final Set<String> UNDOCUMENTED_PROPERTIES = Set.of(
            "Checker.classLoader",
            "Checker.classloader",
            "Checker.moduleClassLoader",
            "Checker.moduleFactory",
            "TreeWalker.classLoader",
            "TreeWalker.moduleFactory",
            "TreeWalker.cacheFile",
            "TreeWalker.upChild",
            "SuppressWithNearbyCommentFilter.fileContents",
            "SuppressionCommentFilter.fileContents"
    );

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }

        final String modulePath = (String) request.getParameter("modulePath");
        final File moduleFile = new File(modulePath);
        final String moduleName = CommonUtil.getFileNameWithoutExtension(moduleFile.getName());

        processModule(moduleName, moduleFile);
        writePropertiesTable(sink, moduleName);
    }

    /**
     * Processes the given module with the ClassAndPropertiesJavadocScraper.
     *
     * @param moduleName the name of the module.
     * @param moduleFile the module file.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void processModule(String moduleName, File moduleFile)
            throws MacroExecutionException {
        ClassAndPropertiesJavadocScraper.clearModulePropertySetterJavadocs();
        ClassAndPropertiesJavadocScraper.setModuleName(moduleName);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(ClassAndPropertiesJavadocScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty(CHARSET, StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        try {
            checker.configure(defaultConfiguration);
            final List<File> filesToProcess = List.of(moduleFile);
            checker.process(filesToProcess);
            checker.destroy();
        }
        catch (CheckstyleException checkstyleException) {
            final String message = String.format(Locale.ROOT, "Failed processing %s", moduleName);
            throw new MacroExecutionException(message, checkstyleException);
        }
    }

    /**
     * Writes the properties table for the given module. Expects that the module has been processed
     * with the ClassAndPropertiesJavadocScraper before calling this method.
     *
     * @param sink the sink to write to.
     * @param moduleName the name of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertiesTable(Sink sink, String moduleName)
            throws MacroExecutionException {
        final DetailNode classJavadoc = ClassAndPropertiesJavadocScraper.getModuleJavadoc();
        final Map<String, DetailNode> settersJavadocs = ClassAndPropertiesJavadocScraper
                .getModulePropertySetterJavadocs();
        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();
        final Set<String> properties = getProperties(clss);
        fixCapturedProperties(instance, clss, properties);

        // TODO: It's good first to match the properties to the setters
        for (Map.Entry<String, DetailNode> entry : settersJavadocs.entrySet()) {
            final DetailNode javadoc = entry.getValue();

            final String descriptionString = getDescription(javadoc);
            System.out.println(descriptionString);

            final String sinceVersion = getSinceVersion(javadoc);
            System.out.println(sinceVersion);

            ////////////////////////////////////////////////////////////////////////////////////////
            // Get default value
            final String propertyName = Introspector.decapitalize(entry.getKey().substring(3));
            final Field field = getField(clss, propertyName);
            final Object value;
            try {
                value = field.get(instance);
            }
            catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            final Class<?> fieldClass = getFieldClass(field);
            String result = null;
            if (CHARSET.equals(propertyName)) {
                result = "the charset property of the parent Checker module";
            }
            else if ("PropertyCacheFile".equals(fieldClass.getSimpleName())) {
                result = "null (no cache file)";
            }
            else if (fieldClass == boolean.class) {
                result = value.toString();
            }
            else if (fieldClass == int.class) {
                result = value.toString();
            }
            else if (fieldClass == int[].class) {
                result = getIntArrayPropertyValue(value);
            }
            else if (fieldClass == double[].class) {
                result = Arrays.toString((double[]) value).replace("[", "").replace("]", "")
                        .replace(".0", "");
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (fieldClass == String[].class) {
                result = getStringArrayPropertyValue(propertyName, value);
            }
            else if (fieldClass == URI.class || fieldClass == String.class) {
                if (value != null) {
                    result = '"' + value.toString() + '"';
                }
            }
            else if (fieldClass == Pattern.class) {
                if (value != null) {
                    result = '"' + value.toString().replace("\n", "\\n").replace("\t", "\\t")
                            .replace("\r", "\\r").replace("\f", "\\f") + '"';
                }
            }
            else if (fieldClass == Pattern[].class) {
                result = getPatternArrayPropertyValue(value);
            }
            else if (fieldClass.isEnum()) {
                if (value != null) {
                    result = value.toString().toLowerCase(Locale.ENGLISH);
                }
            }
            else if (fieldClass == AccessModifierOption[].class) {
                result = Arrays.toString((Object[]) value).replace("[", "").replace("]", "");
            }

            if (result == null) {
                result = "null";
            }

            System.out.println(result);
            ////////////////////////////////////////////////////////////////////////////////////////
            // Get expected value
            final String expectedTypeName = Optional.ofNullable(field)
                .map(nonNullField -> nonNullField.getAnnotation(XdocsPropertyType.class))
                .map(propertyType -> propertyType.value().getDescription())
                .orElse(fieldClass.getSimpleName());
            System.out.println(expectedTypeName);
        }

        final XdocSink xdocSink = (XdocSink) sink;
        sink.table();
        xdocSink.setInsertNewline(false);
        sink.tableRows(null, false);
        sink.rawText(INDENT_LEVEL_12);
        writeTableHeader(sink);
        sink.rawText(INDENT_LEVEL_10);
        // TODO: Insert rows here
        sink.tableRows_();
        sink.table_();
        xdocSink.setInsertNewline(true);
    }

    /**
     * Extract the description of the setter from the Javadoc.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @return the description of the setter.
     */
    private static String getDescription(DetailNode javadoc) {
        final Stack<DetailNode> stack = new Stack<>();

        final List<DetailNode> descriptionNodes = getDescriptionNodes(javadoc);
        for (int childIndex = setterDescriptionChildren.size() - 1; childIndex >= 0; childIndex--) {
            stack.push(setterDescriptionChildren.get(childIndex));
        }
        // Perform DFS traversal on children of interest
        final StringBuilder description = new StringBuilder(128);
        while (!stack.isEmpty()) {
            final DetailNode node = stack.pop();
            final DetailNode[] nodeChildren = node.getChildren();
            for (int childIndex = nodeChildren.length - 1; childIndex >= 0; childIndex--) {
                stack.push(nodeChildren[childIndex]);
            }
            if (node.getType() == JavadocTokenTypes.TEXT) {
                description.append(node.getText());
            }
        }
        return description.toString().trim();
    }

    /**
     * Extracts description nodes from javadoc.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @return the description nodes of the setter.
     */
    private static List<DetailNode> getDescriptionNodes(DetailNode javadoc) {
        final DetailNode[] children = javadoc.getChildren();
        final List<DetailNode> descriptionNodes = new ArrayList<>();
        for (final DetailNode child : children) {
            if (isEndOfDescription(child)) {
                break;
            }
            descriptionNodes.add(child);
        }
        return descriptionNodes;
    }

    /**
     * Determines if the given child index is the end of the description. The end of the description
     * is defined as 4 consecutive nodes of type NEWLINE, LEADING_ASTERISK, NEWLINE,
     * LEADING_ASTERISK. This is an asterisk that is alone on a line. Just like the one below this
     * line.
     *
     * @param child the child to check.
     * @return true if the given child index is the end of the description.
     */
    private static boolean isEndOfDescription(DetailNode child) {
        return child.getType() == JavadocTokenTypes.NEWLINE
            && JavadocUtil.getNextSibling(child).getType() == JavadocTokenTypes.LEADING_ASTERISK
            && JavadocUtil.getNextSibling(child).getType() == JavadocTokenTypes.NEWLINE
            && JavadocUtil.getNextSibling(child).getType() == JavadocTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Extract the since version from the Javadoc.
     *
     * @param javadoc the Javadoc to extract the since version from.
     * @return the since version of the setter.
     */
    private static String getSinceVersion(DetailNode javadoc) {
        final DetailNode sinceJavadocTag = getSinceJavadocTag(javadoc);
        final DetailNode description = JavadocUtil.findFirstToken(sinceJavadocTag,
                JavadocTokenTypes.DESCRIPTION);
        final DetailNode text = JavadocUtil.findFirstToken(description, JavadocTokenTypes.TEXT);
        return text.getText();
    }

    /**
     * Find the since Javadoc tag node in the given Javadoc.
     *
     * @param javadoc the Javadoc to search.
     * @return the since Javadoc tag node or null if not found.
     */
    private static DetailNode getSinceJavadocTag(DetailNode javadoc) {
        final DetailNode[] children = javadoc.getChildren();
        DetailNode javadocTagWithSince = null;
        for (int childIndex = 0; childIndex < children.length; childIndex++) {
            final DetailNode child = children[childIndex];
            if (child.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                final DetailNode sinceNode = JavadocUtil.findFirstToken(
                        child, JavadocTokenTypes.SINCE_LITERAL);
                if (sinceNode != null) {
                    javadocTagWithSince = child;
                    break;
                }
            }
        }
        return javadocTagWithSince;
    }

    private static String getPatternArrayPropertyValue(Object fieldValue) {
        Object value = fieldValue;
        String result;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            final Pattern[] newArray = new Pattern[collection.size()];
            final Iterator<?> iterator = collection.iterator();
            int index = 0;

            while (iterator.hasNext()) {
                final Object next = iterator.next();
                newArray[index] = (Pattern) next;
                index++;
            }

            value = newArray;
        }

        if (value != null && Array.getLength(value) > 0) {
            final String[] newArray = new String[Array.getLength(value)];

            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = ((Pattern) Array.get(value, i)).pattern();
            }

            result = Arrays.toString(newArray).replace("[", "").replace("]", "");
        }
        else {
            result = "";
        }

        if (result.isEmpty()) {
            result = "{}";
        }
        return result;
    }

    private static String getStringArrayPropertyValue(String propertyName, Object value) {
        String result;
        if (value == null) {
            result = "";
        }
        else {
            final Stream<?> valuesStream;
            if (value instanceof Collection) {
                final Collection<?> collection = (Collection<?>) value;
                valuesStream = collection.stream();
            }
            else {
                final Object[] array = (Object[]) value;
                valuesStream = Arrays.stream(array);
            }
            result = valuesStream
                .map(String.class::cast)
                .sorted()
                .collect(Collectors.joining(", "));
        }

        if (result.isEmpty()) {
            if ("fileExtensions".equals(propertyName)) {
                result = "all files";
            }
            else {
                result = "{}";
            }
        }
        return result;
    }

    private static String getIntArrayPropertyValue(Object value) {
        final IntStream stream;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            stream = collection.stream()
                    .mapToInt(number -> (int) number);
        }
        else if (value instanceof BitSet) {
            stream = ((BitSet) value).stream();
        }
        else {
            stream = Arrays.stream((int[]) value);
        }
        String result = stream
                .mapToObj(TokenUtil::getTokenName)
                .sorted()
                .collect(Collectors.joining(", "));
        if (result.isEmpty()) {
            result = "{}";
        }
        return result;
    }

    private static Class<?> getFieldClass(Field field) {
        Class<?> result = null;

        if (field != null) {
            result = field.getType();
        }
        if (result == List.class || result == Set.class) {
            final ParameterizedType type = (ParameterizedType) field.getGenericType();
            final Class<?> parameterClass = (Class<?>) type.getActualTypeArguments()[0];

            if (parameterClass == Integer.class) {
                result = int[].class;
            }
            else if (parameterClass == String.class) {
                result = String[].class;
            }
            else if (parameterClass == Pattern.class) {
                result = Pattern[].class;
            }
        }
        else if (result == BitSet.class) {
            result = int[].class;
        }

        return result;
    }

    private static Field getField(Class<?> fieldClass, String propertyName) {
        Field result = null;
        Class<?> currentClass = fieldClass;

        while (!Object.class.equals(currentClass)) {
            try {
                result = currentClass.getDeclaredField(propertyName);
                result.trySetAccessible();
                break;
            }
            catch (NoSuchFieldException ignored) {
                currentClass = currentClass.getSuperclass();
            }
        }

        return result;
    }

    private static Set<String> getProperties(Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        final PropertyDescriptor[] map = PropertyUtils.getPropertyDescriptors(clss);

        for (PropertyDescriptor propertyDescriptor : map) {
            if (propertyDescriptor.getWriteMethod() != null) {
                result.add(propertyDescriptor.getName());
            }
        }

        return result;
    }

    private static void fixCapturedProperties(
            Object instance, Class<?> clss, Set<String> properties) {
        // remove global properties that don't need documentation
        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(JAVADOC_CHECK_PROPERTIES);

            // override
            properties.add("violateExecutionOnNonTightHtml");
        }
        else if (AbstractCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(CHECK_PROPERTIES);
        }
        if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(FILESET_PROPERTIES);

            // override
            properties.add("fileExtensions");
        }

        // remove undocumented properties
        new HashSet<>(properties).stream()
            .filter(prop -> UNDOCUMENTED_PROPERTIES.contains(clss.getSimpleName() + "." + prop))
            .forEach(properties::remove);

        if (AbstractCheck.class.isAssignableFrom(clss)) {
            final AbstractCheck check = (AbstractCheck) instance;

            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final int[] defaultTokens = check.getDefaultTokens();
            Arrays.sort(defaultTokens);
            final int[] requiredTokens = check.getRequiredTokens();
            Arrays.sort(requiredTokens);

            if (!Arrays.equals(acceptableTokens, defaultTokens)
                    || !Arrays.equals(acceptableTokens, requiredTokens)) {
                properties.add("tokens");
            }
        }

        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;

            final int[] acceptableJavadocTokens = check.getAcceptableJavadocTokens();
            Arrays.sort(acceptableJavadocTokens);
            final int[] defaultJavadocTokens = check.getDefaultJavadocTokens();
            Arrays.sort(defaultJavadocTokens);
            final int[] requiredJavadocTokens = check.getRequiredJavadocTokens();
            Arrays.sort(requiredJavadocTokens);

            if (!Arrays.equals(acceptableJavadocTokens, defaultJavadocTokens)
                    || !Arrays.equals(acceptableJavadocTokens, requiredJavadocTokens)) {
                properties.add("javadocTokens");
            }
        }
    }

    private String getSinceVersion(DetailAST blockCommentAst) {
        // TODO: JavadocUtil::getJavadocTags is probably better here but I haven't figured out how
        // to get a TextBlock yet.
        final String text = ((DetailAstImpl) blockCommentAst).getFirstChild().getText();
        final Pattern sinceVersionPattern = Pattern.compile("@since ((\\d+\\.)+\\d+)\n");
        final Matcher matcher = sinceVersionPattern.matcher(text);
        String sinceVersion = "undefined";
        if (matcher.find()) {
            sinceVersion = matcher.group(1);
        }
        return sinceVersion;
    }

    /**
     * Writes the table header with the 5 columns - name, description, type, default value, since.
     *
     * @param sink sink to write to.
     */
    private static void writeTableHeader(Sink sink) {
        sink.tableRow();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("name");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("description");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("type");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("default value");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("since");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a row of the table with the given property details - name, description, type, default
     * value, since.
     *
     * @param sink sink to write to.
     * @param property property details to write.
     */
    private static void writePropertyRow(Sink sink, ModulePropertyDetails property) {
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getName());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getDescription());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getType());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getDefaultValue());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getSince());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }
}
