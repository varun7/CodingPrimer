package org.code.runs.algo;

public class KMP {

    public static boolean contains(String text, String pattern) {
        int [] kmp = preProcessing(pattern);
        for (int t=0, p=0; t < text.length(); ) {
            char tCh = text.charAt(t);
            char pCh = pattern.charAt(p);

            if (tCh == pCh) {
                t++;
                p++;
            } else if (p == 0){
                t++;
            } else {
                p = kmp[p-1];
            }

            if (p == pattern.length()) {
                return true;
            }
        }
        return false;
    }

    private static int[] preProcessing(String pattern) {
        int[] kmp = new int[pattern.length()];
        kmp[0] = 0;
        for (int i=1, len = 0; i<pattern.length(); ) {
            char ch = pattern.charAt(i);
            char pCh = pattern.charAt(len);
            if (ch == pCh) {
                len++;
                kmp[i++] = len;
            } else {
                if (len == 0) {
                    kmp[i++] = 0;
                } else {
                    len = kmp[len-1];
                }
            }
        }
        return kmp;
    }
}
