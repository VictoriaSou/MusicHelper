package br.com.musichelper;

import org.apache.catalina.loader.WebappLoader;
import java.io.File;
import java.net.ServerSocket;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * Sobe um Tomcat dentro do próprio programa, numa porta livre e só acessível
 * a partir deste computador (127.0.0.1). Serve os JSPs de src/main/webapp e
 * as classes compiladas em target/classes.
 */
public class ServidorEmbutido {

    private static final String CONTEXTO = "/MusicHelper";

    private Tomcat tomcat;
    private int porta;

    public void iniciar() throws Exception {
        // Descobre uma porta livre (evita conflito com algo já usando a 8080)
        try (ServerSocket s = new ServerSocket(0)) {
            porta = s.getLocalPort();
        }

        // O NetBeans executa a partir da pasta do projeto, então estes caminhos funcionam
        File base = new File("target/tomcat").getAbsoluteFile();
        File webapp = new File("src/main/webapp").getAbsoluteFile();
        File classes = new File("target/classes").getAbsoluteFile();

        tomcat = new Tomcat();
        tomcat.setBaseDir(base.getPath());
        tomcat.setPort(porta);
        tomcat.getHost().setAutoDeploy(false);

        Connector conector = tomcat.getConnector();
        conector.setProperty("address", "127.0.0.1");

        Context ctx = tomcat.addWebapp(CONTEXTO, webapp.getPath());

        WebappLoader loader = new WebappLoader();
        loader.setDelegate(true);
        ctx.setLoader(loader);
        // Faz o Tomcat enxergar as classes compiladas (servlets, filtro, DAOs) como WEB-INF/classes
        WebResourceRoot recursos = new StandardRoot(ctx);
        recursos.addPreResources(new DirResourceSet(recursos, "/WEB-INF/classes", classes.getPath(), "/"));
        ctx.setResources(recursos);

        tomcat.start();
    }

    public int getPorta() {
        return porta;
    }

    public String getUrl() {
        return "http://127.0.0.1:" + porta + CONTEXTO + "/";
    }

    public void parar() throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
        }
    }
}
