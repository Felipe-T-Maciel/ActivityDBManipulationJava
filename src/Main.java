//create database GalaxyDB;
//        use GalaxyDB
//
//        create table galaxia(
//        id int not null primary key,
//        tipo varchar(55) not null,
//        materiaEscura boolean not null
//        );
//
//        create table estrela(
//        id int not null primary key,
//        temperatura float not null,
//        tamanho float not null,
//        id_galaxia int,
//        foreign key (id_galaxia) references galaxia(id)
//        );

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String urlBanco = "jdbc:mysql://localhost:3306/galaxydb";
        String user = "root";
        String senha = "root";

        try (Connection connection = DriverManager.getConnection(urlBanco,user,senha)) {
            DAOGalaxia daoGalaxia = new DAOGalaxia();
            do {
                System.out.print("""
                    -- Bem vindo ao Banco de Dados Galactico --
                        
                        [1] Galaxia
                        [2] Estrela
                        [3] Sair
                    >\t""");
                int escolha = sc.nextInt();
                switch (escolha){
                    case 1->{
                        menuGalaxia(connection, daoGalaxia);
                    }
                    case 2->{
                        menuEstrela(connection, daoGalaxia);
                    }
                    case 3->{
                        System.exit(0);
                    }
                }
            }while (true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private static void menuEstrela(Connection connection, DAOGalaxia daoGalaxia) {
        DAOEstrela daoEstrela = new DAOEstrela();
        do{
            System.out.print("""
                    --- Menu Estrela ---
                    [1] Cadastrar uma nova Estrela
                    [2] Listar as Estrelas atualmente conhecidas
                    [3] Deletar uma Estrela
                    [4] Voltar
                    >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1->{
                    daoEstrela.create(connection,cadastrarEstrela(connection,daoGalaxia));
                }
                case 2->{
                    for (Estrela estrela:
                            daoEstrela.realAll(connection)) {
                        System.out.println(estrela.toString());
                    }
                }
                case 3 -> {
                    daoEstrela.delete(connection, retornaIDEscolhidoEstrela(daoEstrela, connection));
                }
                case 4->{
                    return;
                }
            }
        }while (true);
    }

    private static Estrela cadastrarEstrela(Connection connection, DAOGalaxia daoGalaxia) {
        Galaxia galaxia = null;
        System.out.print("Qual a temperatura da Estrela: ");
        double temperatura = sc.nextDouble();

        System.out.print("Quanto km perimetro a Estrela possui: ");
        double tamanho = sc.nextDouble();

        int opcao = 0;
        do{
            System.out.print("""
                A Estrela esta em uma galaxia?
                
                [1] Sim
                [2] Não
                [3] A Galaxia é desconhecida
                >\t""");
            opcao = sc.nextInt();
            switch (opcao){
                case 1->{
                    try (PreparedStatement statement = connection.prepareStatement("Select max(id) from galaxia")){
                        ResultSet rs = statement.executeQuery();
                        if(rs.next()){
                            return new Estrela(rs.getInt("id"),temperatura,tamanho,galaxia);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                case 2->{
                    try (PreparedStatement statement = connection.prepareStatement("Select max(id) from galaxia")){
                        ResultSet rs = statement.executeQuery();
                        if(rs.next()){
                            return new Estrela(rs.getInt("id"),temperatura,tamanho);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                case 3->{
                    menuGalaxia(connection,daoGalaxia);
                }
            }
        }while (opcao<1 || opcao>3);
        throw new RuntimeException();
    }

    private static Galaxia buscarGalaxia(DAOGalaxia daoGalaxia, Connection connection) {
        do{
            int id = retornaIDEscolhidoGalaxia(daoGalaxia, connection);
            for (Galaxia galaxi: daoGalaxia.realAll(connection)) {
                if(id == galaxi.getId()){
                    return galaxi;
                }
            }
        }while (true);
    }

    private static void menuGalaxia(Connection connection, DAOGalaxia daoGalaxia) {
        do{
            System.out.print("""
                    --- Menu Galaxia ---
                    [1] Cadastrar uma nova Galaxia
                    [2] Listar as Galaxias atualmente conhecidas
                    [3] Deletar uma Galaxia
                    [4] Voltar
                    >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1->{
                    daoGalaxia.create(connection,cadastrarGalaxia(connection));
                }
                case 2->{
                    for (Galaxia galaxia:
                         daoGalaxia.realAll(connection)) {
                        System.out.println(galaxia.toString());
                    }
                }
                case 3 -> {
                    daoGalaxia.delete(connection, retornaIDEscolhidoGalaxia(daoGalaxia, connection));
                }
                case 4->{
                    return;
                }
            }
        }while (true);
    }

    static int retornaIDEscolhidoGalaxia(DAOGalaxia daoGalaxia, Connection connection){
        do{
            System.out.print("O id que deseja: ");
            int id = sc.nextInt();
            for (Galaxia galaxia:
                    daoGalaxia.realAll(connection)) {
                if(galaxia.getId() == id){
                    return id;
                }
            }
        }while (true);
    }

    static int retornaIDEscolhidoEstrela(DAOEstrela daoEstrela, Connection connection){
        do{
            System.out.print("O id que deseja: ");
            int id = sc.nextInt();
            for (Estrela estrela:
                    daoEstrela.realAll(connection)) {
                if(estrela.getId() == id){
                    return id;
                }
            }
        }while (true);
    }

    private static Galaxia cadastrarGalaxia(Connection connection) {
        int opcao = 0;
        String tipo = "";
        do{
            System.out.print("""
                Qual o tipo da galaxia?
                
                [1] Espiral
                [2] Elíptica
                [3] Irregular
                >\t""");
            opcao = sc.nextInt();
            switch (opcao){
                case 1 -> {
                    tipo = "Espiral";
                }
                case 2 -> {
                    tipo = "Elíptica";
                }
                case 3 -> {
                    tipo = "Irregular";
                }
                default -> {
                    System.out.println("Escolha uma opção valida!");
                }
            }
        }while (opcao<1 || opcao>3);
        opcao = 0;
        boolean possuiMateriaEscura = false;
        do{
            System.out.print("""
                A galaxia tem presença de materia escura?
                
                        [1] Sim       [2] Não
                >\t""");
            opcao = sc.nextInt();
                if(opcao==1){
                    possuiMateriaEscura = true;
                }else if(opcao!=2){
                    System.out.println("Escolha uma opção valida!");
                }
        }while (opcao<1 || opcao>2);
        try (PreparedStatement statement = connection.prepareStatement("Select max(id) from galaxia")){
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return new Galaxia(rs.getInt("id")+1,tipo,possuiMateriaEscura);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException();
    }
}
