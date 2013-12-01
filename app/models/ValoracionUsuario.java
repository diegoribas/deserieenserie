package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.JsonNode;

import play.db.ebean.Model;

@Entity
public class ValoracionUsuario extends Model 
{
	public ValoracionUsuario() {}
	
	public ValoracionUsuario(JsonNode input)
	{
		super();

		this.valoracion = input.get("valoracion").asDouble();
	}
	
	@Id
	public Long id;
	
	public double valoracion;
	
	@ManyToOne
	public Usuario usuario; 
	
	@ManyToOne
	public Serie serie;
	
	public static Finder<Long, ValoracionUsuario> finder = new Finder<Long, ValoracionUsuario>(Long.class, ValoracionUsuario.class);  
	

	// obtener la valoracion de un usuario para una serie
	public static ValoracionUsuario findBySerieandUser(Long idUsuario, Long idSerie)
	{	
		ValoracionUsuario valoracion = finder.where().eq("usuario_id", idUsuario).eq("serie_id", idSerie).findUnique();
		
		return valoracion;
	}
}
