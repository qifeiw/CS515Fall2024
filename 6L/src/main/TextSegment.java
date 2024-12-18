public class TextSegment implements Comparable<TextSegment>{
    private String _text;
    private int _order;

    TextSegment(int order, String text) {
        _text = text;
        _order = order;
    }

    @Override
    public String toString() { return _text; }

    @Override
    public int compareTo(TextSegment other) {
        return Integer.compare(this._order, other._order);
        //TODO: Implement
    }
}
