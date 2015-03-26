package com.olguin.solarsystem.web;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.olguin.solarsystem.model.Weather;

public class ForecastRepo {
	
	public static final String FORECAST_ENTITY = "Forecast";
	public static final String FORECAST_DAY_KEY = "ForecastDay";
	public static final String DIA_FIELD_IDENTIFIER = "dia_field";
	public static final String CLIMA_FIELD_IDENTIFIER = "clima_field";

	
	public static  List<Entity> findForecast(int dia) {
		//to avoid the id 0
		   long dayKey = dia + 1;
		   Key diaForecast = KeyFactory.createKey(FORECAST_DAY_KEY, dayKey);
		   Query query = new Query(FORECAST_ENTITY, diaForecast);
		   DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		   
		   List<Entity> forecast = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
		return forecast;
	}
	
	
	
	public static  void saveTheRecordInDataStore(int day, Weather weather) {
		//to avoid the id 0
		long dayKey = day + 1;
		Key diaForecast = KeyFactory.createKey(FORECAST_DAY_KEY, dayKey);
		 Entity oneDayforecast = new Entity(FORECAST_ENTITY, diaForecast);
		  
		 
		 oneDayforecast.setProperty(DIA_FIELD_IDENTIFIER, dayKey);
		 oneDayforecast.setProperty(CLIMA_FIELD_IDENTIFIER, weather.toClima());
		
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 datastore.put(oneDayforecast);
	}
}
