/**
 * Measurement filters for the {@code checks.sizes} slice. Each class implements
 * {@code Filter<AstEvent, Measurement>} (or {@code Filter<FileLine, Measurement>}
 * for the file-level checks). Same architectural constraints as the metrics
 * pipeline package: no framework inheritance, no upcalls, dependencies restricted
 * to the architecture allow-list.
 */
package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;
