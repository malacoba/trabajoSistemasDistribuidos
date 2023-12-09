package juego;

import java.io.*;

public class InformacionJugador {
	private String nombre;
	private DataOutputStream outSocket;
	private DataInputStream inSocket;
	
	public InformacionJugador(String nombre,DataOutputStream outSocket,DataInputStream inSocket ) {
		
		this.outSocket=outSocket;
		this.inSocket=inSocket;
		this.nombre=nombre;
		
	}
	public String getNombre() {
		return this.nombre;
	}
	
	public DataOutputStream getOutput() {
		return this.outSocket;
	}
	public DataInputStream getInput() {
		return this.inSocket;
	}


}
