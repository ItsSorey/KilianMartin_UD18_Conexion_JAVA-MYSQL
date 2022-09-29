package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ej6_piezasProveedores {
	final static String DB_URL = "jdbc:mysql://localhost:3306/"; //url de la BD mas el puerto
	final static String USERNAME = "root"; //usuario
	final static String PASSWORD = ""; //contraseña
	
	static Connection conexion = null; //establecemos conexion a nulo para impedir errores
	static Statement stmt = null;  //establecemos stmt a nulo para impedir errores
	
	public static Connection connect(String BD) { //funcion para establecer conexion a la BD
	    try {
	    	conexion = DriverManager.getConnection(DB_URL+BD, USERNAME, PASSWORD);
	    	System.out.println("Conectado a la BD"); 
	    	
	         
	    }catch(SQLException ex) {
	    	System.out.println("No se ha podido conectar con la base de datos"); 
	    	System.out.println(ex);
	    }
	    return conexion;
	}
	
	public static Connection close() { //funcion para cerrar conexion a la BD
		if (conexion!=null) {
			try {
		    	conexion.close();
		    	System.out.println("BD cerrada correctamente"); 
		    	
		    }catch(SQLException ex) {
		    	System.out.println("Ha ocurrido un error al cerrar la BD"); 
		    	System.out.println(ex);
		    }
		}
	    return conexion;
	}
	
	public static void main (String args[]) {
		String BD; //nombre de la Base de Datos
		
       	try { //Creamos la Base de Datos
       		BD=""; // es igual a nada porque vamos a aplicar una nueva BD a la raiz
       		conexion = connect(BD); //nos conectamos a la BD 
       		
       		stmt = conexion.createStatement();
       		
       		String sql = " drop database IF EXISTS Piezas_proveedores;"; //Si existe la BD la borramos, ya que nos interesa importar toda la nueva
       		stmt.execute(sql); //ejecutamos y borramos
       		
       		BD="Piezas_proveedores";
       		sql = "create database "+BD+";"; //codigo MySQL para crear la BD
       		stmt.execute(sql); //ejecutamos y creamos
       		System.out.println("La Base de Datos "+ BD +" ha sido creada");
       		
       	}catch(SQLException ex) {
       		System.out.println(ex);
       	}
       	
       	try { //Añadimos las TABLAS+REGISTROS a la Base de Datos
       		System.out.println();
       		BD="Piezas_proveedores"; //Nos posicionamos en la BD
       		conexion = connect(BD); //nos conectamos a la BD 
       		
       		stmt = conexion.createStatement();
       		
       		//TABLAS------------------------------
       		//creamos la tabla Piezas
       		String sql="create table IF NOT EXISTS Piezas (idPieza INT AUTO_INCREMENT PRIMARY KEY NOT NULL, Nombre nvarchar(100));";
   			stmt.execute(sql); //ejecutamos APUNTE solo se puede ejecutar 1 por 1 y no todos a la vez
   			System.out.println("Tabla Piezas creada correctamente");
   			
   			//creamos la tabla Proveedores
   			sql="create table IF NOT EXISTS Proveedores (idPorveedor char(4) PRIMARY KEY NOT NULL, Nombre nvarchar(100));";
   			stmt.execute(sql); //ejecutamos
   			System.out.println("Tabla Proveedores creada correctamente");
   			
   			//creamos la tabla Suministra
   			sql="create table IF NOT EXISTS Suministra (idPieza int, idPorveedor char(4), Precio int, PRIMARY KEY (idPieza,idPorveedor), FOREIGN KEY (idPieza) REFERENCES Piezas(idPieza), FOREIGN KEY (idPorveedor) REFERENCES Proveedores(idPorveedor));";
   			stmt.execute(sql); //ejecutamos
   			System.out.println("Tabla Suministra creada correctamente");
   			
   			//REGISTROS-----------------------------
   			//Registros Piezas
   			sql="INSERT INTO piezas VALUES(1,'rueda');";
   			stmt.execute(sql);
   			sql="INSERT INTO piezas VALUES(2,'panel');";
   			stmt.execute(sql);
   			sql="INSERT INTO piezas VALUES(3,'pantalla');";
   			stmt.execute(sql);
   			sql="INSERT INTO piezas VALUES(4,'cpu');";
   			stmt.execute(sql);
   			sql="INSERT INTO piezas VALUES(5,'hdmi');";
   			stmt.execute(sql);
   			
   			System.out.println("\nRegistros Piezas añadidos con exito");
   			
   			//Registros Proveedores
   			sql="INSERT INTO proveedores VALUES(1,'intel');";
   			stmt.execute(sql);
   			sql="INSERT INTO proveedores VALUES(2,'amd');";
   			stmt.execute(sql);
   			sql="INSERT INTO proveedores VALUES(3,'amazon');";
   			stmt.execute(sql);
   			sql="INSERT INTO proveedores VALUES(4,'pchouse');";
   			stmt.execute(sql);
   			sql="INSERT INTO proveedores VALUES(5,'beep');";
   			stmt.execute(sql);
   			
   			System.out.println("Registros Proveedores añadidos con exito");
   			
   			//Registros Suministra
   			sql="INSERT INTO suministra VALUES(1,3,'80');";
   			stmt.execute(sql);
   			sql="INSERT INTO suministra VALUES(2,2,'30');";
   			stmt.execute(sql);
   			sql="INSERT INTO suministra VALUES(3,5,'50');";
   			stmt.execute(sql);
   			sql="INSERT INTO suministra VALUES(4,1,'20');";
   			stmt.execute(sql);
   			sql="INSERT INTO suministra VALUES(5,1,'100');";
   			stmt.execute(sql);
   			
   			System.out.println("Registros Suministra añadidos con exito\n");
   			
   			conexion = close();	//Cerramos la BD
       	}catch(SQLException ex) {
       		System.out.println(ex);
       	}
	}
}