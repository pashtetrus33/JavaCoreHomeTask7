import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

class Headline{
    @JsonProperty("EffectiveDate")
    public Date effectiveDate;
    @JsonProperty("EffectiveEpochDate")
    public int effectiveEpochDate;
    @JsonProperty("Severity")
    public int severity;
    @JsonProperty("Text")
    public String text;
    @JsonProperty("Category")
    public String category;
    @JsonProperty("EndDate")
    public Date endDate;
    @JsonProperty("EndEpochDate")
    public int endEpochDate;
    @JsonProperty("MobileLink")
    public String mobileLink;
    @JsonProperty("Link")
    public String link;
}

class Minimum{
    @JsonProperty("Value")
    public double value;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("UnitType")
    public int unitType;
}

class Maximum{
    @JsonProperty("Value")
    public double value;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("UnitType")
    public int unitType;
}

class Temperature{
    @JsonProperty("Minimum")
    public Minimum minimum;
    @JsonProperty("Maximum")
    public Maximum maximum;
}

class Day{
    @JsonProperty("Icon")
    public int icon;
    @JsonProperty("IconPhrase")
    public String iconPhrase;
    @JsonProperty("HasPrecipitation")
    public boolean hasPrecipitation;
}
class Night{
    @JsonProperty("Icon")
    public int icon;
    @JsonProperty("IconPhrase")
    public String iconPhrase;
    @JsonProperty("HasPrecipitation")
    public boolean hasPrecipitation;
}
class DailyForecast{
    @JsonProperty("Date")
    public Date date;
    @JsonProperty("EpochDate")
    public int epochDate;
    @JsonProperty("Temperature")
    public Temperature temperature;
    @JsonProperty("Day")
    public Day day;
    @JsonProperty("Night")
    public Night night;
    @JsonProperty("Sources")
    public List<String> sources;
    @JsonProperty("MobileLink")
    public String mobileLink;
    @JsonProperty("Link")
    public String link;

}

class Root{
    @JsonProperty("Headline")
    public Headline headline;
    @JsonProperty("DailyForecasts")
    public List<DailyForecast> dailyForecasts;
}

