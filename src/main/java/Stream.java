public interface Stream<T> {
    T peek();
    T next();
    boolean endOfStream();
}
