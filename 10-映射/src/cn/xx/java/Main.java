package cn.xx.java;

import cn.xx.java.map.Map;
import cn.xx.java.map.TreeMap;
import cn.xx.java.set.Set;
import cn.xx.java.set.TreeSet;

/**
 * @author xiexu
 * @create 2021-08-13 10:33 上午
 */
public class Main {

    static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("class", 2);
        map.put("public", 5);
        map.put("text", 6);
        map.put("public", 8);
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test2() {
        Set<String> set = new TreeSet<>();
        set.add("a");
        set.add("b");
        set.add("d");
        set.add("a");
        set.traversal(new Set.Visitor<String>() {
            @Override
            public boolean visit(String element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void main(String[] args) {
        test2();
    }
}
