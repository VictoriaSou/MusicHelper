package br.com.musichelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Janela do sistema: um navegador embutido (WebView) mostrando as telas
 * servidas pelo Tomcat interno. Nenhum navegador externo é aberto.
 */
public class App extends Application {

    private final ServidorEmbutido servidor = new ServidorEmbutido();

    @Override
    public void init() throws Exception {
        // Roda antes de a janela abrir: sobe o Tomcat e o banco H2
        servidor.iniciar();
    }

    @Override
    public void start(Stage stage) {
        WebView web = new WebView();
        WebEngine engine = web.getEngine();
        String origem = "http://127.0.0.1:" + servidor.getPorta() + "/";

        // O WebView não mostra alert() nem confirm() sozinho; sem isto, confirm() devolve "não"
        engine.setOnAlert(e -> new Alert(Alert.AlertType.INFORMATION, e.getData()).showAndWait());
        engine.setConfirmHandler(msg -> new Alert(Alert.AlertType.CONFIRMATION, msg)
                .showAndWait().filter(b -> b == ButtonType.OK).isPresent());

        // Links para sites externos abrem no navegador do sistema,
        // e a janela do programa continua na tela em que estava
        engine.locationProperty().addListener((obs, antiga, nova) -> {
            if (nova != null && nova.startsWith("http") && !nova.startsWith(origem)) {
                Platform.runLater(() -> {
                    getHostServices().showDocument(nova);
                    if (antiga != null) {
                        engine.load(antiga);
                    }
                });
            }
        });

        engine.load(servidor.getUrl());

        stage.setTitle("MusicHelper");
        stage.setScene(new Scene(web, 1280, 800));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        servidor.parar();
        System.exit(0);
    }
}
