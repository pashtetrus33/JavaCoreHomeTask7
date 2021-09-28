import entity.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Weather;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AccuweatherModel implements WeatherModel {

    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAY = "5day";
    private static final String API_KEY = "GRX9qyKDjztP68qroYzrqnxoLACp6mfM";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LANGUAGE = "language";
    private static final String METRIC = "metric";
    private static final String LOCATIONS ="locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();



    public void getWeather(String selectedCity, Period period) throws IOException, SQLException {
        switch (period) {
            case NOW -> {
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(LANGUAGE, "ru-ru")
                        .addQueryParameter(METRIC, "true")
                        .build();
                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();
                Response oneDayForecastResponse = okHttpClient.newCall(request).execute();
                String jsonString;
                jsonString = Objects.requireNonNull(oneDayForecastResponse.body()).string();
                System.out.println(jsonString);
                Root root = objectMapper.readValue(jsonString, Root.class);
                Date dateWeather = root.dailyForecasts.get(0).date;
                double minTemperature = (root.dailyForecasts.get(0).temperature.minimum).value;
                String unitMin = (root.dailyForecasts.get(0).temperature.minimum).unit;
                double maxTemperature = (root.dailyForecasts.get(0).temperature.maximum).value;
                String unitMax = (root.dailyForecasts.get(0).temperature.maximum).unit;
                String dayConditions = root.dailyForecasts.get(0).day.iconPhrase;
                String nightConditions = root.dailyForecasts.get(0).night.iconPhrase;
                System.out.println("Погода в " + selectedCity + " на 1 день: " + dateWeather +
                        ":\n" + "Минимальная температура " + minTemperature + unitMin +
                        "\n" + "Максимальная температура " + maxTemperature + unitMax +
                        "\n" + "Днем - " + dayConditions +
                        "\n" + "Ночью - " + nightConditions + "\n");

                Weather weather = new Weather(selectedCity,dateWeather.toString(),minTemperature, maxTemperature, dayConditions, nightConditions);
                DataBaseRepository dataBaseRepository = new DataBaseRepository();
                dataBaseRepository.saveWeatherToDataBase(weather);
            }
            case FIVE_DAYS -> {
                HttpUrl httpUrl_5d = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(LANGUAGE, "ru-ru")
                        .addQueryParameter(METRIC, "true")
                        .build();
                Request request_5d = new Request.Builder()
                        .url(httpUrl_5d)
                        .build();
                Response oneDayForecastResponse_5d = okHttpClient.newCall(request_5d).execute();
                String jsonResponse_5d;
                jsonResponse_5d = Objects.requireNonNull(oneDayForecastResponse_5d.body()).string();
                System.out.println(jsonResponse_5d);
                Root root2 = objectMapper.readValue(jsonResponse_5d, Root.class);
                for (DailyForecast i: root2.dailyForecasts){
                    Date dateWeather = i.date;
                    double minTemperature = i.temperature.minimum.value;
                    String unitMin = i.temperature.minimum.unit;
                    double maxTemperature = i.temperature.maximum.value;
                    String unitMax = i.temperature.maximum.unit;
                    String dayConditions = i.day.iconPhrase;
                    String nightConditions = i.night.iconPhrase;
                    System.out.println("Погода в " + selectedCity +" на 5 дней: " + dateWeather +
                            ":\n" + "Минимальная температура " + minTemperature + unitMin +
                            "\n" + "Максимальная температура " + maxTemperature + unitMax +
                            "\n" + "Днем - " + dayConditions +
                            "\n" + "Ночью - " + nightConditions + "\n");
                    Weather weather = new Weather(selectedCity,dateWeather.toString(),minTemperature, maxTemperature, dayConditions, nightConditions);
                    DataBaseRepository dataBaseRepository = new DataBaseRepository();
                    dataBaseRepository.saveWeatherToDataBase(weather);
                }


            }
        }
    }


   @Override
    public void getSavedToDBWeather(String selectedCity) {
       List<Weather> weathers = new ArrayList<>();
       try (Connection connection = DriverManager.getConnection(DataBaseRepository.DB_PATH)) {
           Statement statement = connection.createStatement();
           String getWeather = "select * from weather where city = '"+ selectedCity + "'";
           ResultSet resultSet = statement.executeQuery(getWeather);
           while (resultSet.next()) {
               weathers.add(new Weather(resultSet.getString("city"),
                       resultSet.getString("localdate"),
                       resultSet.getDouble("temperature_min"),
                       resultSet.getDouble("temperature_max"),
                       resultSet.getString("day_conditions"),
                       resultSet.getString("night_conditions")));
           }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       System.out.println(weathers);
    }

    private String detectCityKey(String selectCity) throws IOException {
        //http://dataservice.accuweather.com/locations/v1/cities/autocomplete
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOKOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectCity)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseString = Objects.requireNonNull(response.body()).string();

        return objectMapper.readTree(responseString).get(0).at("/Key").asText();
    }
}
