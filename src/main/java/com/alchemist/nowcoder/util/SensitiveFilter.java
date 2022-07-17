package com.alchemist.nowcoder.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.text.normalizer.Trie;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    // 替换符号
    private static String REPLACEMENT = "***";

    // 前缀树根节点
    private TrieNode rootNode = new TrieNode();

    /**
     * 过滤敏感词
     *
     * @param text 待过滤的文本
     * @return  过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 若指针1处于根节点,将此符号计入结果,让指针2向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或中间,指针3都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词,将begin~position字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                // 重新指向根节点
                tempNode = rootNode;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }
    // 判断是否为符号
    private boolean isSymbol(Character c) {
        // 0x2E80-0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    @PostConstruct
    public void init() {

            try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            ){
                String keyword;
                while (((keyword = reader.readLine()) != null)) {
                    this.addKeyword(keyword);
                };
            } catch (IOException e) {
                logger.error("加载敏感词文件失败：" + e.getMessage());
            }

    }

    // 添加一个敏感词
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            tempNode = subNode;

            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }


    // 前缀树
    private class TrieNode{
        //关键词结束标志
        private boolean isKeywordEnd = false;
        // 子节点
        private Map<Character, TrieNode> subNode = new HashMap<>();

        // 获取结束标志
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        // 设置结束标志
        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public TrieNode getSubNode(Character c) {
            return subNode.get(c);
        }

        public void addSubNode(Character c, TrieNode node) {
            subNode.put(c, node);
        }
    }

}
