package es.falenda.java.jdbc.repository;

import es.falenda.java.jdbc.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl implements Repositorio<Categoria> {
    private Connection cn;

    public CategoriaRepositorioImpl() {
    }
    public CategoriaRepositorioImpl(Connection cn) {
        this.cn = cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    @Override
    public List<Categoria> Listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();

        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM categorias")) {

            while (rs.next()) {
                categorias.add(crearCategoria(rs));
            }
        }
        return categorias;
    }

    @Override
    public Categoria buscarPorId(Long id) throws SQLException {
        Categoria categoria = new Categoria();
        try (PreparedStatement pst = cn.prepareStatement("SELECT * FROM categorias as c WHERE c.id=?")) {
            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    categoria = crearCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public Categoria guardarActualizar(Categoria categoria) throws SQLException {
        String sql = null;
        if (categoria.getId() != null && categoria.getId() > 0) {
            sql = "UPDATE categorias SET nombre=? WHERE id=?";
        } else {
            sql = "INSERT INTO categorias (nombre) VALUES(?)";
        }

        try (PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, categoria.getNombre());

            if (categoria.getId() != null && categoria.getId() > 0) {
                pst.setLong(2, categoria.getId());
            }
            pst.executeUpdate();

           /*LAS SIGUIENTES LINEAS NOS PERMITEN GUARDAR EL ID QUE SE GENERO EN
            EL "pst.executeUpdate()" DE ARRIBA*/
            if (categoria.getId() == null) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setId(rs.getLong(1));
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement pst = cn.prepareStatement("DELETE FROM categorias WHERE id=?")) {
            pst.setLong(1, id);
            pst.executeUpdate();
        }
    }

    private static Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getLong("1"));
        c.setNombre(rs.getString(2));
        return c;
    }
}
