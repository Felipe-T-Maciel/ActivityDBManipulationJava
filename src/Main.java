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

        String urlBanco = "jbdc:mysql://localhost:3306/attjava";
        String user = "root";
        String senha = "root";

        try (Connection connection = DriverManager.getConnection(urlBanco,user,senha)) {
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
                        menuGalaxia(connection);
                    }
                    case 2->{
                        menuEstrela(connection);
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

    private static void menuEstrela(Connection connection) {
    }

    private static void menuGalaxia(Connection connection) {
        DAOGalaxia daoGalaxia = new DAOGalaxia();
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
