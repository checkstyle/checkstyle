/**
 * Measurement filters for the {@code checks.metrics} slice. Each class implements
 * {@code Filter<AstEvent, Measurement>}. Filters here MUST NOT extend
 * {@code AbstractCheck}/{@code AbstractFileSetCheck} and MUST NOT call
 * {@code AbstractCheck.log(..)}. Allowed Checkstyle utility/AST dependencies are
 * restricted to the architecture allow-list ({@code DetailAST}, {@code TokenTypes},
 * {@code FullIdent}, {@code ScopeUtil}, {@code CommonUtil}, {@code CheckUtil},
 * {@code AnnotationUtil}).
 */
package com.puppycrawl.tools.checkstyle.checks.metrics.pipeline;
