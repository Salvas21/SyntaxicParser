public class InputFormatter {
    public static InputStream format(String input) {
        return new InputStream(input.toCharArray());
    }
}
