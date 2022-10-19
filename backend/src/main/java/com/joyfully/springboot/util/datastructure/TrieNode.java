package com.joyfully.springboot.util.datastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * 单词查找树节点
 *
 * @author marx
 * @date 2022/03/23
 */
public class TrieNode {
    /**
     * 数据
     */
    private Character data;
    /**
     * 结束
     */
    private boolean end = false;
    /**
     * 下一个节点
     */
    private final Map<Character, TrieNode> nextNodes;

    public TrieNode() {
        nextNodes = new HashMap<>();
    }

    public TrieNode(Character data) {
        this.data = data;
        nextNodes = new HashMap<>();
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public TrieNode getNextNode(Character data) {
        return nextNodes.get(data);
    }

    /**
     * 添加下一个节点
     *
     * @param character 字符
     * @return {@link TrieNode}
     */
    public TrieNode addNextNode(Character character) {
        if (nextNodes.get(character) == null) {
            nextNodes.put(character, new TrieNode(character));
        }
        return nextNodes.get(character);
    }

    public int getKeyWordCount() {
        return nextNodes.size();
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "data=" + data +
                ", end=" + end +
                ", nextNodes=" + nextNodes +
                '}';
    }
}