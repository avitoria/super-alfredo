package com.ipartek.formacion.modelo.dao;

import java.util.ArrayList;

import com.ipartek.formacion.modelo.CrudAble;
import com.ipartek.formacion.modelo.pojo.Usuario;

public interface UsuarioDAO extends CrudAble<Usuario> {

	/**
	 * Devuelve un listado de todos los usuarios con sus roles (sin productos)
	 * 
	 * @return {@code ArrayList<Usuario>} si no encuentra nada, devuelve un
	 *         ArrayList vacío
	 */
	ArrayList<Usuario> getAll();

	/**
	 * Busca un usuario por su id (sin productos)
	 * 
	 * @param id int id del usuario que buscamos
	 * @return objeto Usuario correspondiente al usuario buscado
	 * @throws Exception si no encuentra ningún usuario con el id indicado
	 */
	Usuario getById(int id) throws Exception;

	/**
	 * Devuelve los usaurios con un nombre similar al indicado
	 * 
	 * @param palabraBuscada String nombre que queremos buscar
	 * @return registros ArrayList<Usuario> ArrayList de objetos Usuario con los
	 *         usuarios encontrados
	 */
	ArrayList<Usuario> getAllByNombre(String palabraBuscada);

	/**
	 * Comprueba si el nombre de usuario existe en la BDD
	 * 
	 * @param nombre String nombre de usuario
	 * @return true si el nombre existe; false si no existe
	 */
	boolean getByName(String nombre);

	/**
	 * Comprueba si una combinación usuario/password existe en la BDD
	 * 
	 * @param nombre   String nombre del usuario
	 * @param password String password del usaurio
	 * @return Usuario el usuario en caso de la combinación exista
	 * @throws Exception si la combinación no existe
	 */
	Usuario existe(String nombre, String password);

	/**
	 * Inserta un nuevo usuario
	 * 
	 * @param pojo Usuario usuario a insertar
	 * @return pojo Usuario usuario insertado con el id autogenerado
	 * @throws Exception si no se ha insertado el usuario
	 * 
	 */
	Usuario insert(Usuario pojo) throws Exception;

	/**
	 * Elimina el usuario por su id
	 * 
	 * @param id int id del usuario que quieremos eliminar
	 * @return objeto Usuario correspondiente al usuario eliminado
	 * @throws Exception si no encuentra el usuario con el id indicado
	 */
	Usuario delete(int id) throws Exception;

	/**
	 * Actualiza los datos de un usuario
	 * 
	 * @param pojo Usuario usuario a actualizar
	 * @return pojo Usuario usuario actualizado
	 * @throws Exception si no existe el usuario a actualizar
	 * 
	 */
	Usuario update(Usuario pojo) throws Exception;
}
