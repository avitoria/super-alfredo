package com.ipartek.formacion.seguridad;

public class SeguridadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MENSAJE = "No tiene permisos para realizar esta operación.";

	public SeguridadException() {
		super(MENSAJE);
	}

}
