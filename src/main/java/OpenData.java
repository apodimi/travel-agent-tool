import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

import java.lang.*;

/**
 * City description and weather information using OpenData with Jackson JSON processor.
 *
 * @author John Violos
 * @version 1.0
 * @since 29-2-2020
 */
public class OpenData implements Runnable {

	public  String city;
	public  String country;
	public  String appid;
	public  ObjectMapper mapper;
	public  City newCity;


	public OpenData(String city, String country, String appid, City newCity, ObjectMapper mapper) {
		this.city = city;
		this.country = country;
		this.appid = appid;
		this.mapper = mapper;
		this.newCity = newCity;
	}

	@Override
	public void run() {
		try {
			OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);
			double lat = weather_obj.getCoord().getLat(); //Save the latitude
			double lon = weather_obj.getCoord().getLon(); //Save longitude
			newCity.setGeodesic_vector(lat, lon); //Sent the variable to method in city

		} catch (Exception e) {
			System.out.println("Something went wrong with OpenWeather Map");
		}
	}



	/**
	 * Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city.
	 *
	 * @param city    The Wikipedia article and OpenWeatherMap city.
	 * @param country The country initials (i.e. gr, it, de).
	 * @param appid   Your API key of the OpenWeatherMap.
	 * @return
	 */
	public static String RetrieveData(String city, String country, String appid, City newCity) throws IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();
		OpenData data = new OpenData(city, country, appid, newCity, mapper);
		Thread thread = new Thread(data);
		thread.start();
		thread.join();
		MediaWiki mediaWiki_obj = mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
		return mediaWiki_obj.getQuery().getPages().get(0).getExtract();


	}


}