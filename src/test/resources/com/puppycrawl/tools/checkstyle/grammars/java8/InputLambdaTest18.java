public class InputLambdaTest18 {
	
	<T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return AnnotationSupport.
            getDirectlyAndIndirectlyPresent(Arrays.stream(getDeclaredAnnotations()).
                                            collect(Collectors.toMap(Annotation.annotationType(),
                                                                     Function.identity(),
                                                                     ((first,second) -> first),
                                                                     LinkedHashMap.neww())),
                                            annotationClass);
    }
}