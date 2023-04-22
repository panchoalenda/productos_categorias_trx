/*
* ESTA CLASE REPOSITORIO, SE VA A ENCARGAR DE TODO LO QUE TIENDE QUE VER CON LA BASE DE DATOS
* COMO CONSULTA Y OPERACIONES A LA BASE DE DATOS, UTILIZANDO UNA INTERFACE QUE SE DEBE IMPLEMENTAR, POR LO QUE TODO
* TODO REPOSITORIO DEBE TENER UN CRUD PARA CIERTA TABLA DE LA BBDD.
*/

package es.falenda.java.jdbc.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T> Listar() throws SQLException;
    T buscarPorId(Long id) throws SQLException;
    T guardarActualizar(T t) throws SQLException;
//    void modificar(T t); Utilizamo el metodo guardar para modificar tambien, consultando si el Id existe o no
    void eliminar(Long id) throws SQLException;
    void setCn(Connection cn);
}
