package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class Jugador implements Serializable{
	
	private int posicion;
	private String nombre;
	private int numTurno;
	
	public Jugador(String n, int t) {
		
		this.posicion =0;
		this.nombre= n;
		this.numTurno = t;
	}
	public void setPosicion(int p) {
		this.posicion=p;
	}
	public int getPosicion() {
		return this.posicion;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
}
