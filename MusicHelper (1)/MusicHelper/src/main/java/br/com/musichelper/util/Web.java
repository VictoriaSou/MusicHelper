package br.com.musichelper.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Web {

    private Web() {
    }

    public static int usuarioId(HttpServletRequest req) {
        return (Integer) req.getSession().getAttribute("usuarioId");
    }

    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    public static int toInt(String s, int padrao) {
        try {
            return Integer.parseInt(trim(s));
        } catch (NumberFormatException e) {
            return padrao;
        }
    }

    // ---- Mensagens flash (sobrevivem a um redirect) ----

    public static void sucesso(HttpServletRequest req, String msg) {
        req.getSession().setAttribute("flashSucesso", msg);
    }

    public static void erro(HttpServletRequest req, String msg) {
        req.getSession().setAttribute("flashErro", msg);
    }

    /** Move as mensagens flash da sessão para a request, para serem exibidas no JSP. */
    public static void carregarFlash(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null) {
            return;
        }
        for (String chave : new String[]{"flashSucesso", "flashErro"}) {
            Object v = s.getAttribute(chave);
            if (v != null) {
                req.setAttribute(chave.equals("flashSucesso") ? "mensagemSucesso" : "mensagemErro", v);
                s.removeAttribute(chave);
            }
        }
    }
}
