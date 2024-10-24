package es.uva.inf.poo.VendingCo;

import java.util.ArrayList;
import java.util.Date;


/**
 * Clase con los objetos que se pondrán a la venta en las máquinas vending.
 * @author Pablo Bueno
 * @author Diego Valladolid
 *
 */
public class Product {
	private int id;
	private int upc;
	private String nombre;
	private float precio;
	private Date fechaConsumo;
	
	/**
	 * Constructor de La clase con un ID dividido en 2 secciones
	 * @param id1:	ID del producto.
	 * @param nombre:	Nombre del producto.
	 * @param precio:	Precio del producto.
	 * @throws IllegalArgumentException cuando el ID, la fecha o el UPC son incorrectos.
	 * @throws IndexOutOfBoundsException cuando el tamaño del ID es incorrecto.
	 */
	
	public Product(int id, String nombre, float precio, Date fecha) {
		this.nombre = nombre;
		this.precio = precio;
		this.id = id;
		this.fechaConsumo = fecha;
		try {			
			if(!checkUpc(id)){
				throw new IllegalArgumentException("El ID introducido no es correcto");
			}
			
		}		
		catch (IndexOutOfBoundsException errLim) {
			throw errLim;
		}
		catch (IllegalArgumentException errArg) {
			throw errArg;
		}
	}
	
	
	/**
	 * Devuelve el precio del producto.
	 * @return Precio
	 */
	public float getPrice() {
		return this.precio;
	}
	
	public int getID() {
		return this.id;
	}
	
	/**
	 * Devuelve el nombre del producto.
	 * @return Nombre del producto.
	 */
	public String getName() {
		return this.nombre;
	}
	
	/**
	 * Devuelve la fecha de consumo preferente del producto.
	 * @return	Fecha de consumo preferente.
	 */
	public Date getFechaConsumo() {
		return this.fechaConsumo;
	}
	
	/**
	 * UPC del producto.
	 * @return upc del producto
	 */
	public int getUpc(){
		return this.upc;
	}
	
	/**
	 * Divide un numero en digitos y los añade a un ArrayList.
	 * @param n
	 * 			número a añadir.
	 * @return	Arraylist de enteros.
	 */
	private ArrayList<Integer> splitNumbers(int n) {
		ArrayList<Integer> split = new ArrayList<Integer>();
		while(n > 0) {
			split.add(0, (n % 10));
			n = n / 10;
		}
		return split;
	}
	
	/**
	 * Comprueba si el numero enviado es par.
	 * @param n: 
	 * número a verificar.
	 * @return boolean: 
	 * true(par), false(impar).
	 */
	private boolean esPar(int n) {
		boolean par = false;
		if((n % 2) == 0) par = true;
		return par;
	}
	
	/**
	 * Comprueba que el UPC del producto sea válido
	 * @param id
	 * 			ID del producto
	 * @return	True si el upc es correcto y False si no coincide.
	 * @throws IndexOutOfBoundsException cuando el tamaño del ID es diferente de 12.
	 */
	private boolean checkUpc(int id){
		int upc = 0;
		ArrayList<Integer> split = new ArrayList<Integer>();
		split.addAll(splitNumbers(id));
		if(split.size() != 12) {
			throw new IndexOutOfBoundsException("Tamaño de ID incorrecto: ");
		}
		else {
			int upcReal = split.get(11);
			int suma = getSuma(split);
			int m = getMultiplo(suma);
			upc = Math.abs(suma - m);
			return upcReal == upc;
		}
		
	}
	/**
	 * Calcula la suma "s" del código UPC
	 * @param split
	 * 				ArrayList de enteros.
	 * @return s
	 * 			suma del codigo upc.
	 */
	private int getSuma(ArrayList<Integer> split) {
		int par = 0;
		int impar = 0;
		int i = 0;
		for(int elem : split) {
			if(esPar(i)) par += elem;
			else impar += elem;
			i++;
		}
		return 3 * par + impar;
	}
	
	/**
	 * Calcula el múltiplo de 10 mas cercano a un número.
	 * @param suma
	 * 				número del que se obtiene su múltiplo de 10.
	 * @return Múltiplo de 10 mas cercano.
	 */
	private int getMultiplo(int suma) {
		int length = String.valueOf(suma).length() - 1;
		int multiplo = 1;
		for(int i = 0; i<length; i++) {
			multiplo = multiplo * 10;
		}
		return Math.round((float)suma / multiplo) * multiplo;
	}
	
}
