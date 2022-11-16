public class InputStream implements Stream<Character> {
    private final char[] input;
    private int position = 0;

    public InputStream(char[] input) {
        this.input = input;
    }

    @Override
    public Character peek() {
        return input[position];
    }

    @Override
    public Character next() {
        return input[position++];
    }

    @Override
    public boolean endOfStream() {
        return position >= input.length - 1;
    }

}
