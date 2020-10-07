package com.ipartek.formacion.modelo.pojo;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

public class Categoria {

	private int id;
	@NotEmpty(message = "Debes indicar el nombre de la categoría.")
	private String nombre;
	private ArrayList<Producto> productos;

	public Categoria() {
		super();
		this.id = 0;
		this.nombre = "";
		this.productos = new ArrayList<Producto>();
	}

	public Categoria(int id) {
		this();
		this.id = id;
	}

	public Categoria(int id, String nombre) {
		this();
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

}
