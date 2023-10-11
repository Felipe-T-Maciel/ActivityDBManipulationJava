import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

abstract class DAOAll<T,ID> implements ICRD<T,ID>{
    abstract String getTabela();

    @Override
    public Set<T> realAll(Connection connection) {
        Set<T> lista = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from "+getTabela()+";")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                lista.add(converte(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    protected abstract T converte(ResultSet rs) throws SQLException;

    public void delete(Connection connection, Integer integer){
        try (PreparedStatement statement = connection.prepareStatement("delete from "+getTabela()+" where=?")){
            statement.setInt(1,integer);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
