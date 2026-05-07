public class Node {
    private boolean mark;
    private int count;
    private Node[] next = new Node[26];

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Node[] getNext() {
        return next;
    }

    public void setNext(Node[] next) {
        this.next = next;
    }
}
