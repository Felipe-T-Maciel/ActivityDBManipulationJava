import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Estrela {
    private int id;
    private String nome;
    private double temperatura;
    private double tamanho;
    private Galaxia galaxia;

    public Estrela(int id,String nome, double temperatura, double tamanho, Galaxia galaxia) {
        this.id = id;
        this.nome = nome;
        this.temperatura = temperatura;
        this.tamanho = tamanho;
        this.galaxia = galaxia;
    }

    public Estrela(int id,String nome, double temperatura, double tamanho) {
        this.id = id;
        this.nome = nome;
        this.temperatura = temperatura;
        this.tamanho = tamanho;
    }

    public Estrela(ResultSet rs, DAOGalaxia daoGalaxia) throws SQLException {
        this.id = rs.getInt("id");
        this.nome = rs.getString("nome");
        this.tamanho = rs.getDouble("tamanho");
        this.temperatura = rs.getDouble("temperatura");
        if(rs.getInt("id_galaxia")!=0){
            this.galaxia = encontraGalaxia(Banco.getConnection(), daoGalaxia, rs.getInt("id_galaxia"));
        }
    }

    public String getNome() {
        return nome;
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

    private static Galaxia encontraGalaxia(Connection connection, DAOGalaxia daoGalaxia, int id) {
        do{
            for (Galaxia galax:
                    daoGalaxia.realAll(connection, daoGalaxia)) {
                if(galax.getId() == id){
                    return galax;
                }
            }
        }while (true);
    }

    @Override
    public String toString() {
        return "Estrela{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", temperatura=" + temperatura +
                ", tamanho=" + tamanho +
                '}';
    }
}
