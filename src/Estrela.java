import java.sql.ResultSet;
import java.sql.SQLException;

public class Estrela {
    private int id;
    private double temperatura;
    private double tamanho;
    private Galaxia galaxia;
    private String tabela;

    public Estrela(int id, double temperatura, double tamanho, Galaxia galaxia) {
        this.id = id;
        this.temperatura = temperatura;
        this.tamanho = tamanho;
        this.galaxia = galaxia;
    }

    public Estrela(int id, double temperatura, double tamanho) {
        this.id = id;
        this.temperatura = temperatura;
        this.tamanho = tamanho;
    }

    public Estrela(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.tamanho = rs.getDouble("tamanho");
        this.temperatura = rs.getDouble("temepratura");
        this.galaxia.setId(rs.getInt("id_galaxia"));
    }

    public String getTabela() {
        return tabela;
    }

    public int getId() {
        return id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getTamanho() {
        return tamanho;
    }

    public Galaxia getGalaxia() {
        return galaxia;
    }
}
