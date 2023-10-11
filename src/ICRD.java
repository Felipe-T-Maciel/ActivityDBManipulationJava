import java.sql.Connection;

public interface ICRD<T,ID> {
    void create(Connection connection, T ob);
    void readAll(Connection connection);
    void delete(Connection connection,ID id);
}
