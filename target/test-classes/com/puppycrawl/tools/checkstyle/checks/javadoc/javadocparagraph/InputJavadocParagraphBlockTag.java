/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphBlockTag {
    /**
     * Test method.
     *
     * @return some result
     *
     * <p>Indide Block Tag</p>
     */
    public void method() {
    }

    /**
     * Test method.
     *
     * @return some result
     *           <p>Indide Block Tag</p>
     */
    public void method2() {
    }

    /**
     * Test method.
     *
     * @return some result <p>Indide Block Tag</p>
     */
    public void method3() {
    }

    /**
     * Test method.
     *
     * @deprecated All instance methods have
     *       their equivalent on the result of {@code Traverser.forTree(tree)} where {@code tree}
     *       implements {@code SuccessorsFunction}, which has a similar API as {@link #children}
     *       or can be the same lambda function as passed.
     *       <p>This class is scheduled to be removed in October 2019.
     */
    public void method4() {
    }

    /**
     * Test method.
     *
     * @param ast
     *            Root node of subtree to traverse.
     *            <p>
     *            Note that the AST passed in is not visited itself. Visitation
     *            starts with its children.
     *            </p>
     */
    public void method5() {
    }

    /**
     * Test method.
     *
     * @implNote This is for test purpose.
     *  <p>This encoding is a classic space-time trade-off: we save space by encoding the values as
     *  deltas, but pay in time because reading, writing and calculating encoded size requires long
     *  addition/subtraction and array lookbehind. We assume that this is easily optimized by any
     *  and that the space savings are worth the trade-off.
     */
    public void method6() {
    }

    /**
     * Test method.
     *
     * @deprecated This is not useful, not adapted to the problem, and
     *     does not scale to changes in the Java language. The only use
     *     of this is to get a name.
     *
     *     <p>Besides, the real problem is that
     *     <ul>
     *         <li>enums are also classes
     *         <li>annotations are also interfaces
     *         <li>there are also anonymous classes in PMD 7.0, so this
     *         cannot even be used to downcast safely
     *     </ul>
     */
    public void method7() {
    }
}
