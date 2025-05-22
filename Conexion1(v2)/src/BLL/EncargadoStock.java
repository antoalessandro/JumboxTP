package BLL;

public class EncargadoStock extends Usuario {

    

	public EncargadoStock(int id, String nombre, String email, String tipo, String password) {
		super(id, nombre, email, tipo, password);
	}

	@Override
	public String toString() {
		return "EncargadoStock [toString()=" + super.toString() + "]";
	}

    

 
}
