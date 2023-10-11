import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class DAOGalaxia extends DAOAll<Galaxia,Integer>{

    @Override
    public void create(Connection connection, Galaxia ob) {
        try (PreparedStatement statement = connection.prepareStatement("insert into galaxia values(?,?,?);")){
            statement.setInt(1, ob.getId());
            statement.setString(2, ob.getTipo());
            statement.setBoolean(3, ob.isPossuiMateriaEscura());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    String getTabela() {
        return "galaxia";
    }

    @Override
    protected Galaxia converte(ResultSet rs) throws SQLException {
        return new Galaxia(rs);
    }
}
