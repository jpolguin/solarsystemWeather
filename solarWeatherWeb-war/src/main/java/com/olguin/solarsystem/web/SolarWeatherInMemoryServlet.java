package com.olguin.solarsystem.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.olguin.solarsystem.model.ISolarSystem;
import com.olguin.solarsystem.model.SolarSystem;
import com.olguin.solarsystem.model.Weather;

public class SolarWeatherInMemoryServlet extends HttpServlet {

	
	private static final long serialVersionUID = -7793614576759950569L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {
		  int dia =-1;
		   resp.setContentType("application/json");
		
		   String day = req.getParameter("dia");
		   if (day==null || day.equals("")) {
			 resp.sendError(512, "Se requiere  parametro obligatorio dia. Invocacion:  /clima?dia=80  \n\n");
		   }
		   try {
		       dia = new Integer(day);
		   } catch (Exception e) {
			   resp.sendError(516, "Error leyendo parametro obligatorio dia. Invocacion:  /clima?dia=80  \n\n");
		   }
		   if (dia!= -1) {
			   ISolarSystem solarSystem = SolarSystem.createVulcanoSystem();
	
			   Weather weather = solarSystem.weatherAtDay(dia);
				
			   String weatherStr= weather.toClima();
			   resp.getWriter().println("{ \"dia\":" + dia + ", \"clima\":" + weatherStr+"}");
		   }

  }
}
