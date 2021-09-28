
import entity.Weather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

    public interface WeatherModel {
        void getWeather(String selectedCity, Period period) throws IOException, SQLException;
        void getSavedToDBWeather(String selectedCity);
    }

