package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
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
		// lettura URL
		String[] url = req.getRequestURI().substring(1).split("/");
		Integer id;
		try {
			id = Integer.parseInt(url[1]);
			// conversione foto
			resp.setContentType("image/jpeg");
			ServletOutputStream out;
			out = resp.getOutputStream();
			Fotografia f = dao.find(id);
			URL url_type = new URL(f.getUrl());
			BufferedImage img = ImageIO.read(url_type);
			File file = new File("tmp.jpg");
			ImageIO.write(img, "jpg", file);
			FileInputStream fin = new FileInputStream(file);

			BufferedInputStream bin = new BufferedInputStream(fin);
			BufferedOutputStream bout = new BufferedOutputStream(out);
			int ch = 0;
			;
			while ((ch = bin.read()) != -1) {
				bout.write(ch);
			}

			bin.close();
			fin.close();
			bout.close();
			out.close();
		} catch (NumberFormatException e) {
			resp.getWriter().write("Errore: foto non trovata");
		}
	}

}
