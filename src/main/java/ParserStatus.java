public class ParserStatus {
    private final boolean status;
    private final String content;

    public ParserStatus(boolean status, String content) {
        this.status = status;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Entrée " + (status ? "valide" : ("non valide" + " : " + content));
    }
}