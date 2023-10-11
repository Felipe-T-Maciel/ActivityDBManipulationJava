public class Estrela {
    private int id;
    private double temperatura;
    private double tamanho;
    private Galaxia galaxia;

    public Estrela(int id, double temperatura, double tamanho, Galaxia galaxia) {
        this.id = id;
        this.temperatura = temperatura;
        this.tamanho = tamanho;
        this.galaxia = galaxia;
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
