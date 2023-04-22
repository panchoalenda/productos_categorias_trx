/*
* ESTA CLASE SE ENCARGA DE LA CONEXIÓN A LA BASE DE DATOS
* */
package es.falenda.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ConeccionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso?serveTimezone=Europe/Madrid";
    private static String username = "root";
    private static String password = "Pakoal42";
    private static BasicDataSource pool;


    public static BasicDataSource getInstance() throws SQLException {  //Este es un Singleton
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);

            /*CONFIGURACIONES DEL POOL INICIAL*/
            pool.setInitialSize(3); //Comienza con 3 conexiones abiertas (habilitadas)
            pool.setMinIdle(3); //Mínimo de conexiones que están inactivas (Que no se están utilizando)
            pool.setMaxIdle(8); //Máximo de conexiones que están inactivas (Que no se están utilizando)
            pool.setMaxTotal(8); //Es la suma de las activas e inactivas que no se estén utilizando
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException { //Con esto pido una conexión al pool
        return getInstance().getConnection();
    }
}
