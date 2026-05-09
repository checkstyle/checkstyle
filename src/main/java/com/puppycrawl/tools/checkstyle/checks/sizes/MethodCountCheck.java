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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Pipeline;
import com.puppycrawl.tools.checkstyle.checks.pipeline.PipelineBuilder;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.TokenFilter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.ViolationSink;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.sizes.pipeline.MethodCountMeasurementFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the number of methods declared in each type declaration by access modifier
 * or total count.
 * </div>
 *
 * <p>
 * This check can be configured to flag classes that define too many methods
 * to prevent the class from getting too complex. Counting can be customized
 * to prevent too many total methods in a type definition ({@code maxTotal}),
 * or to prevent too many methods of a specific access modifier ({@code private},
 * {@code package}, {@code protected} or {@code public}). Each count is completely
 * separated to customize how many methods of each you want to allow. For example,
 * specifying a {@code maxTotal} of 10, still means you can prevent more than 0
 * {@code maxPackage} methods. A violation won't appear for 8 public methods,
 * but one will appear if there is also 3 private methods or any package-private methods.
 * </p>
 *
 * <p>
 * Methods defined in anonymous classes are not counted towards any totals.
 * Counts only go towards the main type declaration parent, and are kept separate
 * from it's children's inner types.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * public class ExampleClass {
 *   public enum Colors {
 *     RED, GREEN, YELLOW;
 *
 *     public String getRGB() { ... } // NOT counted towards ExampleClass
 *   }
 *
 *   public void example() { // counted towards ExampleClass
 *     Runnable r = (new Runnable() {
 *       public void run() { ... } // NOT counted towards ExampleClass, won't produce any violations
 *     });
 *   }
 *
 *   public static class InnerExampleClass {
 *     protected void example2() { ... } // NOT counted towards ExampleClass,
 *                                    // but counted towards InnerExampleClass
 *   }
 * }
 * </code></pre></div>
 *
 * @since 5.3
 */
@FileStatefulCheck
public final class MethodCountCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRIVATE_METHODS = "too.many.privateMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PACKAGE_METHODS = "too.many.packageMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PROTECTED_METHODS = "too.many.protectedMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PUBLIC_METHODS = "too.many.publicMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MANY_METHODS = "too.many.methods";

    /** Default maximum number of methods. */
    private static final int DEFAULT_MAX_METHODS = 100;

    /** Specify the maximum number of {@code private} methods allowed. */
    private int maxPrivate = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code package} methods allowed. */
    private int maxPackage = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code protected} methods allowed. */
    private int maxProtected = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code public} methods allowed. */
    private int maxPublic = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of methods allowed at all scope levels. */
    private int maxTotal = DEFAULT_MAX_METHODS;

    private Pipeline<AstEvent, ViolationMessage> pipeline;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        pipeline = PipelineBuilder.<AstEvent>start()
                .add(new TokenFilter(
                        TokenTypes.CLASS_DEF,
                        TokenTypes.ENUM_CONSTANT_DEF,
                        TokenTypes.ENUM_DEF,
                        TokenTypes.INTERFACE_DEF,
                        TokenTypes.ANNOTATION_DEF,
                        TokenTypes.METHOD_DEF,
                        TokenTypes.RECORD_DEF))
                .addQueued(new MethodCountMeasurementFilter(maxPrivate, maxPackage, maxProtected,
                        maxPublic, maxTotal, MSG_PRIVATE_METHODS, MSG_PACKAGE_METHODS,
                        MSG_PROTECTED_METHODS, MSG_PUBLIC_METHODS, MSG_MANY_METHODS))
                .addQueued(new ViolationSink())
                .build();
    }

    @Override
    public void visitToken(DetailAST ast) {
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.VISIT));
        drainAndLog();
    }

    @Override
    public void leaveToken(DetailAST ast) {
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.LEAVE));
        drainAndLog();
    }

    private void drainAndLog() {
        while (pipeline.hasResults()) {
            final ViolationMessage v = pipeline.drain();
            log(v.getLine(), v.getCol(), v.getMessageKey(), v.getArgs());
        }
    }

    /**
     * Setter to specify the maximum number of {@code private} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPrivate(int value) {
        maxPrivate = value;
    }

    /**
     * Setter to specify the maximum number of {@code package} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPackage(int value) {
        maxPackage = value;
    }

    /**
     * Setter to specify the maximum number of {@code protected} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxProtected(int value) {
        maxProtected = value;
    }

    /**
     * Setter to specify the maximum number of {@code public} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPublic(int value) {
        maxPublic = value;
    }

    /**
     * Setter to specify the maximum number of methods allowed at all scope levels.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxTotal(int value) {
        maxTotal = value;
    }

}
