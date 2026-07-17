/* Config:                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * basicOffset = 8                                                             //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 8                                                                //indent:1 exp:1
 */                                                                            //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

public class InputIndentationNewWithTabSpring {                                //indent:0 exp:0
	private final LifecycleMetadata emptyLifecycleMetadataViolation =          //indent:8 exp:8
			//below indent:24 exp:24
			new LifecycleMetadata(Object.class, Collections.emptyList(), Collections.emptyList()) {
				@Override                                                      //indent:32 exp:20,24,28 warn
				//below indent:32 exp:32
				public void checkInitDestroyMethods(RootBeanDefinition beanDefinition) {
				}                                                              //indent:32 exp:20,24,28 warn
				@Override                                                      //indent:32 exp:20,24,28 warn
				//below indent:32 exp:32
				public void invokeInitMethods(Object target, String beanName) {
				}                                                              //indent:32 exp:20,24,28 warn
				@Override                                                      //indent:32 exp:20,24,28 warn
				//below indent:32 exp:32
				public void invokeDestroyMethods(Object target, String beanName) {
				}                                                              //indent:32 exp:20,24,28 warn
				@Override                                                      //indent:32 exp:20,24,28 warn
				public boolean hasDestroyMethods() {                           //indent:32 exp:32
					return false;                                              //indent:40 exp:28,32,36 warn
				}                                                              //indent:32 exp:20,24,28 warn
			};                                                                 //indent:24 exp:12,16,20 warn

	private final LifecycleMetadata emptyLifecycleMetadataCorrect =            //indent:8 exp:8
		//below indent:16 exp:16
		new LifecycleMetadata(Object.class, Collections.emptyList(), Collections.emptyList()) {
			@Override                                                          //indent:24 exp:24
			//below indent:24 exp:24
			public void checkInitDestroyMethods(RootBeanDefinition beanDefinition) {
			}                                                                  //indent:24 exp:24
			@Override                                                          //indent:24 exp:24
			public void invokeInitMethods(Object target, String beanName) {    //indent:24 exp:24
			}                                                                  //indent:24 exp:24
			@Override                                                          //indent:24 exp:24
			public void invokeDestroyMethods(Object target, String beanName) { //indent:24 exp:24
			}                                                                  //indent:24 exp:24
			@Override                                                          //indent:24 exp:24
			public boolean hasDestroyMethods() {                               //indent:24 exp:24
				return false;                                                  //indent:32 exp:32
			}                                                                  //indent:24 exp:24
		};                                                                     //indent:16 exp:16

	private static class LifecycleMetadata {                                   //indent:8 exp:8
		//below indent:16 exp:16
		LifecycleMetadata(Class<?> targetClass, Object initMethods, Object destroyMethods) {
		}                                                                      //indent:16 exp:16

		//below indent:16 exp:16
		public void checkInitDestroyMethods(RootBeanDefinition beanDefinition) {
		}                                                                      //indent:16 exp:16

		public void invokeInitMethods(Object target, String beanName) {        //indent:16 exp:16
		}                                                                      //indent:16 exp:16

		public void invokeDestroyMethods(Object target, String beanName) {     //indent:16 exp:16
		}                                                                      //indent:16 exp:16

		public boolean hasDestroyMethods() {                                   //indent:16 exp:16
			return true;                                                       //indent:24 exp:24
		}                                                                      //indent:16 exp:16
	}                                                                          //indent:8 exp:8

	private static final class RootBeanDefinition {                            //indent:8 exp:8
	}                                                                          //indent:8 exp:8

	private static final class Collections {                                   //indent:8 exp:8
		static Object emptyList() {                                            //indent:16 exp:16
			return new Object();                                               //indent:24 exp:24
		}                                                                      //indent:16 exp:16
	}                                                                          //indent:8 exp:8
}                                                                              //indent:0 exp:0
