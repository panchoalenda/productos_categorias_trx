package es.falenda.java.jdbc;

import es.falenda.java.jdbc.models.Categoria;
import es.falenda.java.jdbc.models.Producto;
import es.falenda.java.jdbc.repository.CategoriaRepositorioImpl;
import es.falenda.java.jdbc.repository.ProductoRepositorioImpl;
import es.falenda.java.jdbc.repository.Repositorio;
import es.falenda.java.jdbc.service.Service;
import es.falenda.java.jdbc.service.ServiceImpl;
import es.falenda.java.jdbc.util.ConeccionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {
        Service servicio = new ServiceImpl();

        /*================================== CATEGORÍA =====================================*/
        System.out.println("-----------CREAR CATEGORIA--------------");
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre("Iluminación");

        /*================================== PRODUCTO =====================================*/
        System.out.println("-----------LISTAR PRODUCTOS--------------");
        servicio.listarProducto().forEach(System.out::println);

     /*           System.out.println("-----------BUSCAR POR ID--------------");
                System.out.println(repositorio.buscarPorId(4L));*/

        System.out.println("------------AGREGAR PRODUCTO-------------");
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Lámára");
        nuevoProducto.setPrecio(321);
        nuevoProducto.setFechaRegistro(new Date());
        nuevoProducto.setSku("abc123a");
        servicio.guardarProductoConCategoria(nuevoProducto,nuevaCategoria);

        System.out.println("Producto guardada con éxito: " + nuevoProducto.getId());
        servicio.listarProducto().forEach(System.out::println);

           /*     System.out.println("------------ACTUALIZO PRODUCTO-------------");
                Producto actualizoProducto = new Producto();
                actualizoProducto.setId(4L);
                actualizoProducto.setNombre("Teclado Raizen");
                actualizoProducto.setPrecio(250);
                nuevoProducto.setSku("ababa1234");

                Categoria categoria2 = new Categoria();
                categoria2.setId(3L);
                categoria.setNombre("Electrónica"); // Lo mismo que en el anterior, no es necesario agregarlo
                actualizoProducto.setCategoria(categoria2);

                repositorio.guardarActualizar(actualizoProducto);
                repositorio.Listar().forEach(System.out::println);

                System.out.println("------------ELIMINAR PRODUCTO-------------");
                repositorio.eliminar(13L);
                repositorio.Listar().forEach(System.out::println);*/

        /*================================== COMMIT =====================================*/


    }
}

