import java.io.Serializable;

public class ObjectNum implements Serializable{
	
	String opcion;
	String numeroUno;
	String numeroDos;
	
	public ObjectNum(String opcion, String uno, String dos) {
		this.opcion = opcion;
		this.numeroUno = uno;
		this.numeroDos = dos;
	}

	
	
	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getNumeroUno() {
		return numeroUno;
	}

	public void setNumeroUno(String numeroUno) {
		this.numeroUno = numeroUno;
	}

	public String getNumeroDos() {
		return numeroDos;
	}

	public void setNumeroDos(String numeroDos) {
		this.numeroDos = numeroDos;
	}
	
	
}
