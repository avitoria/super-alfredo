package com.ipartek.formacion.modelo.pojo;

public class ResumenUsuario {

	private int idUsuario;
	private int aprobados;
	private int pendientes;

	public ResumenUsuario() {
		super();
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getAprobados() {
		return aprobados;
	}

	public void setAprobados(int aprobados) {
		this.aprobados = aprobados;
	}

	public int getPendientes() {
		return pendientes;
	}

	public void setPendientes(int pendientes) {
		this.pendientes = pendientes;
	}

	@Override
	public String toString() {
		return "ResumenUsuario [idUsuario=" + idUsuario + ", aprobados=" + aprobados + ", pendientes=" + pendientes
				+ "]";
	}

}
