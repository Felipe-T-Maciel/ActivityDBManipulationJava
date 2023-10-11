import java.sql.Connection;
import java.util.Set;

public interface ICRUD<T,ID> {
    void create(Connection connection, T ob);
    Set<T> realAll(Connection connection, DAOGalaxia daoGalaxia);
    void delete(Connection connection,ID id);
    void editar(Connection connection, T ob);
}
