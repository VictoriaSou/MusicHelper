package br.com.musichelper.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String PASTA = System.getProperty("user.home").replace('\\', '/')
            + "/musichelper_db/musichelper";

    private static final String URL = "jdbc:h2:file:" + PASTA
            + ";MODE=MySQL;DATABASE_TO_LOWER=TRUE"
            + ";INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver H2 não encontrado: " + e.getMessage());
        }
    }

    private Conexao() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
