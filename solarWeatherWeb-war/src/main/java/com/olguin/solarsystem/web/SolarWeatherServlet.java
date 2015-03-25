package com.olguin.solarsystem.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		     resp.setContentType("text/plain");
		    //  resp.getWriter().println("Hello, this is a testing servlet. \n\n");
		    //  Properties p = System.getProperties();
		    //  p.list(resp.getWriter());
		
		   String day = req.getParameter("dia");
		   if (day==null || day.equals("")) {
			resp.getWriter().println("Se requiere el parametro obligatorio dia. Invocacion:  /clima?dia=80  \n\n");
		   }
		   int dia = new Integer(day);
		   
		   ISolarSystem solarSystem = SolarSystem.createVulcanoSystem();
		  
		   resp.getWriter().println("{ \"dia\":" + dia + ", \"clima\":" + solarSystem.weatherAtDay(dia).toClima()+"}");
		   

    }
}
