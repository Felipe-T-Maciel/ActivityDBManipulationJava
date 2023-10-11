import java.sql.Connection;
import java.util.Set;

public interface ICRD<T,ID> {
    void create(Connection connection, T ob);
    Set<T> realAll(Connection connection);
    void delete(Connection connection,ID id);
}
