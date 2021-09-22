package 栈;

import java.util.HashMap;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 *
 * @author xiexu
 * @create 2021-07-18 3:09 下午
 */
public class _20_有效的括号 {

    /**
     * [({})]
     *
     * @param s
     * @return
     */
    public boolean isValid1(String s) {
        while (s.contains("{}") || s.contains("()") || s.contains("[]")) {
            s = s.replace("{}", "");
            s = s.replace("()", "");
            s = s.replace("[]", "");
        }
        return s.isEmpty();
    }

    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        int length = s.length();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') { //左字符
                stack.push(c);
            } else { //右字符
                if (stack.isEmpty()) {
                    return false;
                }
                char left = stack.pop();
                if (left == '(' && c != ')') {
                    return false;
                }
                if (left == '{' && c != '}') {
                    return false;
                }
                if (left == '[' && c != ']') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static HashMap<Character, Character> map = new HashMap<>();

    static {
        //key - value
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');
    }

    public boolean isValid3(String s) {
        Stack<Character> stack = new Stack<>();
        int length = s.length();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) { //左字符
                stack.push(c);
            } else { //右字符
                if (stack.isEmpty()) {
                    return false;
                }
                char left = stack.pop();
                if (c != map.get(left)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

}
