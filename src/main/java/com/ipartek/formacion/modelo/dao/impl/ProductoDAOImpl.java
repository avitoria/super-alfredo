package com.ipartek.formacion.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.ConnectionManager;
import com.ipartek.formacion.modelo.dao.ProductoDAO;
import com.ipartek.formacion.modelo.pojo.Categoria;
import com.ipartek.formacion.modelo.pojo.Producto;
import com.ipartek.formacion.modelo.pojo.ResumenUsuario;
import com.ipartek.formacion.seguridad.SeguridadException;

public class ProductoDAOImpl implements ProductoDAO {

	private final static Logger LOG = Logger.getLogger(ProductoDAOImpl.class);
	private static ProductoDAOImpl INSTANCE = null;

	private ProductoDAOImpl() {
		super();
	}

	public static synchronized ProductoDAOImpl getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new ProductoDAOImpl();
		}

		return INSTANCE;
	}

	// excuteQuery => ResultSet
	private final String SQL_GET_ALL = "SELECT " + "	 p.id     'producto_id', " + "	 p.nombre 'producto_nombre', "
			+ "	 precio, " + "	 imagen, " + "	 c.id     'categoria_id', " + "	 c.nombre 'categoria_nombre'	"
			+ " FROM producto p , categoria c " + " WHERE p.id_categoria  = c.id AND fecha_validado IS NOT NULL "
			+ " ORDER BY p.id DESC LIMIT 500";

	private final String SQL_GET_LAST = "SELECT " + "	 p.id     'producto_id', " + "	 p.nombre 'producto_nombre', "
			+ "	 precio, " + "	 imagen, " + "	 c.id     'categoria_id', " + "	 c.nombre 'categoria_nombre'	"
			+ " FROM producto p , categoria c " + " WHERE p.id_categoria  = c.id AND fecha_validado IS NOT NULL "
			+ " ORDER BY p.id DESC LIMIT ?";

	private final String SQL_GET_BY_CATEGORIA = "SELECT " + "	 p.id     'producto_id', "
			+ "	 p.nombre 'producto_nombre', " + "	 precio, " + "	 imagen, " + "	 c.id     'categoria_id', "
			+ "	 c.nombre 'categoria_nombre'	" + " FROM producto p , categoria c "
			+ " WHERE p.id_categoria  = c.id AND fecha_validado IS NOT NULL " + " AND c.id = ? "
			+ " ORDER BY p.id DESC LIMIT ?";

	private final String SQL_GET_BY_USUARIO_PRODUCTO_VALIDADO = "SELECT 	 p.id     'producto_id', 	 p.nombre 'producto_nombre', 	 precio, 	 imagen, 	 c.id     'categoria_id', 	 c.nombre 'categoria_nombre'	 \n"
			+ "FROM producto p , categoria c  \n"
			+ "WHERE p.id_categoria  = c.id AND fecha_validado IS NOT NULL AND p.id_usuario = ? \n"
			+ "ORDER BY p.id DESC LIMIT 500";

	private final String SQL_GET_BY_USUARIO_PRODUCTO_SIN_VALIDAR = "SELECT 	 p.id     'producto_id', 	 p.nombre 'producto_nombre', 	 precio, 	 imagen, 	 c.id     'categoria_id', 	 c.nombre 'categoria_nombre'	 \n"
			+ "FROM producto p , categoria c  \n"
			+ "WHERE p.id_categoria  = c.id AND fecha_validado IS NULL AND p.id_usuario = ? \n"
			+ "ORDER BY p.id DESC LIMIT 500";

	private final String SQL_GET_BY_ID = "SELECT " + "	 p.id     'producto_id', " + "	 p.nombre 'producto_nombre', "
			+ "	 precio, " + "	 imagen, " + "	 c.id     'categoria_id', " + "	 c.nombre 'categoria_nombre'	"
			+ " FROM producto p , categoria c " + " WHERE p.id_categoria  = c.id AND p.id = ?";

	private final String SQL_GET_BY_ID_PRODUCTO_ID_USUARIO = "SELECT " + "	 p.id     'producto_id', "
			+ "	 p.nombre 'producto_nombre', " + "	 precio, " + "	 imagen, " + "	 c.id     'categoria_id', "
			+ "	 c.nombre 'categoria_nombre'	" + " FROM producto p , categoria c "
			+ " WHERE p.id_categoria  = c.id AND p.id = ? AND p.id_usuario = ?";

	private final String SQL_VIEW_RESUMEN_USUARIO = "SELECT id_usuario, aprobados, pendientes FROM v_usuario_productos WHERE id_usuario = ?";

	// excuteUpdate => int numero de filas afectadas
	private final String SQL_INSERT = "INSERT INTO producto (nombre, imagen, precio , id_usuario, id_categoria ) VALUES (?, ?, ?, ?, ?)";
	private final String SQL_UPDATE = "UPDATE producto SET nombre = ?, imagen = ?, precio = ?, id_categoria = ? WHERE id = ?";
	private final String SQL_UPDATE_BY_USER = "UPDATE producto SET nombre = ?, imagen = ?, precio = ?, id_categoria = ?, fecha_validado = NULL WHERE id = ?";
	private final String SQL_DELETE = "DELETE FROM producto WHERE id = ?";

	@Override
	public void validar(int id) {
		// TODO Auto-generated method stub
		// UPDATE producto SET fecha_validado = NOW() WHERE id = 15;
	}

	public ArrayList<Producto> getAllByNombre(String nombre) {
		return null;
	}

	@Override
	public ArrayList<Producto> getAll() {

		ArrayList<Producto> registros = new ArrayList<Producto>();

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();

		) {

			LOG.debug(pst);
			while (rs.next()) {

				registros.add(mapper(rs));

			} // while

		} catch (Exception e) {
			LOG.error(e);
		}

		return registros;
	}

	@Override
	public ArrayList<Producto> getAllByUser(int idUsuario, boolean isValidado) {
		ArrayList<Producto> registros = new ArrayList<Producto>();

		String sql = (isValidado) ? SQL_GET_BY_USUARIO_PRODUCTO_VALIDADO : SQL_GET_BY_USUARIO_PRODUCTO_SIN_VALIDAR;

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(sql);) {

			// TODO mirar como hacerlo con una SQL, "IS NOT NULL" o "IS NULL"
			// pst.setBoolean(1, isValidado); // me sustituye con un 1 o 0

			pst.setInt(1, idUsuario);

			LOG.debug(pst);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					registros.add(mapper(rs));
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return registros;
	}

	@Override
	public ArrayList<Producto> getLast(int numReg) {

		ArrayList<Producto> registros = new ArrayList<Producto>();

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_GET_LAST);) {
			pst.setInt(1, numReg);
			LOG.debug(pst);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					registros.add(mapper(rs));
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return registros;
	}

	@Override
	public ArrayList<Producto> getAllByCategoria(int idCategoria, int numReg) {
		ArrayList<Producto> registros = new ArrayList<Producto>();
		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_GET_BY_CATEGORIA);) {
			pst.setInt(1, idCategoria);
			pst.setInt(2, numReg);
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					registros.add(mapper(rs));
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		return registros;
	}

	@Override
	public Producto getById(int id) throws Exception {
		Producto p = new Producto();

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_GET_BY_ID);) {

			pst.setInt(1, id);
			LOG.debug(pst);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				p = mapper(rs);

			} else {
				throw new Exception("No ha podido encontrar producto con id " + id);
			}

		}

		return p;
	}

	public Producto checkSeguridad(int idProducto, int idUsuario) throws Exception, SeguridadException {
		Producto p = new Producto();

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_GET_BY_ID_PRODUCTO_ID_USUARIO);) {

			pst.setInt(1, idProducto);
			pst.setInt(2, idUsuario);
			LOG.debug(pst);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				p = mapper(rs);

			} else {
				throw new SeguridadException();
			}
		}

		return p;
	}

	@Override
	public Producto delete(int id) throws Exception, SeguridadException {

		Producto producto = getById(id);

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_DELETE);

		) {
			pst.setInt(1, id);
			LOG.debug(pst);

			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				throw new Exception("No se ha podido eliminar el producto " + id);
			}
		}

		return producto;
	}

	/*
	 * public Producto delete(int idProducto, int idUsuario) throws Exception {
	 * 
	 * Producto producto = null;
	 * 
	 * try (Connection conexion = ConnectionManager.getConnection();
	 * PreparedStatement pst = conexion.prepareStatement(SQL_DELETE);
	 * 
	 * ) { // Si no encuentra el producto para ese usuario, lanza una excepción y no
	 * // ejecuta la siguiente línea producto = checkSeguridad(idProducto,
	 * idUsuario);
	 * 
	 * pst.setInt(1, idProducto);
	 * 
	 * LOG.debug(pst); int affectedRows = pst.executeUpdate();
	 * 
	 * if (affectedRows != 1) { throw new
	 * Exception("No se ha podido eliminar el producto con id " + idProducto); } }
	 * 
	 * return producto; }
	 */

	@Override
	public Producto insert(Producto pojo) throws Exception {

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

		) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getImagen());
			pst.setFloat(3, pojo.getPrecio());
			pst.setInt(4, pojo.getUsuario().getId());
			pst.setInt(5, pojo.getCategoria().getId());
			LOG.debug(pst);

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {

				// conseguir el ID

				try (ResultSet rsKeys = pst.getGeneratedKeys()) {
					if (rsKeys.next()) {
						int id = rsKeys.getInt(1);
						pojo.setId(id);
					}
				}

			} else {
				throw new Exception("No se ha podido guardar el registro " + pojo);
			}
		}

		return pojo;
	}

	@Override
	public Producto update(Producto producto) throws Exception {

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_UPDATE);

		) {
			pst.setString(1, producto.getNombre());
			pst.setString(2, producto.getImagen());
			pst.setFloat(3, producto.getPrecio());
			pst.setInt(4, producto.getCategoria().getId());
			pst.setInt(5, producto.getId());
			LOG.debug(pst);

			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				throw new Exception("No se ha podido modificar el producto " + producto.getId());
			}

		}

		return producto;
	}

	@Override
	public Producto updateByUser(Producto producto) throws Exception, SeguridadException {
		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_UPDATE_BY_USER);

		) {
			pst.setString(1, producto.getNombre());
			pst.setString(2, producto.getImagen());
			pst.setFloat(3, producto.getPrecio());
			pst.setInt(4, producto.getCategoria().getId());
			pst.setInt(5, producto.getId());
			LOG.debug(pst);

			int affectedRows = pst.executeUpdate();

			if (affectedRows != 1) {
				throw new Exception("No se ha podidio modificar el producto " + producto.getId());
			}
		}

		return producto;
	}

	@Override
	public ArrayList<Producto> getAllRangoPrecio(int precioMinimo, int precioMaximo) throws Exception {
		throw new Exception("Sin implemntar");
	}

	private Producto mapper(ResultSet rs) throws SQLException {

		Producto p = new Producto();
		Categoria c = new Categoria();

		p.setId(rs.getInt("producto_id"));
		p.setNombre(rs.getString("producto_nombre"));
		p.setImagen(rs.getString("imagen"));
		p.setPrecio(rs.getFloat("precio"));

		c.setId(rs.getInt("categoria_id"));
		c.setNombre(rs.getString("categoria_nombre"));
		p.setCategoria(c);

		return p;
	}

	@Override
	public ResumenUsuario getResumenByUsuario(int idUsuario) {

		ResumenUsuario resumen = new ResumenUsuario();

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL_VIEW_RESUMEN_USUARIO);) {

			pst.setInt(1, idUsuario);
			LOG.debug(pst);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					// mapper de RS al POJO
					resumen.setIdUsuario(idUsuario);
					resumen.setAprobados(rs.getInt("aprobados"));
					resumen.setPendientes(rs.getInt("pendientes"));
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return resumen;
	}

}
