package es.falenda.java.jdbc.service;

import es.falenda.java.jdbc.models.Categoria;
import es.falenda.java.jdbc.models.Producto;
import es.falenda.java.jdbc.repository.CategoriaRepositorioImpl;
import es.falenda.java.jdbc.repository.ProductoRepositorioImpl;
import es.falenda.java.jdbc.repository.Repositorio;
import es.falenda.java.jdbc.util.ConeccionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceImpl implements Service {
    Repositorio<Producto>  productoRepositorio;
    Repositorio<Categoria> categoriaRepositorio;

    public ServiceImpl() {
        this.productoRepositorio = new ProductoRepositorioImpl();
        this.categoriaRepositorio = new CategoriaRepositorioImpl();
    }

    /*========================= PRODUCTO=========================*/
    @Override
    public List<Producto> listarProducto() throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {  //EN TODOS LOS MÈTODOS DE PRODUCTO CREO UNA CONEXION
            productoRepositorio.setCn(cn);
            return productoRepositorio.Listar();
        }
    }

    @Override
    public Producto porIdProducto(Long id) throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            productoRepositorio.setCn(cn);
            return productoRepositorio.buscarPorId(id);
        }
    }

    @Override
    public Producto guardarProducto(Producto producto) throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            productoRepositorio.setCn(cn);
            if (cn.getAutoCommit()) {
                cn.setAutoCommit(false);
            }
            Producto nuevoProducto = null;
            try {
                nuevoProducto = productoRepositorio.guardarActualizar(producto);
                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                e.printStackTrace();
            }
            return nuevoProducto;
        }
    }

    @Override
    public void eliminarProducto(Long id) throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            productoRepositorio.setCn(cn);
            if (cn.getAutoCommit()) {
                cn.setAutoCommit(false);
            }
            try {
                productoRepositorio.eliminar(id);
                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                e.printStackTrace();
            }
        }
    }


    /*========================= CATEGORÍA=========================*/
    @Override
    public List<Categoria> listarCategoria() throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            categoriaRepositorio.setCn(cn);
            return categoriaRepositorio.Listar();
        }
    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            categoriaRepositorio.setCn(cn);
            return categoriaRepositorio.buscarPorId(id);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            categoriaRepositorio.setCn(cn);

            if (cn.getAutoCommit()) {
                cn.setAutoCommit(false);
            }

            Categoria nuevaCategoria = null;
            try {
                nuevaCategoria = categoriaRepositorio.guardarActualizar(categoria);
                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                e.printStackTrace();
            }
            return nuevaCategoria;
        }
    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {

        try (Connection cn = ConeccionBaseDatos.getConnection();) {
            productoRepositorio.setCn(cn);
            categoriaRepositorio.setCn(cn);

            if (cn.getAutoCommit()) {
                cn.setAutoCommit(false);
            }

            try {
                Categoria nuevaCategoria = categoriaRepositorio.guardarActualizar(categoria);
                producto.setCategoria(nuevaCategoria);
                productoRepositorio.guardarActualizar(producto);
                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                e.printStackTrace();
            }
        }
    }
}
