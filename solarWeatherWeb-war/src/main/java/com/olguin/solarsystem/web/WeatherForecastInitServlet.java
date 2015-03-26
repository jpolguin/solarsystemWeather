package com.olguin.solarsystem.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.datastore.Entity;
import com.olguin.solarsystem.model.ISolarSystem;
import com.olguin.solarsystem.model.SolarSystem;
import com.olguin.solarsystem.model.Weather;

public class WeatherForecastInitServlet extends HttpServlet {
	
	private static Logger _logger = Logger.getLogger(WeatherForecastInitServlet.class.getName());

	
	private static final long serialVersionUID = 193118762504868196L;

	private static final int DAYS_TO_FORECAST = 3600;
	
	
	@Override
	public void init() throws ServletException {
		Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
			  public void run() {
			    try {
			    	  calculateForecastVulcanoSystem(DAYS_TO_FORECAST);
			    } catch (Exception ex) {
			      throw new RuntimeException("Exception calculating forecast:", ex);
			    }
			  }
			});
			thread.start();
	    
		super.init();
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {
		  int dias =1;
		   resp.setContentType("application/json");
		   
		   String days = req.getParameter("dias");
		   if (days==null || days.equals("")) {
			 resp.sendError(512, "Se requiere  parametro obligatorio dia. Invocacion:  /generar_pronostico?dias=800  \n\n");
		   }
		   try {
		      dias = new Integer(days);
		      generateForecast(dias);
		      resp.getWriter().println("{ \"pronostico_generado\":" + dias +"}");
		   } catch (Exception e) {
			   resp.sendError(599, "Error generando pronostico. Exception: " + e.toString()+ " \n\n");
		   }
	
	}

	private void calculateForecastVulcanoSystem(int daysToForecast) {
		 List<Entity> existing = ForecastRepo.findForecast(1);
		  
		  if (existing.isEmpty()) { 
			 generateForecast(daysToForecast);
			 _logger.warning(daysToForecast +  " forecast days stored in Datastore.");
		  } else {
			  _logger.warning("Found existing forecast database, skipping forecast calculation...:");
		  }
	}


	private void generateForecast(int daysToForecast) {
		ISolarSystem solarSystem = SolarSystem.createVulcanoSystem();
		 
		 for(int day=0;day<=daysToForecast;day++) {
			 
			 Weather weather = solarSystem.weatherAtDay(day);
			 ForecastRepo.saveTheRecordInDataStore(day, weather);
			
		 }
	}


	

}
