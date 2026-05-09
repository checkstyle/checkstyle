package com.puppycrawl.tools.checkstyle.checks.metrics.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Filter that tracks class coupling.
 * Extracted from {@code AbstractClassCouplingCheck}.
 */
public final class CouplingMeasurementFilter implements Filter<AstEvent, Measurement> {

    private static final char DOT = '.';
    private static final Pattern BRACKET_PATTERN = Pattern.compile("\\[[^]]*]");

    private final String logMessageId;
    private final int max;
    private final Set<String> excludedClasses;
    private final Set<String> excludedPackages;
    private final List<Pattern> excludeClassesRegexps;

    private final Map<String, String> importedClassPackages = new HashMap<>();
    private final Deque<ClassContext> classesContexts = new ArrayDeque<>();
    private String packageName = "";

    public CouplingMeasurementFilter(String logMessageId, int max,
                                     Set<String> excludedClasses,
                                     Set<String> excludedPackages,
                                     List<Pattern> excludeClassesRegexps) {
        this.logMessageId = logMessageId;
        this.max = max;
        this.excludedClasses = excludedClasses;
        this.excludedPackages = excludedPackages;
        this.excludeClassesRegexps = excludeClassesRegexps;
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<Measurement> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }

            final DetailAST ast = event.getNode();

            if (event.getPhase() == AstEvent.Phase.BEGIN_TREE) {
                importedClassPackages.clear();
                classesContexts.clear();
                classesContexts.push(new ClassContext("", null));
                packageName = "";
            }
            else if (event.getPhase() == AstEvent.Phase.VISIT) {
                switch (ast.getType()) {
                    case TokenTypes.PACKAGE_DEF -> visitPackageDef(ast);
                    case TokenTypes.IMPORT -> registerImport(ast);
                    case TokenTypes.CLASS_DEF,
                         TokenTypes.INTERFACE_DEF,
                         TokenTypes.ANNOTATION_DEF,
                         TokenTypes.ENUM_DEF,
                         TokenTypes.RECORD_DEF -> visitClassDef(ast);
                    case TokenTypes.EXTENDS_CLAUSE,
                         TokenTypes.IMPLEMENTS_CLAUSE,
                         TokenTypes.TYPE -> visitType(ast);
                    case TokenTypes.LITERAL_NEW -> visitLiteralNew(ast);
                    case TokenTypes.LITERAL_THROWS -> visitLiteralThrows(ast);
                    case TokenTypes.ANNOTATION -> visitAnnotationType(ast);
                    default -> { }
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                if (TokenUtil.isTypeDeclaration(ast.getType())) {
                    leaveClassDef(out);
                }
            }
        }
    }

    private void visitPackageDef(DetailAST pkg) {
        final FullIdent ident = FullIdent.createFullIdent(pkg.getLastChild().getPreviousSibling());
        packageName = ident.getText();
    }

    private void visitClassDef(DetailAST classDef) {
        final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
        classesContexts.push(new ClassContext(className, classDef));
    }

    private void leaveClassDef(Pipe<Measurement> out) {
        final ClassContext context = classesContexts.pop();
        context.referencedClassNames.remove(context.className);
        context.referencedClassNames.remove(packageName + DOT + context.className);

        out.write(new Measurement(context.classAst, context.classAst.getLineNo(), context.classAst.getColumnNo(),
                context.referencedClassNames.size(), logMessageId,
                context.referencedClassNames.size(), max, context.referencedClassNames.toString()));
    }

    private void registerImport(DetailAST imp) {
        final FullIdent ident = FullIdent.createFullIdent(imp.getLastChild().getPreviousSibling());
        final String fullName = ident.getText();
        final int lastDot = fullName.lastIndexOf(DOT);
        importedClassPackages.put(fullName.substring(lastDot + 1), fullName);
    }

    private void visitType(DetailAST ast) {
        classesContexts.peek().visitType(ast);
    }

    private void visitLiteralNew(DetailAST ast) {
        classesContexts.peek().visitLiteralNew(ast);
    }

    private void visitLiteralThrows(DetailAST ast) {
        classesContexts.peek().visitLiteralThrows(ast);
    }

    private void visitAnnotationType(DetailAST annotationAST) {
        final DetailAST children = annotationAST.getFirstChild();
        final DetailAST type = children.getNextSibling();
        classesContexts.peek().addReferencedClassName(type.getText());
    }

    private final class ClassContext {
        private final Set<String> referencedClassNames = new TreeSet<>();
        private final String className;
        private final DetailAST classAst;

        private ClassContext(String className, DetailAST ast) {
            this.className = className;
            classAst = ast;
        }

        void visitLiteralThrows(DetailAST literalThrows) {
            for (DetailAST childAST = literalThrows.getFirstChild();
                 childAST != null;
                 childAST = childAST.getNextSibling()) {
                if (childAST.getType() != TokenTypes.COMMA) {
                    addReferencedClassName(childAST);
                }
            }
        }

        void visitType(DetailAST ast) {
            DetailAST child = ast.getFirstChild();
            while (child != null) {
                if (TokenUtil.isOfType(child, TokenTypes.IDENT, TokenTypes.DOT)) {
                    final String fullTypeName = FullIdent.createFullIdent(child).getText();
                    final String trimmed = BRACKET_PATTERN.matcher(fullTypeName).replaceAll("");
                    addReferencedClassName(trimmed);
                }
                child = child.getNextSibling();
            }
        }

        void visitLiteralNew(DetailAST ast) {
            if (ast.getParent().getType() == TokenTypes.METHOD_REF) {
                addReferencedClassName(ast.getParent().getFirstChild());
            }
            else {
                addReferencedClassName(ast);
            }
        }

        private void addReferencedClassName(DetailAST ast) {
            final String fullIdentName = FullIdent.createFullIdent(ast).getText();
            final String trimmed = BRACKET_PATTERN.matcher(fullIdentName).replaceAll("");
            addReferencedClassName(trimmed);
        }

        void addReferencedClassName(String referencedClassName) {
            if (isSignificant(referencedClassName)) {
                referencedClassNames.add(referencedClassName);
            }
        }

        private boolean isSignificant(String candidateClassName) {
            return !excludedClasses.contains(candidateClassName)
                && !isFromExcludedPackage(candidateClassName)
                && !isExcludedClassRegexp(candidateClassName);
        }

        private boolean isFromExcludedPackage(String candidateClassName) {
            String classNameWithPackage = candidateClassName;
            if (candidateClassName.indexOf(DOT) == -1) {
                classNameWithPackage = getClassNameWithPackage(candidateClassName).orElse("");
            }
            boolean isFromExcludedPackage = false;
            if (classNameWithPackage.indexOf(DOT) != -1) {
                final int lastDotIndex = classNameWithPackage.lastIndexOf(DOT);
                final String candidatePackageName = classNameWithPackage.substring(0, lastDotIndex);
                isFromExcludedPackage = candidatePackageName.startsWith("java.lang")
                    || excludedPackages.contains(candidatePackageName);
            }
            return isFromExcludedPackage;
        }

        private Optional<String> getClassNameWithPackage(String examineClassName) {
            return Optional.ofNullable(importedClassPackages.get(examineClassName));
        }

        private boolean isExcludedClassRegexp(String candidateClassName) {
            boolean result = false;
            for (Pattern pattern : excludeClassesRegexps) {
                if (pattern.matcher(candidateClassName).matches()) {
                    result = true;
                    break;
                }
            }
            return result;
        }
    }
}
