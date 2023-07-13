package com.example.qa_backend.SensitiveWord;

import java.util.*;

public class SimpleTrie {
    /**
     * 一个敏感词结束后对应的 key
     */
    private static final Character CHARACTER_END = '\0';

    /**
     * 使用敏感词，构建的前缀树
     */
    private final Map<Character, Object> children;

    /**
     * 基于字符串，构建前缀树
     *
     * @param strs 字符串数组
     */
    public SimpleTrie(List<String> strs) {
        children = new HashMap<>();
        // 构建树
        strs.sort(Comparator.comparingInt(String::length));
        for (String str : strs) {
            Map<Character, Object> child = children;
            // 遍历每个字符
            for (Character c : str.toCharArray()) {
                // 如果已经到达结束，就没必要在添加更长的敏感词。
                // 例如说，有两个敏感词是：吃饭啊、吃饭。输入一句话是 “我要吃饭啊”，则只要匹配到 “吃饭” 这个敏感词即可。
                if (child.containsKey(CHARACTER_END)) {
                    break;
                }
                if (!child.containsKey(c)) {
                    child.put(c, new HashMap<>());
                }
                child = (Map<Character, Object>) child.get(c);
            }
            // 结束
            child.put(CHARACTER_END, null);
        }
    }

    /**
     * 验证文本是否合法，即不包含敏感词
     *
     * @param text 文本
     * @return 是否 ok
     */
    public boolean isValid(String text) {
        // 遍历 text，使用每一个 [i, n) 段的字符串，使用 children 前缀树匹配，是否包含敏感词
        for (int i = 0; i < text.length() - 1; i++) {
            Map<Character, Object> child = (Map<Character, Object>) children.get(text.charAt(i));
            if (child == null) {
                continue;
            }
            boolean ok = recursion(text, i + 1, child);
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证文本从指定位置开始，是否包含某个敏感词
     *
     * @param text  文本
     * @param index 开始位置
     * @param child 节点（当前遍历到的）
     * @return 是否包含
     */
    private boolean recursion(String text, int index, Map<Character, Object> child) {
        if (index == text.length()) {
            return true;
        }
        child = (Map<Character, Object>) child.get(text.charAt(index));
        return child == null || !child.containsKey(CHARACTER_END) && recursion(text, ++index, child);
    }
}
