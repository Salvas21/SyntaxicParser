import java.util.ArrayList;

public class TokenStream implements Stream<Token> {
    private final ArrayList<Token> tokens;
    private int position = 0;

    public TokenStream(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Token peek() {
        return tokens.get(position);
    }

    @Override
    public Token next() {
        return tokens.get(position++);
    }

    @Override
    public boolean endOfStream() {
        return position >= tokens.size();
    }
}
