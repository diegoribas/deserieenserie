package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.JsonNode;


import play.db.ebean.Model;


@Entity
public class SerieUsuario extends Model 
{
	public SerieUsuario() {}
	
	public SerieUsuario(JsonNode input)
	{
		super();

		this.fuente = input.get("fuente").asText();
		this.temporada = input.get("temporada").asInt();
		this.capitulo = input.get("capitulo").asInt();
	}
		
	
	@Id
	public Long id;
		
	@ManyToOne
	public Usuario usuario; 
	
	@ManyToOne
	public Serie serie;
	
	public String fuente;
	
	public int temporada;
	
	public int capitulo;
	
	public static Finder<Long, SerieUsuario> finder = new Finder<Long, SerieUsuario>(Long.class, SerieUsuario.class);  
	
	// Devuelve las SerieUsuario de un usuario en concreto
	public static List<SerieUsuario> findByUser(Long idUsuario)//, int pag)
	{	
		List<SerieUsuario> seriesUsuario = new ArrayList<SerieUsuario>();

		seriesUsuario = finder.where().eq("usuario_id", idUsuario).findList();
	
		return seriesUsuario;
	}  
	

}  
