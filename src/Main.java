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
        try (Connection connection = Banco.getConnection()) {
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
                    [4] Editar uma Estrela
                    [5] Voltar
                    >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1->{
                    daoEstrela.create(connection,cadastrarEstrela(connection,daoGalaxia, daoEstrela,0));
                }
                case 2->{
                    for (Estrela estrela:
                            daoEstrela.realAll(connection, daoGalaxia)) {
                        System.out.print(estrela.toString());
                        if(estrela.getGalaxia()!=null){
                            System.out.println(" | "+estrela.getGalaxia().toString());
                        }
                    }
                }
                case 3 -> {
                    daoEstrela.delete(connection, retornaIDEscolhidoEstrela(daoEstrela, connection, daoGalaxia));
                }
                case 4 -> {
                    daoEstrela.editar(connection, cadastrarEstrela(connection,daoGalaxia,daoEstrela, retornaIDEscolhidoEstrela(daoEstrela,connection,daoGalaxia)));
                }
                case 5->{
                    return;
                }
            }
        }while (true);
    }

    private static Estrela cadastrarEstrela(Connection connection, DAOGalaxia daoGalaxia, DAOEstrela daoEstrela, int id) {
        System.out.print("Qual o nome da Estrela: ");
        String nome = sc.next();

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
                    if(id==0){
                        return new Estrela(daoEstrela.ultimoID(),nome, temperatura,tamanho,encontraGalaxia(connection,daoGalaxia));
                    }else {
                        return new Estrela(id,nome, temperatura,tamanho,encontraGalaxia(connection,daoGalaxia));
                    }
                }
                case 2->{
                    if(id==0){
                        return new Estrela(daoEstrela.ultimoID(),nome, temperatura,tamanho);
                    }else {
                        return new Estrela(id,nome, temperatura,tamanho);
                    }
                }
                case 3->{
                    menuGalaxia(connection,daoGalaxia);
                }
            }
        }while (opcao<1 || opcao>3);
        throw new RuntimeException();
    }

    private static Galaxia encontraGalaxia(Connection connection, DAOGalaxia daoGalaxia) {
        do{
            int id = retornaIDEscolhidoGalaxia(daoGalaxia, connection);
            for (Galaxia galax:
                    daoGalaxia.realAll(connection, daoGalaxia)) {
                if(galax.getId() == id){
                    return galax;
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
                    [4] Editar uma Galaxia
                    [5] Voltar
                    >\t""");
            int escolha = sc.nextInt();
            switch (escolha){
                case 1->{
                    daoGalaxia.create(connection,cadastrarGalaxia(daoGalaxia, 0));
                }
                case 2->{
                    for (Galaxia galaxia:
                         daoGalaxia.realAll(connection, daoGalaxia)) {
                        System.out.println(galaxia.toString());
                    }
                }
                case 3 -> {
                    daoGalaxia.delete(connection, retornaIDEscolhidoGalaxia(daoGalaxia, connection));
                }
                case 4 -> {
                    daoGalaxia.editar(connection, cadastrarGalaxia(daoGalaxia, retornaIDEscolhidoGalaxia(daoGalaxia, connection)));
                }
                case 5->{
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
                    daoGalaxia.realAll(connection, daoGalaxia)) {
                if(galaxia.getId() == id){
                    return id;
                }
            }
        }while (true);
    }

    static int retornaIDEscolhidoEstrela(DAOEstrela daoEstrela, Connection connection, DAOGalaxia daoGalaxia){
        do{
            System.out.print("O id que deseja: ");
            int id = sc.nextInt();
            for (Estrela estrela:
                    daoEstrela.realAll(connection, daoGalaxia)) {
                if(estrela.getId() == id){
                    return id;
                }
            }
        }while (true);
    }

    private static Galaxia cadastrarGalaxia(DAOGalaxia daoGalaxia,int id) {
        int opcao = 0;
        String tipo = "";
        System.out.println("O nome da Galaxia: ");
        String nome = sc.next();
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
        if(id==0){
            return new Galaxia(daoGalaxia.ultimoID(),nome, tipo,possuiMateriaEscura);
        }else {
            return new Galaxia(id,nome, tipo,possuiMateriaEscura);
        }
    }
}
