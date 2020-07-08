package org.code.runs.ds;

import java.util.ArrayList;
import java.util.List;

public class Trie implements PrefixTree {

    private class Node {
        Node[] children;
        char ch;
        boolean word;

        public Node(char ch, boolean word) {
            children = new Node[26];
            this.ch = ch;
            this.word = word;
        }
    }

    private Node root = new Node('#', false);

    @Override
    public void add(String word) {
        Node ptr = root;
        for (char ch: word.toCharArray()) {
            int index = ch - 'a';
            if (ptr.children[index] == null) {
                ptr.children[index] = new Node(ch, false);
            }
            ptr = ptr.children[index];
        }
        ptr.word = true;
    }

    @Override
    public List<String> wordsWithPrefix(String prefix) {
        List<String> wordList = new ArrayList<>();
        Node ptr = find(prefix);
        if (ptr== null) {
            return wordList;
        }

        // Do pre-order traversal of the tree and add all words to the list.
        preOrderTraversal(ptr, wordList, prefix);
        return wordList;
    }

    private void preOrderTraversal(Node ptr, List<String> wordList, String word) {
        if (ptr == null) {
            return;
        }

        if (ptr.word) {
            wordList.add(word);
        }

        for (int i=0; i<ptr.children.length; i++) {
            char ch = (char) ('a' + i);
            preOrderTraversal(ptr.children[i], wordList, word + ch);
        }

    }

    @Override
    public boolean isWord(String word) {
        Node ptr = find(word);
        if (ptr == null) {
            return false;
        }
        return ptr.word;
    }

    @Override
    public boolean containsPrefix(String prefix) {
        return find(prefix) != null;
    }

    private Node find(String word) {
        Node ptr = root;
        for (char ch: word.toCharArray()) {
            int index = ch - 'a';
            if (ptr.children[index] == null) {
                return null;
            }
            ptr = ptr.children[index];
        }
        return ptr;
    }
}
