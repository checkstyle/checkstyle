/**
 * Pipe-and-Filter core infrastructure: pipes, filter contract, pipeline composer,
 * common reusable filters, and immutable pipeline messages. This package is
 * framework-free; classes here MUST NOT depend on Checkstyle execution
 * infrastructure ({@code TreeWalker}, {@code Checker}, {@code AbstractCheck},
 * {@code AbstractFileSetCheck}). Drivers in {@code checks.metrics} and
 * {@code checks.sizes} bridge this package to the framework.
 */
package com.puppycrawl.tools.checkstyle.checks.pipeline;
