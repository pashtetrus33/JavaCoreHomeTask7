import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class AccuWeatherType {

    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAY = "5day";
    private static final String API_KEY = "GRX9qyKDjztP68qroYzrqnxoLACp6mfM";
    private static final String PITER_KEY = "295212";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LANGUAGE = "language";
    private static final String METRIC = "metric";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void getWeather(String input) throws IOException {
        switch (input) {
            case "1" -> {
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(PITER_KEY)
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
                ObjectMapper objectMapper = new ObjectMapper();
                Root root = objectMapper.readValue(jsonString, Root.class);
                Date dateWeather = root.dailyForecasts.get(0).date;
                double minTemperature = (root.dailyForecasts.get(0).temperature.minimum).value;
                String unitMin = (root.dailyForecasts.get(0).temperature.minimum).unit;
                double maxTemperature = (root.dailyForecasts.get(0).temperature.maximum).value;
                String unitMax = (root.dailyForecasts.get(0).temperature.maximum).unit;
                String dayConditions = root.dailyForecasts.get(0).day.iconPhrase;
                String nightConditions = root.dailyForecasts.get(0).night.iconPhrase;
                System.out.println("Погода в Санкт-Петербурге на " + dateWeather +
                        ":\n" + "Минимальная температура " + minTemperature + unitMin +
                        "\n" + "Максимальная температура " + maxTemperature + unitMax +
                        "\n" + "Днем - " + dayConditions +
                        "\n" + "Ночью - " + nightConditions + "\n");
            }
            case "5" -> {
                HttpUrl httpUrl_5d = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAY)
                        .addPathSegment(PITER_KEY)
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(LANGUAGE, "ru-ru")
                        .build();
                Request request_5d = new Request.Builder()
                        .url(httpUrl_5d)
                        .build();
                Response oneDayForecastResponse_5d = okHttpClient.newCall(request_5d).execute();
                String weatherResponse_5d;
                weatherResponse_5d = Objects.requireNonNull(oneDayForecastResponse_5d.body()).string();
                System.out.println(weatherResponse_5d);
            }
        }
    }

}
