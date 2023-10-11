import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOEstrela extends DAOAll<Estrela, Integer>{

    @Override
    public void create(Connection connection, Estrela ob) {
        try (PreparedStatement statement = connection.prepareStatement("insert into estrela values(?,?,?,?);")){
            statement.setInt(1, ob.getId());
            statement.setDouble(2, ob.getTemperatura());
            statement.setDouble(3, ob.getTamanho());
            statement.setInt(4, ob.getGalaxia().getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}