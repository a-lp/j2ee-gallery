package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FotografiaDAO;
import database.Fotografia;

public class FotoServlet extends HttpServlet {
	@Inject
	FotografiaDAO dao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String[] url = req.getRequestURI().substring(1).split("/");
			Integer id;
			if (url.length > 1) {
				id = Integer.parseInt(url[2]);
				// conversione foto
				Fotografia f = dao.find(id);
				if (f == null) {
					resp.getWriter().write("Errore: foto non trovata.");
				} else {
					resp.setContentType("image/jpeg");
					ServletOutputStream out;
					out = resp.getOutputStream();
					URL url_type = new URL(f.getUrl());
					BufferedInputStream bin = new BufferedInputStream(url_type.openStream());
					BufferedOutputStream bout = new BufferedOutputStream(out);
					int ch = 0;
					while ((ch = bin.read()) != -1) {
						bout.write(ch);
					}

					bin.close();
					// fin.close();
					bout.close();
					out.close();
				}
			}
		} catch (NumberFormatException e) {
			resp.getWriter().write("Errore: l'id inserito non e' di tipo numerico.\n");
		} catch (ArrayIndexOutOfBoundsException e) {
			resp.getWriter().write("Errore: non e' stato inserito nessun id.\n");
		}
	}

}
