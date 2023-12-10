//María Lacoba Martinez
package juego;

import java.io.Serializable;

public class Jugador implements Serializable{
	
	/**
	 * 
	 */
	//Un jugador tiene 3 atributos: su posicion, su nombre y su turno
	private int posicion;
	private String nombre;
	private int turno;
	
	//constructor con parametros donde se le asigna la posicion inicial (0), su nombre y su turno
	public Jugador(String n, int i) {
		
		this.posicion =0;
		this.nombre= n;
		this.turno=i;
	}
	//funcion que asigna la posicion del jugador
	public void setPosicion(int p) {
		this.posicion=p;
	}
	//funcion que devuelve la posicion del jugador
	public int getPosicion() {
		return this.posicion;
	}
	//funcion que devuelve el nombre del jugador
	public String getNombre() {
		return this.nombre;
	}
	//funcion que asigna el nombre a un jugador
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	//funcion que asigna el turno a un jugador
	public void setTurno(int turno) {
		this.turno=turno;
	}
	//funcion que devuelve el turno del jugador
	public int getTurno() {
		return this.turno;
	}
	//funcion que devuelve una string con los datos del jugador
	public String toString() {
		String s= "Nombre del jugador "+ nombre;
		s+= " posicion " + posicion;
		s+= " turno " + turno;
		return s;
	}
}