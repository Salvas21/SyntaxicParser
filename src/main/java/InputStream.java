public class InputStream implements Stream<Character> {
    private char[] input;
    private int position = 0;
    private int line = 1;
    private int col = 0;
    // TODO : implement line and col for specific errors

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
