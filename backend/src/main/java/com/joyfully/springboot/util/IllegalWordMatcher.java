package com.joyfully.springboot.util;

import com.joyfully.springboot.util.datastructure.TrieNode;
import lombok.Setter;

import java.util.List;

/**
 * 非法词工具
 *
 * @author marx
 * @date 2022/03/23
 */
public class IllegalWordMatcher {
    /**
     * 根
     */
    private final TrieNode root = new TrieNode();

    /**
     * 取代
     */
    @Setter
    private String replace;

    public IllegalWordMatcher() {
        this("*");
    }

    public IllegalWordMatcher(String replace) {
        this.replace = replace;
    }

    /**
     * 设置匹配列表
     *
     * @param matchList 匹配列表
     */
    public void setMatchList(List<String> matchList) {
        matchList.forEach(this::addWord);
    }

    /**
     * 添加单词进Trie
     *
     * @param word 词
     */
    public void addWord(CharSequence word) {
        int n = word.length();
        TrieNode temp = root;

        for (int i = 0; i < n - 1; i++) {
            temp = temp.addNextNode(word.charAt(i));
        }

        temp.addNextNode(word.charAt(n - 1)).setEnd(true);
    }

    public String replace(CharSequence text) {
        return replace(text, replace);
    }

    /**
     * 替换敏感词 (线程不安全)
     * 需要先通过 setMatchList 或 addWord 添加屏蔽词
     *
     * @param text    需要检测的文本
     * @param replace 替换的字符
     * @return {@link String}
     */
    public String replace(CharSequence text, String replace) {
        // 当前字符为空串
        if (StringUtil.isBlank(text)) {
            return text.toString();
        }

        // 断言字典树未构建的情况，仅在VM参数加上-ea的情况下生效
        assert root.getKeyWordCount() != 0: "The match trie is null";

        StringBuilder result = new StringBuilder();

        int size = text.length();
        // 匹配的字符串起点位置，匹配的当前字符的位置
        int begin = 0, now = 0;

        TrieNode temp = root;

        // 遍历字符串
        while (now < size) {
            TrieNode next = temp.getNextNode(text.charAt(now));

            // 不存在当前字符的非法串开头
            // 将当前字符存入结果集
            // 并移动指针、恢复temp节点开始下一个字符判断
            if (next == null) {
                result.append(text.charAt(begin));
                now = begin + 1;
                begin = now;
                temp = root;
            }
            // 已经找到一个非法词匹配
            else if (next.isEnd()) {
                // 填充对应长度的替换符
                for (int i = begin; i <= now; i++) {
                    result.append(replace);
                }
                // 从下一个字符继续开始匹配
                now++;
                begin = now;
                temp = root;
            }
            // 存在非法词出现的可能，但是还不确定
            else {
                now++;
                temp = next;
            }
        }

        result.append(text.subSequence(begin, text.length()));

        return result.toString();
    }

    /**
     * 当前字符是否合法
     *
     * @param text 文本
     * @return boolean true合法 false非法
     */
    public boolean isLegal(String text) {
        // 当前字符为空串
        if (StringUtil.isBlank(text)) {
            return true;
        }

        // 断言字典树未构建的情况，仅在VM参数加上-ea的情况下生效
        assert root.getKeyWordCount() != 0: "The match trie is null";

        int size = text.length();
        // 匹配的字符串起点位置，匹配的当前字符的位置
        int begin = 0, now = 0;

        TrieNode temp = root;
        char[] textChars = text.toCharArray();

        // 遍历字符串
        while (now < size) {
            TrieNode next = temp.getNextNode(text.charAt(now));

            // 不存在当前字符的非法串开头
            // 将当前字符存入结果集
            // 并移动指针、恢复temp节点开始下一个字符判断
            if (next == null) {
                now = begin + 1;
                begin = now;
                temp = root;
            }
            // 已经找到一个非法词匹配
            else if (next.isEnd()) {
                return false;
            }
            // 存在非法词出现的可能，但是还不确定
            else {
                now++;
                temp = next;
            }
        }

        return true;
    }
}
