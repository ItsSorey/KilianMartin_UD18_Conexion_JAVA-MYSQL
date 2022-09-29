package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ej9_investigadores {
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
       		
       		String sql = "drop database IF EXISTS Investigadores;"; //Si existe la BD la borramos, ya que nos interesa importar toda la nueva
       		stmt.execute(sql); //ejecutamos y borramos
       		
       		BD="Investigadores";
       		sql = "create database "+BD+";"; //codigo MySQL para crear la BD
       		stmt.execute(sql); //ejecutamos y creamos
       		System.out.println("La Base de Datos "+ BD +" ha sido creada");
       		
       	}catch(SQLException ex) {
       		System.out.println(ex);
       	}
       	
       	try { //Añadimos las TABLAS+REGISTROS a la Base de Datos
       		System.out.println();
       		BD="Investigadores"; //nos posicionamos en la BD
       		conexion = connect(BD); //nos conectamos a la BD 
       		
       		stmt = conexion.createStatement();
       		
       		//TABLAS------------------------------
       		//creamos la tabla Facultad
       		String sql="create table IF NOT EXISTS Facultad (idFacultad INT AUTO_INCREMENT PRIMARY KEY NOT NULL, Nombre nvarchar(100));";
   			stmt.execute(sql); //ejecutamos APUNTE solo se puede ejecutar 1 por 1 y no todos a la vez
   			System.out.println("Tabla Facultad creada correctamente");
   			
   			//creamos la tabla Equipos
   			sql="create table IF NOT EXISTS Equipos (NumSerie char(4) PRIMARY KEY NOT NULL, Facultad int, Nombre nvarchar(100), FOREIGN KEY (Facultad) REFERENCES Facultad(idFacultad));";
   			stmt.execute(sql); //ejecutamos
   			System.out.println("Tabla Equipos creada correctamente");
   			
   			//creamos la tabla Investigadores
   			sql="create table IF NOT EXISTS Investigadores (DNI varchar(8) PRIMARY KEY NOT NULL, NomApels nvarchar(255), Facultad int, FOREIGN KEY (Facultad) REFERENCES Facultad(idFacultad));";
   			stmt.execute(sql); //ejecutamos
   			System.out.println("Tabla Investigadores creada correctamente");
   			
   			//creamos la tabla Reserva
   			sql="create table IF NOT EXISTS Reserva ( DNI varchar(8) NOT NULL, NumSerie char(4) NOT NULL, Comienzo datetime, Fin datetime, PRIMARY KEY (DNI,NumSerie), FOREIGN KEY (DNI) REFERENCES Investigadores(DNI), FOREIGN KEY (NumSerie) REFERENCES Equipos(NumSerie));";
   			stmt.execute(sql); //ejecutamos
   			System.out.println("Tabla Reserva creada correctamente");
   			
   			//REGISTROS-----------------------------
   			//Registros Facultad
   			sql="INSERT INTO facultad VALUES(1,'roviraIVirgil');";
   			stmt.execute(sql);
   			sql="INSERT INTO facultad VALUES(2,'barcelona');";
   			stmt.execute(sql);
   			sql="INSERT INTO facultad VALUES(3,'salamanca');";
   			stmt.execute(sql);
   			sql="INSERT INTO facultad VALUES(4,'sevilla');";
   			stmt.execute(sql);
   			sql="INSERT INTO facultad VALUES(5,'valencia');";
   			stmt.execute(sql);
   			
   			System.out.println("\nRegistros Facultad añadidos con exito");
   			
   			//Registros Equipos
   			sql="INSERT INTO equipos VALUES(1,5,'roviraIVirgil');";
   			stmt.execute(sql);
   			sql="INSERT INTO equipos VALUES(2,4,'barcelona');";
   			stmt.execute(sql);
   			sql="INSERT INTO equipos VALUES(3,3,'salamanca');";
   			stmt.execute(sql);
   			sql="INSERT INTO equipos VALUES(4,2,'sevilla');";
   			stmt.execute(sql);
   			sql="INSERT INTO equipos VALUES(5,1,'valencia');";
   			stmt.execute(sql);
   			
   			System.out.println("Registros Equipos añadidos con exito");
   			
   			//Registros Investigadores
   			sql="INSERT INTO investigadores VALUES(28091247,'roviraIVirgil',3);";
   			stmt.execute(sql);
   			sql="INSERT INTO investigadores VALUES(27068029,'barcelona',2);";
   			stmt.execute(sql);
   			sql="INSERT INTO investigadores VALUES(50068687,'salamanca',5);";
   			stmt.execute(sql);
   			sql="INSERT INTO investigadores VALUES(24166646,'sevilla',4);";
   			stmt.execute(sql);
   			sql="INSERT INTO investigadores VALUES(44641329,'valencia',1);";
   			stmt.execute(sql);
   			
   			System.out.println("Registros Investigadores añadidos con exito");
   			
   			//Registros Reserva
   			sql="INSERT INTO reserva VALUES(28091247,1,'2021-10-03','2021-11-12');";
   			stmt.execute(sql);
   			sql="INSERT INTO reserva VALUES(27068029,2,'2021-02-10','2021-12-18');";
   			stmt.execute(sql);
   			sql="INSERT INTO reserva VALUES(50068687,3,'2021-04-12','2021-12-10');";
   			stmt.execute(sql);
   			sql="INSERT INTO reserva VALUES(24166646,4,'2021-01-05','2021-12-06');";
   			stmt.execute(sql);
   			sql="INSERT INTO reserva VALUES(44641329,5,'2021-01-22','2021-08-01');";
   			stmt.execute(sql);
   			
   			System.out.println("Registros Reserva añadidos con exito\n");
   			
   			conexion = close(); //Cerramos la BD
       	}catch(SQLException ex) {
       		System.out.println(ex);
       	}
	}
}
