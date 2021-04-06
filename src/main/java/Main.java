import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class Main {

	public static void main(String[] args) throws IOException {
		String appid ="a0c3f1ce027ecc4b1932f1b78a91f755";
		RetrieveData("Rome","it",appid);
		RetrieveData("Athens","gr",appid);
		RetrieveData("Corfu","gr",appid);
		RetrieveData("Berlin","de",appid);

	}

	public static void RetrieveData(String city, String country, String appid) throws  IOException {
		ObjectMapper mapper = new ObjectMapper();
		OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&appid="+appid+""), OpenWeatherMap.class);
		System.out.println(city+" temperature: " + (weather_obj.getMain()).getTemp());
		System.out.println(city+" lat: " + weather_obj.getCoord().getLat()+" lon: " + weather_obj.getCoord().getLon());
		MediaWiki mediaWiki_obj =  mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city+"&format=json&formatversion=2"),MediaWiki.class);
		System.out.println(city+" Wikipedia article: "+mediaWiki_obj.getQuery().getPages().get(0).getExtract());
	}
}
