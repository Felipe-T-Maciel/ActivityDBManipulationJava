import java.sql.ResultSet;
import java.sql.SQLException;

public class Galaxia {
    private int id;
    private String tipo;
    private boolean possuiMateriaEscura;
    private String tabela = "galaxia";

    public Galaxia(int id, String tipo, boolean possuiMateriaEscura) {
        this.id = id;
        this.tipo = tipo;
        this.possuiMateriaEscura = possuiMateriaEscura;
    }

    public Galaxia(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.tipo = rs.getString("tipo");
        this.possuiMateriaEscura = rs.getBoolean("materiaEscura");
    }

    public String getTabela() {
        return tabela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isPossuiMateriaEscura() {
        return possuiMateriaEscura;
    }

    public void setPossuiMateriaEscura(boolean possuiMateriaEscura) {
        this.possuiMateriaEscura = possuiMateriaEscura;
    }
}
