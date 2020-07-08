package org.code.runs.ds;

import java.util.List;

public interface PrefixTree {

    void add(String word);

    boolean containsPrefix(String prefix);

    List<String> wordsWithPrefix(String prefix);

    boolean isWord(String word);

}
