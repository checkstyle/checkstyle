/* Config:                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * basicOffset = 8                                                             //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 8                                                                //indent:1 exp:1
 */                                                                            //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

public class InputIndentationNewWithTabRegression {                            //indent:0 exp:0
	void javaDesignPatternsViolation() {                                       //indent:8 exp:8
		Commander commander = new Commander(new Sergeant(new Soldier(),        //indent:16 exp:16
				new Soldier(), new Soldier()), new Sergeant(new Soldier(),     //indent:32 exp:32
				new Soldier(), new Soldier()));                                //indent:32 exp:36 warn
	}                                                                          //indent:8 exp:8

	void javaDesignPatternsCorrect() {                                         //indent:8 exp:8
		Commander commander = new Commander(new Sergeant(new Soldier(),        //indent:16 exp:16
				new Soldier(), new Soldier()), new Sergeant(new Soldier(),     //indent:32 exp:32
				    new Soldier(), new Soldier()));                            //indent:36 exp:36
	}                                                                          //indent:8 exp:8

	private final ThreadLocal<Object> targetInThreadViolation =                //indent:8 exp:8
			new NamedThreadLocal<>("Thread-local instance of bean") {          //indent:24 exp:24
				@Override                                                      //indent:32 exp:20,24,28 warn
				public String toString() {                                     //indent:32 exp:32
					return super.toString() + " '" + targetBeanName + "'";     //indent:40 exp:28,32,36 warn
				}                                                              //indent:32 exp:20,24,28 warn
			};                                                                 //indent:24 exp:12,16,20 warn

	private final ThreadLocal<Object> targetInThreadCorrect =                  //indent:8 exp:8
		new NamedThreadLocal<>("Thread-local instance of bean") {              //indent:16 exp:16
			@Override                                                          //indent:24 exp:24
			public String toString() {                                         //indent:24 exp:24
				return super.toString() + " '" + targetBeanName + "'";         //indent:32 exp:32
			}                                                                  //indent:24 exp:24
		};                                                                     //indent:16 exp:16

	private String targetBeanName;                                             //indent:8 exp:8

	private static final class Soldier {                                       //indent:8 exp:8
	}                                                                          //indent:8 exp:8

	private static final class Sergeant {                                      //indent:8 exp:8
		Sergeant(Soldier... soldiers) {                                        //indent:16 exp:16
		}                                                                      //indent:16 exp:16
	}                                                                          //indent:8 exp:8

	private static final class Commander {                                     //indent:8 exp:8
		Commander(Sergeant... sergeants) {                                     //indent:16 exp:16
		}                                                                      //indent:16 exp:16
	}                                                                          //indent:8 exp:8

	private static class NamedThreadLocal<T> extends ThreadLocal<T> {          //indent:8 exp:8
		NamedThreadLocal(String name) {                                        //indent:16 exp:16
		}                                                                      //indent:16 exp:16
	}                                                                          //indent:8 exp:8
}                                                                              //indent:0 exp:0
