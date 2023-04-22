package es.falenda.java.jdbc.service;

import es.falenda.java.jdbc.models.Categoria;
import es.falenda.java.jdbc.models.Producto;

import java.sql.SQLException;
import java.util.List;

public interface Service {

    /*===================== PRODUCTO =====================*/
    List<Producto> listarProducto() throws SQLException;
    Producto porIdProducto(Long id) throws SQLException;
    Producto guardarProducto(Producto producto) throws SQLException;
    void eliminarProducto(Long id) throws SQLException;

    /*===================== CATEGORÍA =====================*/
    List<Categoria> listarCategoria() throws SQLException;
    Categoria porIdCategoria(Long id) throws SQLException;
    Categoria guardarCategoria(Categoria categoria) throws SQLException;

    /*===================== PRODUCTO-CATEGORÍA =====================*/
    void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException;

}
