package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.JsonNode;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class Usuario extends Model
{
	public Usuario() {	}
	
	public Usuario(JsonNode input) {
		super();
		
		this.nick = input.get("nick").asText();
		this.password = input.get("password").asText();
	}
	
	public Usuario(Document input) {
		super();
		
		this.nick = input.getElementsByTagName("nick").item(0).getTextContent();
		this.password = input.getElementsByTagName("password").item(0).getTextContent();
	}
	
	@Id
	public Long id;
	
	@Required
	public String nick;
	
	@Required
	public String password;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="usuario")
	public List<SerieUsuario> series = new ArrayList<SerieUsuario>();

	
	public static Finder<Long, Usuario> finder = new Finder<Long, Usuario>(Long.class, Usuario.class);  
}
