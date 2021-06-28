package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

//import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;

@Table(name ="T_ACTUALIZACION_DB")
public class ActualizacionDB implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -991457763706443641L;

	@SerializedName("IdActualizacionDB")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Tipo")
	@Column(name="TIPO", defaultValue="SQL") 
	private String tipo;

	@SerializedName("Comando")
	@Column(name="COMANDO") 
	private String comando;
	
	@SerializedName("VersionPDA")
	@Column(name="VERSION_PDA") 
	private String versionPDA;

	//@Element
	@SerializedName("FechaRegistro")
	@Column(name="FECHA_REGISTRO",type ="INTEGER")// ANTES DATETIME
	private Date fechaRegistro;

	//@Element
	@SerializedName("FechaEjecucion")
	@Column(name="FECHA_EJECUCION",type ="INTEGER")// ANTES DATETIME
	private Date fechaEjecucion;

	@SerializedName("Realizado")
	@Column(name="REALIZADO", defaultValue="N") 
	private String realizado;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String nombre) {
		this.tipo = nombre;
	}
	
	
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return comando;
	}
	
	public ActualizacionDB(){}
	
	public ActualizacionDB(Integer id, String tipo, String comando, String versionPDA){
		super();
		this.id = id;
		this.tipo=tipo;
		this.comando=comando;
		this.realizado ="N";
		this.versionPDA = versionPDA;
		try
		{
		this.fechaRegistro = Tools.Today();
		}
		catch(Exception ex)
		{
			
		}
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
