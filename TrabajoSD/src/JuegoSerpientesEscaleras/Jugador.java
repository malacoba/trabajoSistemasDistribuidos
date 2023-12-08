package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class Jugador implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int posicion;
	private String nombre;
	private int turno;
	
	public Jugador(String n, int i) {
		
		this.posicion =0;
		this.nombre= n;
		this.turno=i;
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
	public void setTurno(int turno) {
		this.turno=turno;
	}
	public int getTurno() {
		return this.turno;
	}
	
}
