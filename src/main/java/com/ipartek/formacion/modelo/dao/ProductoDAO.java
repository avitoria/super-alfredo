/**
 * Hereda los métodos de la interfaz CrudAble y añade el método getAllByNombre
 * 
 * @author alfredo
 * @version 1.0
 */

package com.ipartek.formacion.modelo.dao;

import java.util.ArrayList;

import com.ipartek.formacion.modelo.CrudAble;
import com.ipartek.formacion.modelo.pojo.Producto;
import com.ipartek.formacion.modelo.pojo.ResumenUsuario;
import com.ipartek.formacion.seguridad.SeguridadException;

public interface ProductoDAO extends CrudAble<Producto> {

	/**
	 * Valida el producto para que sea visible en la parte pública
	 * 
	 * @param id id del producto
	 * 
	 */
	void validar(int id) throws Exception;

	/**
	 * Obtiene todos los productos que cuyo nombre contiene nombre
	 * 
	 * @param nombre nombre del producto
	 * @return {@code ArrayList<Producto>}
	 */
	ArrayList<Producto> getAllByNombre(String nombre);

	/**
	 * Obtiene todos los productos de un usuario
	 * 
	 * @param idUsuario  int identificador del usuario
	 * @param isValidado boolean true para mostrar los productos validados, false
	 *                   para mostrar productos pendientes de validar
	 * @return {@code ArrayList<Producto>}
	 */
	ArrayList<Producto> getAllByUser(int idUsuario, boolean isValidado);

	/**
	 * Obtiene los x últimos registros ordenados por id
	 * 
	 * @param numReg int numero de registros a mostrar
	 * @return {@code ArrayList<Producto>}
	 */
	ArrayList<Producto> getLast(int numReg);

	/**
	 * Obtiene los productos de una categoria
	 * 
	 * @param idCategoria int id de la categoria
	 * @param numReg      int número de productos a mostrar
	 * @return {@code ArrayList<Producto>}
	 */
	ArrayList<Producto> getAllByCategoria(int idCategoria, int numReg);

	/**
	 * Obtiene el resumen de un usuario, con el número de productos validados y
	 * pendientes de validar
	 * 
	 * @param idUsuario int id del usuario cuyo resumen queremos obtener
	 * @return ResumenUsuario resumen del usuario buscado
	 * 
	 * @see com.ipartek.formacion.modelo.pojo.ResumenUsuario
	 */
	ResumenUsuario getResumenByUsuario(int idUsuario);

	/**
	 * Actualiza un producto por parte de un usuario. El producto queda pendiente de
	 * validación (fecha_validado = NULL).
	 * 
	 * @param p Producto producto que queremos actualizar
	 * @return Producto producto actualizado
	 * @throws Exception          si no se encuentra el producto
	 * @throws SeguridadException si el producto no pertenece al Usuario
	 */
	Producto updateByUser(Producto p) throws Exception, SeguridadException;

}
