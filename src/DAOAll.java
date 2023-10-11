import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

abstract class DAOAll<T,ID> implements ICRUD<T,ID> {
    abstract String getTabela();

    @Override
    public Set<T> realAll(Connection connection, DAOGalaxia daoGalaxia) {
        Set<T> lista = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from "+getTabela()+";")){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                lista.add(converte(rs,daoGalaxia));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    protected abstract T converte(ResultSet rs, DAOGalaxia daoGalaxia) throws SQLException;

    public void delete(Connection connection, Integer integer){
        try (PreparedStatement statement = connection.prepareStatement("delete from "+getTabela()+" where id = ?")){
            statement.setInt(1,integer);
            try (PreparedStatement statement2 = connection.prepareStatement("select * from estrela")){
                ResultSet rs = statement2.executeQuery();
                while (rs.next()){
                    if(rs.getInt("id_galaxia")==integer){
                        try (PreparedStatement statement3 = connection.prepareStatement("delete from estrela where id = "+rs.getInt("id")+"")) {
                            statement3.execute();
                        }
                    }
                }
            }
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    int ultimoID(){
        try (Connection connection = Banco.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("Select max(id) as id from "+getTabela()+"")){
                ResultSet rs = statement.executeQuery();
                rs.next();
                return (rs.getInt("id")+1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException();
    }
}
