package entity;

public class Weather {
    private String city;
    private String localDate;
    private double temperature_min;
    private double temperature_max;
    private String day_conditions;
    private String night_conditions;

    public Weather(String city, String localDate, double temperature_min, double temperature_max, String day_conditions, String night_conditions) {
        this.city = city;
        this.localDate = localDate;
        this.temperature_min = temperature_min;
        this.temperature_max = temperature_max;
        this.day_conditions = day_conditions;
        this.night_conditions = night_conditions;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }


    public double getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(double temperature_min) {
        this.temperature_min = temperature_min;
    }

    public double getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(double temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getDay_conditions() {
        return day_conditions;
    }

    public void setDay_conditions(String day_conditions) {
        this.day_conditions = day_conditions;
    }

    public String getNight_conditions() {
        return night_conditions;
    }

    public void setNight_conditions(String night_conditions) {
        this.night_conditions = night_conditions;
    }

    @Override
    public String toString() {
        return "Погода в " + city +" : " + localDate +
                ":\n" + "Минимальная температура " + temperature_min +
                "\n" + "Максимальная температура " + temperature_max +
                "\n" + "Днем - " + day_conditions +
                "\n" + "Ночью - " + night_conditions + "\n";
    }
}
