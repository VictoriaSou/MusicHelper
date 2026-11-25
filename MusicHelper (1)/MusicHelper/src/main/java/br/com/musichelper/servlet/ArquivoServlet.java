package br.com.musichelper.servlet;

import br.com.musichelper.util.Uploads;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Entrega os arquivos enviados (fotos de perfil e anexos), que ficam fora da pasta web. */
@WebServlet("/arquivo/*")
public class ArquivoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String info = req.getPathInfo(); // ex.: /musicas/musica_1_abc.pdf
        if (info == null || info.length() < 2) {
            resp.sendError(404);
            return;
        }
        Path base = Uploads.base().toAbsolutePath().normalize();
        Path arquivo = base.resolve(info.substring(1)).normalize();
        if (!arquivo.startsWith(base) || !Files.isRegularFile(arquivo)) {
            resp.sendError(404);
            return;
        }
        String tipo = getServletContext().getMimeType(arquivo.getFileName().toString());
        resp.setContentType(tipo != null ? tipo : "application/octet-stream");
        resp.setContentLengthLong(Files.size(arquivo));
        Files.copy(arquivo, resp.getOutputStream());
    }
}
