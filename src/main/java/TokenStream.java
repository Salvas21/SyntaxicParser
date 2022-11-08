import java.util.ArrayList;

public class TokenStream implements Stream<Token> {
    private ArrayList<Token> tokens;

    @Override
    public Token peek() {
        return null;
    }

    @Override
    public Token next() {
        return null;
    }

    @Override
    public boolean endOfStream() {
        return false;
    }
}
