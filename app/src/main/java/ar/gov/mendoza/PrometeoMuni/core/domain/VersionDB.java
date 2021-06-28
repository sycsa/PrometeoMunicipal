package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

//import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;

@Table(name ="T_VERSION_DB")
public class VersionDB implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8621760610098747043L;

	@SerializedName("IdVersionDB")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=true,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("OldVersion")
	@Column(name="OLD_VERSION") 
	private Integer oldVersion;

	@SerializedName("NewVersion")
	@Column(name="NEW_VERSION") 
	private Integer newVersion;
	
	//@Element
	@SerializedName("Comentarios")
	@Column(name="COMENTARIOS")
	private String comentarios;

	@SerializedName("VersionPDA")
	@Column(name="VERSION_PDA") 
	private String versionPDA;

	//@Element
	@SerializedName("FechaRegistro")
	@Column(name="FECHA_REGISTRO",type ="INTEGER")// ANTES DATETIME
	private Date fechaRegistro;


	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




	public Integer getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(Integer oldVersion) {
		this.oldVersion = oldVersion;
	}

	public Integer getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(Integer newVersion) {
		this.newVersion = newVersion;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getVersionPDA() {
		return versionPDA;
	}

	public void setVersionPDA(String versionPDA) {
		this.versionPDA = versionPDA;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}	
	
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return newVersion.toString();
	}
	
	public VersionDB(){}
	
	public VersionDB(Integer oldVersion, Integer newVersion, String comentarios, String versionPDA){
		super();
		this.oldVersion=oldVersion;
		this.newVersion=newVersion;
		this.comentarios = comentarios;
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
