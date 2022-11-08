public class InputStream implements Stream<Character> {
    private char[] input;
    private int position = 0;
    private int line = 1;
    private int col = 0;

    public InputStream(char[] input) {
        this.input = input;
    }

    @Override
    public Character peek() {
        return null;
    }

    @Override
    public Character next() {
        return null;
    }

    @Override
    public boolean endOfStream() {
        return false;
    }

}
