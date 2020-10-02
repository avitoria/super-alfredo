package com.ipartek.formacion.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.Alerta;
import com.ipartek.formacion.modelo.dao.impl.CategoriaDAOImpl;

/**
 * Application Lifecycle Listener implementation class InicioAppListener
 *
 */
@WebListener
public class InicioAppListener implements ServletContextListener {

	private final static Logger LOG = Logger.getLogger(InicioAppListener.class);
	static private final CategoriaDAOImpl categoriaDAO = CategoriaDAOImpl.getInstance();

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// cuando paramos la App
		LOG.info("Apagando Servidor");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// cuando ejecutamos la App en el Servidor, al arrancar la 1º vez
		LOG.info("Estamos arrancado la App, y soy un evento");

		// Este contexto es para toda la Aplicacion y es accesible desde cualñquier JSP
		// o Servlet
		ServletContext contextoAplicacion = sce.getServletContext();

		try {
			contextoAplicacion.setAttribute("usuarios_conectados", 0);
			contextoAplicacion.setAttribute("categorias", categoriaDAO.getAll());

		} catch (Exception e) {
			LOG.fatal(e);
			contextoAplicacion.setAttribute("alerta", new Alerta("danger", "Tenemos un problema sin determinar"));
		}

	}

}
