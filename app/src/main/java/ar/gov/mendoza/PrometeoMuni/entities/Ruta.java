package ar.gov.mendoza.PrometeoMuni.entities;
import java.util.ArrayList;
import java.util.List;

import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;
public class Ruta implements ISeleccionable<String>  
{
	    private Integer _id;
	    private String _name;
	    private int _idTipoRuta;
	    
	    /**
	     * @return the ID
	     */
	    public Integer getId() {
	        return _id;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setId(Integer id) {
	        this._id = id;
	    }

	    /**
	     * @return the ID
	     */
	    public String getName() {
	        return _name;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setName(String name) {
	        this._name= name;
	    }
	    
	    /**
	     * @return the ID
	     */
	    public int getIdTipoRuta() {
	        return _idTipoRuta;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setIdTipoRuta(int idTipoRuta) {
	        this._idTipoRuta = idTipoRuta;
	    }

	    
	    @Override
		public String getItemId() {
			
	    	return _id.toString();
		}

		@Override
		public String getItemName() {
			return _name;
		}
	    
	    static public List<Ruta> lstRutas = new  ArrayList<Ruta>();
	    
	    @Override
	    public String toString() {
	        return this.getName();            
	    }
	    public Ruta(int pId,String pName,int pIdTipoRuta)
	    {
	    	_id = pId;
	    	_name =pName;
	    	_idTipoRuta = pIdTipoRuta;
	    	
	    }
	    
	    static public void InitializeCollection()
	    {
	    	if (lstRutas.size()>0) return;
	    	
	    	lstRutas.add(new Ruta(1,"1",2));
	    	lstRutas.add(new Ruta(2,"5",2));
	    	lstRutas.add(new Ruta(3,"6",2));
	    	lstRutas.add(new Ruta(7,"10",2));
	    	lstRutas.add(new Ruta(8,"11",2));
	    	lstRutas.add(new Ruta(9,"13",2));
	    	lstRutas.add(new Ruta(10,"14",2));
	    	lstRutas.add(new Ruta(11,"15",2));
	    	lstRutas.add(new Ruta(12,"17",2));
	    	lstRutas.add(new Ruta(19,"E-88",2));
	    	lstRutas.add(new Ruta(23,"A-156",2));
	    	lstRutas.add(new Ruta(24,"A-73",2));
	    	lstRutas.add(new Ruta(25,"C45",2));
	    	lstRutas.add(new Ruta(26,"E-34",2));
	    	lstRutas.add(new Ruta(27,"2",2));
	    	lstRutas.add(new Ruta(28,"E-53",2));
	    	lstRutas.add(new Ruta(29,"E-55",2));
	    	lstRutas.add(new Ruta(30,"E-57",2));
	    	lstRutas.add(new Ruta(32,"3",2));
	    	lstRutas.add(new Ruta(33,"21",2));
	    	lstRutas.add(new Ruta(34,"S-253",2));
	    	lstRutas.add(new Ruta(35,"16",2));
	    	lstRutas.add(new Ruta(36,"30",2));
	    	lstRutas.add(new Ruta(37,"4",2));
	    	lstRutas.add(new Ruta(38,"A-196",2));
	    	lstRutas.add(new Ruta(39,"12",2));
	    
	    	lstRutas.add(new Ruta(4,"7",1));
			lstRutas.add(new Ruta(5,"8",1));
			lstRutas.add(new Ruta(6,"9",1));
			lstRutas.add(new Ruta(13,"19",1));
			lstRutas.add(new Ruta(14,"20",1));
			lstRutas.add(new Ruta(15,"35",1));
			lstRutas.add(new Ruta(16,"36",1));
			lstRutas.add(new Ruta(17,"38",1));
			lstRutas.add(new Ruta(18,"60",1));
			lstRutas.add(new Ruta(20,"148",1));
			lstRutas.add(new Ruta(21,"158",1));
			lstRutas.add(new Ruta(22,"1V09",1));
			lstRutas.add(new Ruta(31,"A019",1));
			
	    }
	    
	   static public List<Ruta> All(){
			   InitializeCollection();
		   
		   return lstRutas;
	   }
	   
	   static public List<Ruta> GetByTipoRuta(int pIdTipoRuta)
	   {
			   InitializeCollection();
		   /**
	        ** Creating Filter Logic
	        **/
	        Filter<Ruta, Integer> filter = new Filter<Ruta,Integer>() {
	            public boolean isMatched(Ruta object, Integer entero) {
	                return object.getIdTipoRuta() == entero;
	            }
	        };
		/**
		* Pass filter to filter the original list
		*/
	        List<Ruta> sortEmpList = new FilterList().filterList(lstRutas, filter,pIdTipoRuta);
	       return sortEmpList;
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
