/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Conexion {

    Connection con;
    //variables para la coneccion
    String url = "jdbc:mysql://localhost:3306/db_Compras";
    String usr = "root";
    String pass = "";
//metod tipo Connection
    public Connection getConnection() {

        try {
            //llamamos al driver de mysql o pude ser oracle
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usr, pass);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error no conecta" + e);
        }

        return con;
    }

}
