package com.olguin.solarsystem.web;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.olguin.solarsystem.model.ISolarSystem;
import com.olguin.solarsystem.model.SolarSystem;
import com.olguin.solarsystem.model.WeatherForecast;
import com.olguin.solarsystem.model.WeatherForecastReport;

public class SolarWeatherServlet extends HttpServlet{

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5544760010927716522L;
	

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {
		  int dia =-1;
		   resp.setContentType("application/json");
		
		   String day = req.getParameter("dia");
		   if (day==null || day.equals("")) {
			 resp.sendError(512, "Se requiere  parametro obligatorio dia. Invocacion:  /clima2?dia=80  \n\n");
		   }
		   try {
		       dia = new Integer(day);
		   } catch (Exception e) {
			   resp.sendError(516, "Error leyendo parametro obligatorio dia. Invocacion:  /clima2?dia=80  \n\n");
		   }
		   if (dia!= -1) {
			   List<Entity> forecast = ForecastRepo.findForecast(dia);
			
			   if (forecast.size()<1) {
				   resp.sendError(514, "Dia de pronostico meterologico no encontrado:" + dia);
			   } else {
				  Entity forecastday = forecast.get(0);
			      String weather=(String) forecastday.getProperty(ForecastRepo.CLIMA_FIELD_IDENTIFIER);
			      resp.getWriter().println("{ \"dia\":" + dia + ", \"clima\":" + weather+"}");
			   }
		   }

    }

	
}
