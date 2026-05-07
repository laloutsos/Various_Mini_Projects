import java.util.*;

public class StringTrie {

    private static final int R = 26;  // number of different characters
    private static int N = 0;         // number of words in trie
    private Node root;                // root of trie

    public int contains(String s) {
        Node x = contains(root, s, 0);
        return (x == null) ? 0 : x.getCount();
    }

    private Node contains(Node x, String s, int d) {
        if (x == null) return null;
        if (d == s.length()) return x;
        int j = s.charAt(d) - 'a';
        return contains(x.getNext()[j], s, d + 1);
    }

    public void insert(String s) {
        root = insert(root, s, 0);
    }

    private Node insert(Node x, String s, int d) {
        if (x == null) x = new Node();
        if (d == s.length()) {
            if (!x.isMark()) {
                x.setMark(true);
                N++;
            }
            x.setCount(x.getCount() + 1);
            return x;
        }
        int j = s.charAt(d) - 'a';
        Node[] next = x.getNext();
        next[j] = insert(next[j], s, d + 1);
        x.setNext(next);
        return x;
    }

    public void delete(String s) {
        root = delete(root, s, 0);
    }

    private Node delete(Node x, String s, int d) {
        if (x == null) return null;

        if (d == s.length()) {
            if (x.getCount() == 0) {
                x.setMark(false);
                N--;
            }
        } else {
            int j = s.charAt(d) - 'a';
            Node[] next = x.getNext();
            next[j] = delete(next[j], s, d + 1);
            x.setNext(next);
        }

        if (x.isMark()) {
            x.setCount(x.getCount() - 1);
            if (x.getCount() == 0) {
                x.setMark(false);
                N--;
            }
            return x;
        }

        for (int j = 0; j < R; j++) {
            if (x.getNext()[j] != null) {
                x.setCount(x.getCount() - 1);
                return x;
            }
        }

        return null;
    }

    public Iterable<String> words() {
        Queue<String> q = new Queue<>(N);
        collect(root, "", q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.isMark()) q.put(pre);
        for (int j = 0; j < R; j++) {
            char c = (char) ('a' + j);
            collect(x.getNext()[j], pre + c, q);
        }
    }

    private void ccollect(Node x, String pre, Queue<Item> q) {
        if (x == null) return;
        if (x.isMark()) {
            Item p = new Item();
            p.setS(pre);
            p.setCount(x.getCount());
            q.put(p);
        }
        for (int j = 0; j < R; j++) {
            char c = (char) ('a' + j);
            ccollect(x.getNext()[j], pre + c, q);
        }
    }

    private void collectt(Node x, String pattern, String current, Queue<Item> q) {
        if (x == null) return;

        if (current.length() == pattern.length()) {
            if (x.isMark()) {
                Item item = new Item();
                item.setS(current);
                item.setCount(x.getCount());
                q.put(item);
            }
            return;
        }

        char c = pattern.charAt(current.length());
        if (c == '?') {
            for (int j = 0; j < R; j++) {
                char nextChar = (char) ('a' + j);
                collectt(x.getNext()[j], pattern, current + nextChar, q);
            }
        } else {
            int j = c - 'a';
            collectt(x.getNext()[j], pattern, current + c, q);
        }
    }

    public Iterable<Item> wordsWithPrefix(String s) {
        Queue<Item> q = new Queue<>(N);
        Node x = contains(root, s, 0);
        ccollect(x, s, q);
        return q;
    }

    public int countWordsWithPrefix(String s) {
        Node x = contains(root, s, 0);
        return (x == null) ? 0 : countWords(x);
    }

    private int countWords(Node x) {
        if (x == null) return 0;
        int count = x.isMark() ? 1 : 0;
        for (int c = 0; c < R; c++) {
            count += countWords(x.getNext()[c]);
        }
        return count;
    }

    public Iterable<Item> wordsThatMatch(String s) {
        Queue<Item> q = new Queue<>(N);
        collectt(root, s, "", q);
        return q;
    }

    public String longestPrefixOf(String s) {
        int length = longestPreLength(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int longestPreLength(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (d == s.length()) return d;
        int j = s.charAt(d) - 'a';
        if (x.getNext()[j] == null) return length;
        return longestPreLength(x.getNext()[j], s, d + 1, length + 1);
    }

    public Item mostFrequent() {
        Item I = new Item();
        findMostFrequent(root, "", I);
        return I;
    }

    private void findMostFrequent(Node x, String current, Item mostFrequentItem) {
        if (x == null) return;
        if (x.isMark() && x.getCount() > mostFrequentItem.getCount()) {
            mostFrequentItem.setS(current);
            mostFrequentItem.setCount(x.getCount());
        }
        for (int j = 0; j < R; j++) {
            char nextChar = (char) ('a' + j);
            findMostFrequent(x.getNext()[j], current + nextChar, mostFrequentItem);
        }
    }

    public int size() {
        return N;
    }

    public void printWords(Iterable<Item> S) {
        for (Item s : S) {
            System.out.println(s.getS() + " " + s.getCount());
        }
    }
}
