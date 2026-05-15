///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Check that a private field is declared, but never used. Fields with any other
 * visibility (package-private, protected, or public), including those declared in
 * an implicitly declared class (compact source file), are not checked.
 * </div>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class UnusedPrivateFieldCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_PRIVATE_FIELD = "unused.private.field";

    /**
     * Stack of private field maps, one per class nesting level.
     */
    private final Deque<Map<String, DetailAST>> privateFields = new ArrayDeque<>();

    /**
     * Stack of currently open enclosing type names.
     */
    private final Deque<String> enclosingTypeNames = new ArrayDeque<>();

    /**
     * Recorded field-name usage occurrences.
     */
    private final List<FieldUsage> fieldUsages = new ArrayList<>();

    /**
     * Global set of field names accessed.
     */
    private final Set<String> globalUsedFields = new HashSet<>();

    /**
     * Accumulated pending fields, reported at finishTree.
     */
    private final List<PendingField> pendingFields = new ArrayList<>();

    /**
     * Scope stack tracking local variable and parameter names per block.
     */
    private final Deque<Map<String, String>> scopeStack = new ArrayDeque<>();

    /**
     * Snapshots of scope stack saved when entering a nested class,
     * restored when leaving it.
     */
    private final Deque<Deque<Map<String, String>>> scopeStackSnapshots = new ArrayDeque<>();

    /**
     * Recorded field-name accesses on a qualifier.
     */
    private final List<TypedUsage> typedUsages = new ArrayList<>();

    /**
     * Each class's private fields, keyed by simple type name, populated once
     * a class's OBJBLOCK closes. Used to resolve {@link #typedUsages}.
     */
    private final Map<String, Map<String, DetailAST>> privateFieldsByType = new HashMap<>();

    /**
     * Specify annotations canonical names which ignore variables in consideration.
     * A field is ignored either when the field itself carries a matching annotation,
     * or when its enclosing class, interface, enum, or record carries one (e.g. a
     * class-level Lombok {@code @Getter}).
     */
    private Set<String> ignoreAnnotationCanonicalNames = new HashSet<>(Set.of("java.io.Serial"));

    /**
     * Specify a regular expression pattern for field names to ignore.
     */
    private Pattern ignoredFieldPattern = Pattern.compile("serialVersionUID");

    /**
     * Set of ignore annotations short names.
     */
    private Set<String> ignoreAnnotationShortNames = new HashSet<>();

    /**
     * Creates a new {@code UnusedPrivateFieldCheck} instance with default values.
     *
     */
    public UnusedPrivateFieldCheck() {
        // default constructor
    }

    /**
     * Setter to specify annotations canonical names which ignore variables in consideration.
     *
     * @param annotationNames array of ignore annotations canonical names.
     * @since 14.1.0
     */
    public void setIgnoreAnnotationCanonicalNames(String... annotationNames) {
        ignoreAnnotationCanonicalNames = Set.of(annotationNames);
    }

    /**
     * Setter to specify a regular expression pattern for field names to ignore, even
     * if they otherwise satisfy this check's detection of an unused private field.
     * Note this replaces the default value entirely — to keep {@code serialVersionUID}
     * ignored alongside your own pattern, include it explicitly, e.g.
     * {@code ^(serialVersionUID|LOG|LOGGER)$}.
     *
     * @param pattern regular expression pattern for field names to ignore.
     * @since 14.1.0
     */
    public void setIgnoredFieldPattern(Pattern pattern) {
        ignoredFieldPattern = pattern;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.PARAMETERS,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        privateFields.clear();
        enclosingTypeNames.clear();
        fieldUsages.clear();
        globalUsedFields.clear();
        pendingFields.clear();
        scopeStack.clear();
        scopeStackSnapshots.clear();
        typedUsages.clear();
        privateFieldsByType.clear();
        ignoreAnnotationShortNames = ignoreAnnotationCanonicalNames.stream()
                .map(CommonUtil::baseClassName)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK -> {
                privateFields.push(new HashMap<>());
                scopeStackSnapshots.push(new ArrayDeque<>(scopeStack));
                scopeStack.clear();
                final DetailAST typeDef = ast.getParent();
                final DetailAST nameIdent = typeDef.findFirstToken(TokenTypes.IDENT);
                if (nameIdent == null) {
                    enclosingTypeNames.push("");
                }
                else {
                    enclosingTypeNames.push(nameIdent.getText());
                }
            }
            case TokenTypes.PARAMETERS, TokenTypes.SLIST, TokenTypes.LITERAL_FOR,
                 TokenTypes.LITERAL_CATCH -> scopeStack.push(new HashMap<>());
            case TokenTypes.PARAMETER_DEF -> {
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                if (ident != null) {
                    scopeStack.peek().put(ident.getText(), resolveDeclaredTypeName(ast));
                }
            }
            case TokenTypes.VARIABLE_DEF -> handleVariableDef(ast);
            case TokenTypes.IDENT -> handleIdent(ast);
            default -> {
                // no action needed for other token types
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK -> {
                final Map<String, DetailAST> classFields = privateFields.pop();
                privateFieldsByType.put(enclosingTypeNames.peek(), classFields);
                for (final Map.Entry<String, DetailAST> entry : classFields.entrySet()) {
                    pendingFields.add(new PendingField(entry));
                }
                final Deque<Map<String, String>> snapshot = scopeStackSnapshots.pop();
                snapshot.forEach(scopeStack::push);
                enclosingTypeNames.pop();
            }
            case TokenTypes.LAMBDA -> {
                if (ast.findFirstToken(TokenTypes.PARAMETERS) != null) {
                    scopeStack.pop();
                }
            }
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                 TokenTypes.SLIST, TokenTypes.LITERAL_FOR,
                 TokenTypes.LITERAL_CATCH -> scopeStack.pop();
            default -> {
                // no action needed for other token types
            }
        }
    }

    @Override
    public void finishTree(final DetailAST rootAST) {
        final Set<DetailAST> usedFieldIdents = new HashSet<>();
        fieldUsages.stream()
                .map(UnusedPrivateFieldCheck::resolveUsage)
                .forEach(usedFieldIdents::add);
        for (final TypedUsage typedUsage : typedUsages) {
            final Map<String, DetailAST> fields = privateFieldsByType.get(typedUsage.typeName());
            if (fields != null) {
                final DetailAST ident = fields.get(typedUsage.fieldName());
                usedFieldIdents.add(ident);

            }
        }
        for (final PendingField pending : pendingFields) {
            final Map.Entry<String, DetailAST> entry = pending.entry();
            final DetailAST ident = entry.getValue();
            final String name = entry.getKey();
            if (!usedFieldIdents.contains(ident) && !globalUsedFields.contains(name)) {
                log(ident, MSG_PRIVATE_FIELD, name);
            }
        }
    }

    /**
     * Resolves a recorded usage to the exact field declaration it refers to,
     * following the same shadowing rules Java itself applies.
     *
     * @param usage the recorded usage to resolve.
     * @return the DetailAST of the field it resolves to, or null if none.
     */
    private static DetailAST resolveUsage(FieldUsage usage) {
        DetailAST result = null;
        if (usage.qualifierTypeName() != null) {
            final int index = usage.ancestorTypeNames().indexOf(usage.qualifierTypeName());
            if (index != -1) {
                result = usage.ancestorFieldMaps().get(index).get(usage.name());
            }
        }
        else {
            for (final Map<String, DetailAST> level : usage.ancestorFieldMaps()) {
                result = level.get(usage.name());
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Collects private field declarations.
     *
     * @param ast for this method.
     */
    private void handleVariableDef(DetailAST ast) {
        final DetailAST parent = ast.getParent();

        if (parent.getType() == TokenTypes.OBJBLOCK) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean isPrivateField = isPrivate(modifiers);
            final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
            final boolean isIgnoredName =
                    ignoredFieldPattern.matcher(ident.getText()).matches();
            final boolean isIgnored = isIgnoredName || hasIgnoredAnnotation(ast);
            if (isPrivateField && !isIgnored) {
                privateFields.peek().put(ident.getText(), ident);
            }
        }
        else if (!scopeStack.isEmpty()) {
            final String localName =
                    ast.findFirstToken(TokenTypes.IDENT).getText();
            scopeStack.peek().put(localName, resolveDeclaredTypeName(ast));
        }
    }

    /**
     * Checks whether a field should be ignored because either the field itself or its
     * enclosing type (class, interface, enum, or record) carries an annotation present
     * in {@link #ignoreAnnotationCanonicalNames} (matched by canonical or short name).
     *
     * @param variableDef the VARIABLE_DEF node.
     * @return true if the field or its enclosing type has a matching ignore annotation.
     */
    private boolean hasIgnoredAnnotation(final DetailAST variableDef) {
        boolean result = isAnnotatedWithIgnoredAnnotation(variableDef);
        if (!result) {
            final DetailAST classDef = variableDef.getParent().getParent();
            result = isAnnotatedWithIgnoredAnnotation(classDef);
        }
        return result;
    }

    /**
     * Checks whether the given AST node (a field or a type definition) carries an
     * annotation present in {@link #ignoreAnnotationCanonicalNames}, matched either
     * by canonical name or by short name.
     *
     * @param ast the VARIABLE_DEF, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, or
     *            ANNOTATION_DEF node to inspect.
     * @return true if a matching ignore annotation is present directly on {@code ast}.
     */
    private boolean isAnnotatedWithIgnoredAnnotation(final DetailAST ast) {
        boolean result = false;
        final DetailAST holder = AnnotationUtil.getAnnotationHolder(ast);
        if (holder != null) {
            DetailAST child = holder.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.ANNOTATION) {
                    final String name =
                            FullIdent.createFullIdent(
                                    child.getFirstChild().getNextSibling()).getText();
                    if (ignoreAnnotationCanonicalNames.contains(name)
                            || ignoreAnnotationShortNames.contains(name)) {
                        result = true;
                        break;
                    }
                }
                child = child.getNextSibling();
            }
        }
        return result;
    }

    /**
     * Records field usage, respecting local variable and parameter shadowing.
     * Resolution to a specific field declaration happens later, at
     * {@link #finishTree}.
     *
     * @param ast for handleIdent
     */
    private void handleIdent(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        if (!isDeclarationParent(parent)) {
            final String name = ast.getText();
            final boolean shadowed =
                    scopeStack.stream().anyMatch(scope -> scope.containsKey(name));
            if (parent.getType() == TokenTypes.DOT) {
                handleDotAccess(parent, name);
            }
            else if (!shadowed) {
                recordUsage(name, null, false);
            }
        }
    }

    /**
     * Classifies a dot-qualified reference: {@code this.field} and
     * {@code ClassName.this.field} are resolved precisely; any other qualifier
     * (an arbitrary object or type reference) cannot be resolved without type
     * information, so it falls back to name-only matching via
     * {@link #globalUsedFields}.
     *
     * @param dot  the DOT node whose last child is the field IDENT.
     * @param name the field name being accessed.
     */
    private void handleDotAccess(DetailAST dot, String name) {
        final DetailAST qualifier = dot.getFirstChild();
        if (qualifier.getType() == TokenTypes.LITERAL_THIS) {
            recordUsage(name, null, true);
        }
        else if (qualifier.getType() == TokenTypes.DOT
                && qualifier.getLastChild().getType() == TokenTypes.LITERAL_THIS) {
            final String qualifiedName =
                    FullIdent.createFullIdent(qualifier.getFirstChild()).getText();
            final String simpleName =
                    qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
            recordUsage(name, simpleName, false);
        }
        else if (findDeclaredType(qualifier.getText()) != null) {
            typedUsages.add(new TypedUsage(findDeclaredType(qualifier.getText()), name));
        }
        else {
            globalUsedFields.add(name);
        }
    }

    /**
     * Looks up the declared simple type name of a local variable or parameter
     * currently in scope.
     *
     * @param name the variable or parameter name.
     * @return its declared simple type name, or null if the name is not a
     *         tracked local/parameter, or its type could not be determined.
     */
    private String findDeclaredType(String name) {
        String result = null;
        for (final Map<String, String> scope : scopeStack) {
            final String type = scope.get(name);
            if (type != null) {
                result = type;
                break;
            }
        }
        return result;
    }

    /**
     * Extracts the declared simple type name of a VARIABLE_DEF or
     * PARAMETER_DEF, if determinable.
     *
     * @param varOrParamDef the VARIABLE_DEF or PARAMETER_DEF node.
     * @return the simple type name, or null if it could not be determined
     *         (e.g. primitive types, inferred/generic-only forms).
     */
    private static String resolveDeclaredTypeName(DetailAST varOrParamDef) {
        final DetailAST typeAst = varOrParamDef.findFirstToken(TokenTypes.TYPE);
        String result = null;
        final DetailAST identChild = typeAst.findFirstToken(TokenTypes.IDENT);
        if (identChild != null) {
            result = identChild.getText();
        }
        return result;
    }

    /**
     * Snapshots the currently open class chain (field maps and type names) so the
     * usage can be resolved later, once every class in the file is fully populated.
     *
     * @param name              the field name referenced.
     * @param qualifierTypeName the enclosing type name named in a
     *                          {@code ClassName.this.field} reference, or null.
     * @param bareThisQualified true for a {@code this.field} reference.
     */
    private void recordUsage(String name, String qualifierTypeName, boolean bareThisQualified) {
        fieldUsages.add(new FieldUsage(name,
                new ArrayList<>(privateFields),
                new ArrayList<>(enclosingTypeNames),
                qualifierTypeName,
                bareThisQualified));
    }

    /**
     * Checks whether the given parent node is a declaration site whose IDENT child
     * names the declared element itself (a variable, method, constructor, or type),
     * rather than referencing some other field.
     *
     * @param parent the parent of the IDENT being inspected.
     * @return true if the IDENT is a declaration name, not a usage.
     */
    private static boolean isDeclarationParent(DetailAST parent) {
        final int type = parent.getType();
        return type == TokenTypes.VARIABLE_DEF
                || type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.RECORD_DEF
                || type == TokenTypes.ANNOTATION_DEF;
    }

    /**
     * Checks whether a field is private.
     *
     * @param modifiers for isPrivate method.
     * @return modifiers of literal_private.
     */
    private static boolean isPrivate(final DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

    /**
     * Holds a private field entry, reported if never resolved to by any usage.
     *
     * @param entry The field name and its AST node.
     */
    private record PendingField(Map.Entry<String, DetailAST> entry) {
    }

    /**
     * A recorded field-name access on a qualifier whose declared type is
     * known precisely.
     *
     * @param typeName  the qualifier's declared simple type name.
     * @param fieldName the field name accessed on it.
     */
    private record TypedUsage(String typeName, String fieldName) {
    }

    /**
     * A recorded, not-yet-resolved reference to a field name, with enough context
     * to resolve it precisely once the whole file has been visited.
     *
     * @param name                the field name referenced.
     * @param ancestorFieldMaps   the currently open field maps at the time of the
     *                            reference, innermost first (index 0).
     * @param ancestorTypeNames   the currently open type names, parallel to
     *                            {@code ancestorFieldMaps}.
     * @param qualifierTypeName   the type name in a {@code ClassName.this.field}
     *                            reference, or null if not that form.
     * @param bareThisQualified   true for a {@code this.field} reference.
     */
    private record FieldUsage(String name, List<Map<String, DetailAST>> ancestorFieldMaps,
                              List<String> ancestorTypeNames, String qualifierTypeName,
                              boolean bareThisQualified) {
    }

}
