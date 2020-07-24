package com.ipartek.formacion.modelo.dao;

import java.util.ArrayList;

import com.ipartek.formacion.modelo.CrudAble;
import com.ipartek.formacion.modelo.pojo.Producto;
import com.ipartek.formacion.modelo.pojo.ResumenUsuario;
import com.ipartek.formacion.seguridad.SeguridadException;

/**
 * Hereda los metodos basicos de la interfaz CrudAble Ademas definie un nuevo:
 * ArrayList<Producto> getAllByNombre( String nombre )
 * 
 * @author javaee
 *
 */
public interface ProductoDAO extends CrudAble<Producto> {

	/**
	 * Validamos el producto para que sea visible en la parte publica
	 * 
	 * @param id identificador del producto
	 * 
	 */
	void validar(int id);

	/**
	 * 
	 * @param nombre
	 * @return
	 */
	ArrayList<Producto> getAllByNombre(String nombre);

	/**
	 * Obtiene todos los productos de un usuario, estos pueden estar validados o no
	 * 
	 * @param idUsuario  int identificador del usuario
	 * @param isValidado boolean true para mostrar los productos con
	 *                   fecha_validacion, false para mostrar los pendientes de
	 *                   validar
	 * @return
	 */
	ArrayList<Producto> getAllByUser(int idUsuario, boolean isValidado);

	/**
	 * Obtiene los ultimos registros ordenador por id descentente
	 * 
	 * @param numReg int numero de registros a recuperar
	 * @return ArrayList<Producto>
	 */
	ArrayList<Producto> getLast(int numReg);

	/**
	 * Obtienes los productos de una Categoria
	 * 
	 * @param idCategoria int identificador de la Categoria
	 * @param numReg      int numero de registgros a mostrar
	 * @return ArrayList<Producto>
	 */
	ArrayList<Producto> getAllByCategoria(int idCategoria, int numReg);

	/**
	 * 
	 * @param precioMinimo
	 * @param precioMaximo
	 * @return
	 * @throws Exception
	 */
	ArrayList<Producto> getAllRangoPrecio(int precioMinimo, int precioMaximo) throws Exception;

	/**
	 * 
	 * @param idUsuario
	 * @return
	 */
	ResumenUsuario getResumenByUsuario(int idUsuario);

	/**
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 * @throws SeguridadException
	 */
	Producto updateByUser(Producto p) throws Exception, SeguridadException;

}
