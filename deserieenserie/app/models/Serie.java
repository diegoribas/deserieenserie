package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Serie extends Model
{
	
	@Id
	public Long id;
	
	@Required
	public String nombre;
	
	public String descripcion;
	
	public double valoracion;
	
	public String genero;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="serie")
	public List<SerieUsuario> seriesUsuario = new ArrayList<SerieUsuario>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="serie")
	public List<ValoracionUsuario> valoracionesUsuario = new ArrayList<ValoracionUsuario>();
	
	public static Finder<Long, Serie> finder = new Finder<Long, Serie>(Long.class, Serie.class);  
	
	// Devuelve las SerieUsuario de un usuario en concreto
	public static List<Serie> findByGenero(String genero)
	{	
		List<Serie> series = new ArrayList<Serie>();

		series = finder.where().eq("genero", genero).findList();
	
		return series;
	}  
	
}	
