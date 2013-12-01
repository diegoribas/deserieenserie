package controllers;


import java.util.List;

import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.JsonNode;

import models.Serie;
import models.SerieUsuario;
import models.Usuario;
import play.i18n.Messages;
import play.mvc.*;


public class Usuarios extends Controller
{
	// METODOS PARA USUARIO
	
	// devuelve todos los usuarios. Se puede consultar en json y xml
	public static Result getUsers() 
	{		
		List<Usuario> usuarios = Usuario.finder.all();
		
		// si acepta json enviamos la informacion en json
		if (request().accepts("application/json"))
		{	
			response().setContentType("application/json");
			//return json
			return ok(views.txt.usuarios.render(usuarios));
		}
		else	
		{
			// si acepta xml enviamos la informacion en xml
			if (request().accepts("text/xml"))
			{
				//return xml
				response().setContentType("application/xml");
				return ok(views.xml.usuarios.render(usuarios));
			}
		}
		
		
		return ok();
	}
	
	// devuelve un unico usuario a traves del id que se pasa en el path
	public static Result getUser(Long id)
	{
		Usuario usuario = Usuario.finder.byId(id);

		// si no se ha encontrado el usuario
		if (usuario == null) 
		{
			return badRequest(Messages.get("error"));
		}
		else
		{
			if (request().accepts("application/json"))
			{
				response().setContentType("application/json");
				return ok(views.txt._usuario.render(usuario));
			}
			else	
			{
				// si acepta xml enviamos la informacion en xml
				if (request().accepts("text/xml"))
				{
					//return xml
					response().setContentType("application/xml");
					return ok(views.xml._usuario.render(usuario));
				}
			}						
		}
		
		
		return badRequest(Messages.get("error"));
	}
	
	
	// añade el usuario recuperado del body
	public static Result addUser()
	{ 
		Usuario usuario = getUsuarioFromBody();
		
		// si la informacion del nuevo usuario no es correcta
		if (usuario == null) 
		{
			return badRequest(Messages.get("error"));
		}
		else {
		//	List<String> errors = usuario.validateAndSave();
			/*
			if (errors.size() == 0) {
				response().setHeader(LOCATION, routes.Usuarios.retrieve(usuario.id).absoluteURL(request()));
			} 
			else {
				return badRequest();
			}  */
			usuario.save();
			
			return ok();
		}
	}
	
	
	// Devuelve el usuario que se pasa en la peticion. Acepta json y xml
	private static Usuario getUsuarioFromBody() 
	{
		Usuario usuario;
		
		if (request().getHeader("Content-Type").startsWith("application/json"))
		{
			JsonNode input = request().body().asJson();
			usuario = new Usuario(input);
		}
		
		else if (request().getHeader("Content-Type").startsWith("application/xml")) {
			Document input = request().body().asXml();
			usuario = new Usuario(input);
		}  
		else {
			usuario = null;
		} 
		
		return usuario;
	}
	
	// METODOS PARA SERIEUSUARIO
	
	// Devuelve una lista de las series que sigue un usuario en json
	public static Result getSeriesUsuario(Long idUsuario)
	{
		List<SerieUsuario> seriesUsuario = SerieUsuario.findByUser(idUsuario);
				
		if (request().accepts("application/json"))
		{			
			response().setContentType("application/json");
			//return json
			return ok(views.txt.seriesUsuario.render(seriesUsuario));
		}
		
		return ok();
	}
	
	// Añade una serie que sigue un usuario. Acepta json
	public static Result addSerieUsuario(Long idUsuario, Long idSerie)
	{
		SerieUsuario serieUsuario = getSerieUsuarioFromBody();
		if (serieUsuario == null) {
			System.out.println("mal");
			return badRequest();
		}
		else {
		//	List<String> errors = usuario.validateAndSave();
			/*
			if (errors.size() == 0) {
				response().setHeader(LOCATION, routes.Usuarios.retrieve(usuario.id).absoluteURL(request()));
			} 
			else {
				return badRequest();
			}  */
			System.out.println("insertado" + serieUsuario.fuente + " " + serieUsuario.temporada + " " + serieUsuario.capitulo);
			
			serieUsuario.usuario = Usuario.finder.byId(idUsuario);
			serieUsuario.serie = Serie.finder.byId(idSerie);
			serieUsuario.save();
		}
		
		return ok();
	}
	
	
	// Devuelve la SerieUsuario que se pasa en la peticion. Acepta json y xml
	private static SerieUsuario getSerieUsuarioFromBody() 
	{
		SerieUsuario serieUsuario;
		
		if (request().getHeader("Content-Type").startsWith("application/json"))
		{
			JsonNode input = request().body().asJson();
			serieUsuario = new SerieUsuario(input);
		}
		
	/*	else if (request().getHeader("Content-Type").startsWith("application/xml")) {
			Document input = request().body().asXml();
			serieUsuario = new Usuario(input);
		}   */
		else {
			serieUsuario = null;
		} 
		
		return serieUsuario;
	} 
	
}
