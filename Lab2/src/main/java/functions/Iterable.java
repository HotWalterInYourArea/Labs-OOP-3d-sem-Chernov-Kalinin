package functions;

import java.util.Iterator;

public interface Iterable<T> {
    Iterator<T> iterator() throws UnsupportedOperationException;
}
