import java.sql.ResultSet;
import java.sql.SQLException;

public class Galaxia {
    private int id;
    private String nome;
    private String tipo;
    private boolean possuiMateriaEscura;

    public Galaxia(int id,String nome, String tipo, boolean possuiMateriaEscura) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.possuiMateriaEscura = possuiMateriaEscura;
    }

    public Galaxia(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nome = rs.getString("nome");
        this.tipo = rs.getString("tipo");
        this.possuiMateriaEscura = rs.getBoolean("materiaEscura");
    }

    public String getNome() {
        return nome;
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

    @Override
    public String toString() {
        return "Galaxia{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", possuiMateriaEscura=" + possuiMateriaEscura +
                '}';
    }
}
