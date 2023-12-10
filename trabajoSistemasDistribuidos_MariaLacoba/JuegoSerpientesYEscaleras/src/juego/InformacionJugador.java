//María Lacoba Martinez
package juego;

import java.io.*;

public class InformacionJugador {
	//Informacion del jugador tiene 3 atributos: su nombre, su input y su output
	private String nombre;
	private DataOutputStream outSocket;
	private DataInputStream inSocket;
	//constructor con parámetros para construir una nueva informacion del jugador
	public InformacionJugador(String nombre,DataOutputStream outSocket,DataInputStream inSocket ) {
		
		this.outSocket=outSocket;
		this.inSocket=inSocket;
		this.nombre=nombre;
		
	}
	//funcion que devuelve el nombre del jugador
	public String getNombre() {
		return this.nombre;
	}
	//funcion que devuelve el outputStream del jugador
	public DataOutputStream getOutput() {
		return this.outSocket;
	}
	//funcion que devuelve el inputStream del jugador
	public DataInputStream getInput() {
		return this.inSocket;
	}


}
