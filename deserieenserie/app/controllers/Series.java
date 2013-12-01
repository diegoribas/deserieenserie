package controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Serie;
import models.Usuario;
import models.ValoracionUsuario;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

public class Series extends Controller
{
	// METODOS PARA SERIES
	
	// devuelve una lista con todas las series. Se puede consultar en json y xml
	public static Result getSeries() 
	{
		List<Serie> series = new ArrayList<Serie>();
		
		series = Serie.finder.all();
		
		// if request accepts json
		if (request().accepts("application/json"))
		{			
			response().setContentType("application/json");
			//return json
			return ok(views.txt.series.render(series));
		}
		else	
		{
			if (request().accepts("text/xml"))
			{
				response().setContentType("application/xml");
				return ok(views.xml.series.render(series));
			}
		}
	
		return ok(); 
	}
	
	// devuelve una serie a traves del id que se pasa en el path. Se puede consultar en json y xml
	public static Result getSerie(Long id)
	{
		Serie serie = Serie.finder.byId(id);

		// si no se ha encontrado la serie
		if (serie == null) 
		{
			return badRequest(Messages.get("error"));
		}
		else
		{
			if (request().accepts("application/json"))
			{
				response().setContentType("application/json");
				return ok(views.txt._serie.render(serie));
			}
			else	
			{
				// si acepta xml enviamos la informacion en xml
				if (request().accepts("text/xml"))
				{
					//return xml
					response().setContentType("application/xml");
					return ok(views.xml._serie.render(serie));
				}
			}						
		}
		
		
		return badRequest(Messages.get("error"));
	}
	
	// METODOS PARA VALORACIONUSUARIO

	// añade la valoracion de un usuario a una serie
	public static Result addValoracionUsuario(Long idUsuario, Long idSerie)
	{
		ValoracionUsuario valoracionUsuario = getValoracionUsuarioFromBody();
		
		// si la informacion del nuevo usuario no es correcta
		if (valoracionUsuario == null) 
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
			valoracionUsuario.usuario = Usuario.finder.byId(idUsuario);
			valoracionUsuario.serie = Serie.finder.byId(idSerie);
			
			valoracionUsuario.save();
			
			// actualizamos la valoracion global de la serie
			obtenerValoracion(idSerie);
			
			return ok();
		}
	}
	
	// devuelve la valoracion de un usuario para una serie
	public static Result getValoracionUsuario(Long idUsuario, Long idSerie)
	{
		ValoracionUsuario valoracionUsuario = ValoracionUsuario.findBySerieandUser(idUsuario, idSerie);

		// si no se ha encontrado la serie
		if (valoracionUsuario == null) 
		{
			return badRequest(Messages.get("error"));
		}
		else
		{
			if (request().accepts("application/json"))
			{
				response().setContentType("application/json");
				return ok(views.txt._valoracionUsuario.render(valoracionUsuario));
			}
			else	
			{/*
				// si acepta xml enviamos la informacion en xml
				if (request().accepts("text/xml"))
				{
					//return xml
					response().setContentType("application/xml");
					return ok(views.xml._serie.render(serie));
				}  */
				
				return badRequest();
			}						
		} 		
	}  
		
	
	// hace la media de todas las valoraciones y la actualiza en el campo valoracion de la correspondiente serie
	private static void obtenerValoracion(Long idSerie)
	{
		double suma = 0;
		int cont = 0;
		
		Serie serie = Serie.finder.byId(idSerie);
		
		List<ValoracionUsuario> valoraciones = serie.valoracionesUsuario;
		
		for (ValoracionUsuario val: valoraciones)
		{
			suma += val.valoracion;
			cont++;
		}
		
		serie.valoracion = suma/cont;
		serie.update();
	}	
	
	
	// Devuelve la ValoracionUsuario que se pasa en la peticion. Acepta json
	private static ValoracionUsuario getValoracionUsuarioFromBody() 
	{
		ValoracionUsuario valoracionUsuario;
		
		if (request().getHeader("Content-Type").startsWith("application/json"))
		{
			JsonNode input = request().body().asJson();
			valoracionUsuario = new ValoracionUsuario(input);
		}
		/*
		else if (request().getHeader("Content-Type").startsWith("application/xml")) {
			Document input = request().body().asXml();
			usuario = new Usuario(input);
		}  */
		else {
			valoracionUsuario = null;
		} 
		
		return valoracionUsuario;
	}	
	
	// Devuelve todas las series de un genero
	
	public static Result getSeriesGenero(String genero)
	{
		List<Serie> series = new ArrayList<Serie>();
		
		series = Serie.findByGenero(genero);

		// si no se ha encontrado la serie
		if (series == null) 
		{
			return badRequest(Messages.get("error"));
		}
		else
		{
			if (request().accepts("application/json"))
			{
				response().setContentType("application/json");
				return ok(views.txt.series.render(series));
			}
			else	
			{
				// si acepta xml enviamos la informacion en xml
				if (request().accepts("text/xml"))
				{
					//return xml
					response().setContentType("application/xml");
					return ok(views.xml.series.render(series));
				}
			}	
		}
		
		
		
		return badRequest(Messages.get("error"));
	}

}
