package br.com.musichelper.util;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Uploads {

    private Uploads() {
    }

    public static Path base() {
        String custom = System.getProperty("musichelper.uploads");
        return custom != null
                ? Paths.get(custom)
                : Paths.get(System.getProperty("user.home"), "musichelper_uploads");
    }

    public static boolean temArquivo(Part p) {
        return p != null && p.getSize() > 0
                && p.getSubmittedFileName() != null && !p.getSubmittedFileName().isEmpty();
    }

    public static String extensao(Part p) {
        String nome = p.getSubmittedFileName();
        int i = nome == null ? -1 : nome.lastIndexOf('.');
        return i < 0 ? "" : nome.substring(i + 1).toLowerCase();
    }

    /** Salva o arquivo e devolve o caminho relativo (ex.: "musicas/musica_1_abc.pdf"). */
    public static String salvar(Part p, String subpasta, String prefixo, int usuarioId) throws IOException {
        Path dir = base().resolve(subpasta);
        Files.createDirectories(dir);
        String nome = prefixo + "_" + usuarioId + "_" + UUID.randomUUID().toString().replace("-", "")
                + "." + extensao(p);
        try (InputStream in = p.getInputStream()) {
            Files.copy(in, dir.resolve(nome));
        }
        return subpasta + "/" + nome;
    }
}
