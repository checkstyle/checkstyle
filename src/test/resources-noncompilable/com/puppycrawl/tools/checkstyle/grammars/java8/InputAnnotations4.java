//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotations4 {
	
	public static void methodName(@NotNull String args) {
		
	}


    public static <T> T[] checkNotNullContents(T @Nullable [] array) {
        return null;
    }
    public static int checkNotNullContents(int @Nullable [] array) {
        return 0;
    }
    public static int checkNotNullContents(String @Nullable [] array) {
        return 0;
    }
	
	@Target(ElementType.TYPE_USE)
	@interface NotNull {

	}
}
