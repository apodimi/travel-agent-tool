import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

/**City description and weather information using OpenData with Jackson JSON processor.
* @since 29-2-2020
* @version 1.0
* @author John Violos */
public class OpenData {

	/**Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city.
	 * @param city The Wikipedia article and OpenWeatherMap city.
	 * @param country The country initials (i.e. gr, it, de).
	 * @param appid Your API key of the OpenWeatherMap.
	 * @return */
	 public static String RetrieveData(String city, String country, String appid) throws  IOException {
		 ObjectMapper mapper = new ObjectMapper();
		 OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+""), OpenWeatherMap.class);
		 double lat = weather_obj.getCoord().getLat(); //Save the latitude
		 double lon = weather_obj.getCoord().getLon(); //Save longitude
		 City.setGeodesic_vector(lat,lon); //Sent the variable to method in city
		 MediaWiki mediaWiki_obj =  mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city+"&format=json&formatversion=2"),MediaWiki.class);
		 return mediaWiki_obj.getQuery().getPages().get(0).getExtract();

	 }


	/*public static void main(String[] args) throws IOException {
		String appid ="";
		RetrieveData("Rome","it",appid);
		RetrieveData("Athens","gr",appid);
		RetrieveData("Corfu","gr",appid);
		RetrieveData("Berlin","de",appid);
	}*/

}