package es.falenda.java.jdbc.repository;

import es.falenda.java.jdbc.models.Categoria;
import es.falenda.java.jdbc.models.Producto;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    public Connection cn;

    public ProductoRepositorioImpl() {

    }
    public ProductoRepositorioImpl(Connection cn) {
        this.cn = cn;
    }
    public void setCn(Connection cn) {
        this.cn = cn;
    }

    /*-------------------MÉTODO LISTAR---------------------*/
    @Override
    public List<Producto> Listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p" +
               " inner join categorias as c on (p.categoria_id=c.id)")) {

            while (rs.next()) {
                Producto p = crearProducto(rs);
                productos.add(p);

               /* TODO ESTO LO LLEVAMOS A UN MÉTODO QUE SE ENCUENTRA AL FINAL DE ESTA CLASE ("crearProducto")
               Producto p = new Producto(rs.getInt("id"), rs.getString("nombre"),
                  rs.getInt("precio"), rs.getDate("registro")); //guardo el producto que traigo
                // de la BBDD en "p".

                productos.add(p); //Agrgo a la lista el producto que traje de la BBDD y que lo guarde en "p".*/
            }

        }
        return productos;
    }

    /*-------------------MÉTODO BUSCAR POR ID---------------------*/
    @Override
    public Producto buscarPorId(Long id) throws SQLException {
        Producto producto = null;
        String sql = "SELECT p.*, c.nombre as categoria FROM productos as p " +
          "inner join categorias as c on (p.categoria_id=c.id) WHERE p.id=?";
        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery();) {
                while (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        }
        return producto;
    }


    /*-------------------MÉTODO GUARDAR / ACTUALIZAR---------------------*/
    @Override
    public Producto guardarActualizar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=?, sku=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos(nombre, precio, categoria_id, sku, registro) VALUES (?,?,?,?,?)";
        }

        try (PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, producto.getNombre());
            pst.setInt(2, producto.getPrecio());
            pst.setLong(3, producto.getCategoria().getId());
            pst.setString(4, producto.getSku());

            if (producto.getId() != null && producto.getId() > 0) {
                pst.setLong(5, producto.getId());
            } else {
                pst.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }

            pst.executeUpdate();

            if (producto.getId() == null) {  //Con esto le asignamos el último "ID" al producto
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setId(rs.getLong(1));
                    }
                }
            }
        }
        return producto;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";

        try (PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        }
    }

    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("registro"));
        p.setSku(rs.getString("sku"));

        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }
}
