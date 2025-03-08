package com.puppycrawl.tools.checkstyle.utils.checkutil;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * It is class.
 * @param <V> ssss
 * @param <C> dddd
 */
public class InputCheckUtilTest<V, C> {
    private Map<String, Integer> field = new HashMap<>();
    protected int[] array = new int[10];
    public static final Long VAR_1 = 1L;
    static final double VAR_2 = 5.0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InputCheckUtilTest<V, C> that = (InputCheckUtilTest<V, C>) o;
        return Objects.equal(field, that.field) &&
                Objects.equal(array, that.array);
    }

    public void doSomething(int value) {
        if (field.isEmpty()) {
            field.put(String.valueOf(value), value << 1);
        } else {
            if(!field.containsKey(String.valueOf(value))){
                field.put(String.valueOf(value), value << 1);
            }
        }

        if(!field.containsKey(String.valueOf(value))){
            field.put(String.valueOf(value), value << 1);
        } else if (value == 10) {
            array[9] = -1;
        }

        if(field.size() < 10){
            array[9] = field.getOrDefault(String.valueOf(value), -1);
        }

    }

    public Map<String, Integer> getField() {
        return new HashMap<>(field);
    }

    public void setField(Map<String, Integer> field) {
        this.field = field;
    }

    public int [] setArray(int... array) {
        this.array = array;
        if(array.length > 0){
            return this.array;
        } else {
            return new int[4];
        }
    }

    public void testReceiver(InputCheckUtilTest<V, C>this, int variable) {}

    public interface Example {
        void method();
    }

    enum Enum {
        A;
    }
}
