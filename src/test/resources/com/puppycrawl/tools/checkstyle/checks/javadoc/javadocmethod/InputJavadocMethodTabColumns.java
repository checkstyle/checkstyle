/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="tabWidth" value="4"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

class InputJavadocMethodTabColumns {
	/**
	 * @param value first description
	 * @param value duplicate description
	 */
	void first(int value) {} // violation 2 lines above 'Duplicate @param tag.'

		/**
		 * @return first description
		 * @return duplicate description
		 */
		int second() { // violation 2 lines above 'Duplicate @return tag.'
		    return 0;
		}
}
