package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_FECHAS_CONFIGURADAS")
public class FechasConfiguradas implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6309180467672948812L;

	@SerializedName("IdFecha")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=true,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Fecha")
	@Column(name="FECHA",type="INTEGER") 
	private Integer fecha;
	
	@SerializedName("FechaString")
	@Column(name="FECHA_STRING") 
	private String fechaString;
	
	@SerializedName("TipoFecha")
	@Column(name="TIPO") 
	private String tipo;
	
	@SerializedName("IdJuzgado")
	@Column(name="ID_JUZGADO",type="INTEGER") 
	private Integer idJuzgado;
	

	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION") 
	private Integer Descripcion;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return fechaString;
	}
	
	public FechasConfiguradas(){}
	
	public FechasConfiguradas(Integer id, String fechaString){
		super();
		this.id = id;
		this.fechaString = fechaString;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItemName(String item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
