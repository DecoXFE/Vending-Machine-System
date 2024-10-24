package es.uva.inf.poo.VendingCo;

import java.util.ArrayList;

import fabricante.externo.tarjetas.TarjetaMonedero;

/**
 * Prueba VendingMachine
 * @author Pablo Bueno
 * @author Diego Valladolid
 *
 */
public class VendingMachine {
	private int identificador;
	private boolean operativa;
	private int[] lineId;
	private ArrayList<ArrayList<Product>> productos;
	private int cantidad;
	
	/**
	 * nicialización por defecto. 
	 * @param id
	 * 			ID de la máquina.
	 * @param noLineas
	 * 			Número de lineas de la máquina.
	 * @param noObjLinea
	 * 			Número máximo de objetos por linea.
	 * @throws IllegalArgumentException si el número de lineas es incorrecto.
	 */
	public VendingMachine(int id, int noLineas, int noObjLinea) {
		this.identificador = id;
		this.operativa = true;
		this.productos = new ArrayList<ArrayList<Product>>(noLineas);
		this.cantidad = noObjLinea;
		try {
			this.lineId = setLineas(noLineas);
		}
		catch (Exception idErr) {
			throw idErr;
		}
		
	}
	
	/**
	 * Devuelve el identificador de la máquina vending.
	 * @return	Identificador de la máquina vending.
	 */
	public int getId() {
		return this.identificador;
	}
	
	/**
	 * Verifica si la máquina tiene alguna linea vacia.
	 * @return	boolean: 
	 * true: hay alguna linea vacia,
	 * false: en caso contrario.
	 */
	public boolean lineaVacia() {
		boolean empty = false;
		for(ArrayList<Product> linea : this.productos) {
			if(linea.size() == 0) empty = true;
		}
		return empty;
	}
	
	/**
	 * Comprueba si la máquina está vacia.
	 * @return	true si está vacía, false en caso contrário.
	 */
	private boolean maquinaVacia() {
		boolean vacia = false;
		for(int i = 0; i<this.lineId.length; i++) {
			if(this.productos.get(i).size() == 0) vacia = true;
		}
		return vacia;
	}
	
	/**
	 * Comprueba si la linea dada está vacia
	 * @param i
	 * @return	false si no está vacia
	 * @throws SecurityException si la linea está vacía
	 */
	private boolean checkLineaVacia(int i) {
		if(this.productos.get(i).size() == 0) {
			throw new SecurityException("No hay productos en la linea");
		}
		else return false;
	}
	
	/**
	 * Establece el Número de lineas de la máquina.
	 * @param noLineas
	 * 				numero de lineas de la máquina.
	 * @return	un array con los id de linea.
	 * @throws IllegalAccessError if {@code noLineas<=0}
	 */
	private int[] setLineas(int noLineas) {
		int[] lineas;
		if(noLineas <= 0) {
			throw new IllegalArgumentException("Nº de lineas no válido");
		}
		else {
			lineas = new int[noLineas];
			for(int i = 0; i<noLineas; i++) {
				lineas[i] = i;
			}
		}
		return lineas;
	}
	
	/**
	 * Cambia el estado de la máquina a "operativo".
	 */
	public void turnOn() {
		if(this.operativa) {
			throw new SecurityException("Máquina ya operativa.");
		}
		else this.operativa = true;
	}
	
	/**
	 * Cambia el estado de la máquina a "no operativo".
	 */
	public void shutDown() {
		if(!this.operativa) {
			throw new SecurityException("Maquina ya en fuera de servicio.");
		}
		else this.operativa = false;
	}
	
	/**
	 * Devuelve un boolean con el estado actual de la máquina vending
	 * @return boolean: 
	 * true: Máquina operativa.
	 * false: Máquina fuera de servicio.
	 */
	public boolean isOperative() {
		return this.operativa;
	}
	/**
	 * Comprueba que el ID de linea sea correcto.
	 * @param lineId
	 * @return	true si no produce excepción
	 * @throws IllegalArgumentException si el ID no es correcto.
	 */
	private boolean checkId(int lineId) {
		if(lineId < 0 || lineId > this.lineId.length - 1) {
			throw new IllegalArgumentException("ID de linea incorrecto");	
		}
		else {
			return true;
		}
	}
	
	/**
	 * Compra un producto a partir de una tarjeta y un ID de linea.
	 * Al comprar un objeto este se descuenta de la máquina y se 
	 * elimina el saldo correspondiente de la tarjeta.
	 * @param card
	 * @param lineId
	 * @trhows SecurityException si la máquina no está operativa.
	 * @trhows SecurityException si la linea está vacia.
	 * @throws IllegalArgumentException si se produce excepción con la TarjetaMonedero.
	 */
	public void buyProduct(TarjetaMonedero card, int lineId) {
		if(!this.operativa) {
			throw new SecurityException("Maquina fuera de servicio");
		}
		else {
			for(int i = 0; i<this.lineId.length; i++) {
				if(lineId == this.lineId[i]) {
					try {
						if(!checkLineaVacia(i)) {
								this.productos.get(i).remove(0);
								card.descontarDelSaldo("6Z1y00Nm31aA-571", this.productos.get(i).get(0).getPrice());
							}
					}
					catch (IllegalArgumentException cardExc) {
						throw cardExc;
					}
					catch (SecurityException lineExc) {
						throw lineExc;
					}
				}
			}
		}
	}
	
	/**
	 * Consulta el precio de un producto a partir de su ID.
	 * @param lineId: 
	 * 				Identificador de linea.
	 * @throws SecurityException si la máquina no está operativa.
	 * @throws IndexOutOfBoundsException si la linea está vacia.
	 * @throws IllegalArgumentException si el ID de linea es incorrecto.
	 */
	public void consultarPrecio(int lineId) {
		try {
			if(checkId(lineId)) {
				if(!this.operativa) {
					throw new SecurityException("Máquina no operativa: operativa = false");
				}
				else {
					int i = 0;
					for(int id : this.lineId) {
						if(lineId == id) {
							try {
								if(!checkLineaVacia(id)) this.productos.get(i).get(0).getPrice();
							}
							catch (SecurityException lineExc) {
								throw lineExc;
							}
						}
						i++;
					}
				}
			}
		}
		catch (IllegalArgumentException idExc) {
			throw idExc;
		}
					
	}
	
	/**
	 * Añade un producto o cantidad de productos a la linea dada por el ID
	 * @param p
	 * 				Producto a introducir en la máquina
	 * @param lineId
	 * 				ID de la linea
	 * @param cantidad
	 * 				Cantidad de productos a introducir.
	 * @throws IllegalArgumentException si el ID de linea es incorrecto.	
	 * @throws SecurityException si se intenta introducir mas cantidad de la permitida.
	 * @throws SecurityException si el producto a introducir es distinto al de la linea.
	 */
	public void addProduct(Product p, int lineId, int cantidad) {
		if(cantidad <= 0) {
			throw new IllegalArgumentException("cantidad no válida.");
		}
		else {
			try {
				if(checkId(lineId)) {
					int i = 0;
					for(int id : this.lineId) {
						if(id == lineId) {
							if(this.productos.get(i).get(0).getID() != p.getID()) {
								throw new SecurityException("Productos distintos");
							}
							else if(cantidad + this.productos.get(i).size() > this.cantidad) {
								throw new SecurityException("Demasiados produtcos en la linea: ");
							}
							else for(int j = 0; j<cantidad; j++) this.productos.get(i).add(p);
						}
						i++;
					}
				}
			}
			catch (IllegalArgumentException idExc) {
				throw idExc;
			}			
		}
	}
	
	/**
	 * Reabastece una linea de un producto
	 * @param p
	 * 			producto a reabastecer
	 * @param lineId
	 * 			ID de linea
	 * @throws SecurityException si la linea está completa.
	 * @throws SecurityException si el produco a reabastecer es distinto.
	 */
	public void reabastecerLinea(Product p, int lineId) {
		try {
			if(checkId(lineId)) {
				int i = 0;
				for(int id : this.lineId) {
					if(id == lineId) {
						try {
							addProduct(p, lineId, (this.cantidad - this.productos.get(i).size()));
						}
						catch (IllegalArgumentException exc) {
							throw new SecurityException("Linea completa.");
						}
						catch (SecurityException secExc) {
							throw secExc;
						}
					}
					i++;
				}
			}
		}
		catch (IllegalArgumentException idExc) {
			throw idExc;
		}
				
	}
	
	/**
	 * Elimina todos los productos de una linea.
	 * @param lineId
	 * 				ID de linea.
	 * @param cantidad
	 * 				Numero de productos a quitar de la linea.
	 * @throws SecurityException si la cantidad a eliminar es superior
	 *  a la cantidad de objetos
	 *  
	 */
	public void removeProduct(int lineId) {
		try {
			if(checkId(lineId)) {
				this.productos.get(lineId).clear();
			}
		}
		catch (IllegalArgumentException idExc) {
			throw idExc;
		}
					
	}	
	/**
	 * Vacía la máquina de productos.
	 * @throws SecurityException si la máquina ya está vacia.
	 */
	public void vaciarMaquina() {
		if(maquinaVacia()) {
			throw new SecurityException("La maquina ya está vacia.");
		}
		for(ArrayList<Product> linea : this.productos) {
			linea.clear();
		}
	}

}
