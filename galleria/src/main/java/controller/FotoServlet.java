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

/**
 * Classe per la gestione dei parametri richiesti dai client.
 * 
 * @author Armando La Placa
 *
 */
public class FotoServlet extends HttpServlet {
	@Inject
	FotografiaDAO dao;

	/**
	 * Metodo per la gestione delle richieste al path galleria/foto/{id}. Viene
	 * restituita l'immagine identificata dall'id passato nella URL. L'immagine
	 * viene ricercata nel database, quindi si crea uno stream di dati per la
	 * restituzione in output. Se l'immagine non è presente nel DB, viene mandato un
	 * messaggio di errore in output.
	 * 
	 * @exception NumberFormatException          L'id inserito non è di tipo
	 *                                           numerico.
	 * @exception ArrayIndexOutOfBoundsException Il path inserito nella URL non è
	 *                                           completo.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String[] url = req.getRequestURI().substring(1).split("/");
			Integer id;
			if (url.length > 1) {
				id = Integer.parseInt(url[2]);
				// ricerco la foto nel database.
				Fotografia f = dao.find(id);
				if (f == null) {
					resp.getWriter().write("Errore: foto non trovata.");
				} else {
					// genero l'immagine da mandare in output.
					resp.setContentType("image/jpeg");
					ServletOutputStream out;
					out = resp.getOutputStream();
					// l'immagine originale viene scaricata dall'URL contenuto nel rispettivo campo.
					URL url_type = new URL(f.getUrl());
					BufferedInputStream bin = new BufferedInputStream(url_type.openStream());
					BufferedOutputStream bout = new BufferedOutputStream(out);
					int ch = 0;
					while ((ch = bin.read()) != -1) {
						bout.write(ch);
					}

					bin.close();
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
