package com.puppycrawl.tools.checkstyle.checks.coding.diamondoperatorforvariabledefinition;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.tree.TreePath;
import com.google.common.base.Predicate;

public class InputDiamondOperatorForVariableDefinition {
    static Map<String, List<String>> myMap1 = new TreeMap<String, List<String>>();
    List<Integer> list1 =
            new ArrayList<Integer>();
    Map<String, List<String>> myMap = new HashMap<String, List<String>>();
    List<Character> abc = new LinkedList<Character>();
    private TreePath[] suppressParentPaths(TreePath[] paths) {
        List<TreePath> selectedPaths = Arrays.asList(paths);
        Collections.sort(selectedPaths, new Comparator<TreePath>() {

            public int compare(TreePath o1, TreePath o2) {
                return 1;
            }
        });
        return paths;
     }

    private TreePath[] suppressParentPaths1(TreePath[] paths) {
        List<TreePath> selectedPaths = Arrays.asList(paths);
        Collections.sort(selectedPaths, new Comparator<TreePath>() {

            public int compare(TreePath o1, TreePath o2) {
                return 0;
            }
        });


    Object statusArray[] = null;
    Arrays.sort(statusArray, new Comparator<Object>() {
        public int compare(Object s1, Object s2) {
            return 1;
        }
    });
    return paths; }

    List<Integer> list = new LinkedList<Integer>();
    private transient Map<Method, String> shadowMatchCache = new ConcurrentHashMap<Method, String>(32);

    private Predicate<File> inputCsvFilesFilter = new Predicate<File>() {
    	public boolean apply(File input)
		{
			return false;
		}
    };

    String str = new String("");

    Object obj;

    Class<?>[] resolvedInterfaces = new Class<?>[5];
    Class<?> resolvedInterfacesDif[] = new Class<?>[5];

    private Object[] statusArray;
    List<Object> ruleViolations = new ArrayList<Object>(Arrays.asList(statusArray));

    interface RuleViolation {
       void doSmth();
    }
    class ParametricRuleViolation<T> implements RuleViolation {
        public void doSmth() {
            // no code
        }
    }
    RuleViolation ruleViolation = new ParametricRuleViolation<Object>();
    java.util.Date date = new java.util.Date();

    static java.util.Map<List<String>, String> myMap2 = new java.util.TreeMap<List<String>, String>();
    static java.util.Map<List<String>, String> myMap3 = new TreeMap<List<String>, String>();
}

