public class WeatherData {
    private String id;private String name;private String state;private String time_zone;private String lat;
    private String lon;private String local_date_time;private String local_date_time_full;private String air_temp;
    private String apparent_t;private String cloud;private String dewpt;private String press;
    private String rel_hum;private String wind_dir;private String wind_spd_kmh;private String wind_spd_kt;

    public String getId(){return this.id;}public String getName(){return name;}public String getState(){return state;}public String getTime_zone(){return time_zone;}public String getLat(){return lat;}
    public String getLon(){return lon;}public String getLocal_date_time(){return local_date_time;}public String getLocal_date_time_full(){return local_date_time_full;}public String getAir_temp(){return air_temp;}
    public String getApparent_t(){return apparent_t;}public String getCloud(){return cloud;}public String getDewpt(){return dewpt;}public String getPress(){return press;}public String getRel_hum(){return rel_hum;}
    public String getWind_dir(){return wind_dir;}public String getWind_spd_kmh(){return wind_spd_kmh;}public String getWind_spd_kt(){return wind_spd_kt;}
    public void setId(String id){this.id = id;}public void setName(String name){this.name = name;}public void setState(String state){ this.state = state;}public void setTime_zone(String time_zone){ this.time_zone = time_zone;}
    public void setLat(String lat){ this.lat = lat;}
    public void setLon(String lon){ this.lon = lon;}public void setLocal_date_time(String local_date_time){ this.local_date_time = local_date_time;}public void setLocal_date_time_full(String local_date_time_full){ this.local_date_time_full = local_date_time_full;}
    public void setAir_temp(String air_temp){ this.air_temp = air_temp;}
    public void setApparent_t(String apparent_t){ this.apparent_t = apparent_t;}public void setCloud(String cloud){ this.cloud = cloud;}
    public void setDewpt(String dewpt){ this.dewpt = dewpt;}public void setPress(String press){ this.press = press;}
    public void setRel_hum(String rel_hum){ this.rel_hum = rel_hum;}
    public void setWind_dir(String wind_dir){ this.wind_dir = wind_dir;}public void setWind_spd_kmh(String wind_spd_kmh){ this.wind_spd_kmh = wind_spd_kmh;}public void setWind_spd_kt(String wind_spd_kt){ this.wind_spd_kt = wind_spd_kt;}

    public void set(String key, String value){
        if(key.equals("id")){
            setId(value);
        }
        if(key.equals("name")){
            setName(value);
        }
        if(key.equals("time_zone")){
            setTime_zone(value);
        }
        if(key.equals("lat")){
            setLat(value);
        }
        if(key.equals("lon")){
            setLon(value);
        }
        if(key.equals("local_date_time")){
            setLocal_date_time(value);
        }
        if(key.equals("local_date_time_full")){
            setLocal_date_time_full(value);
        }
        if(key.equals("air_temp")){
            setAir_temp(value);
        }
        if(key.equals("apparent_t")){
            setApparent_t(value);
        }
        if(key.equals("cloud")){
            setCloud(value);
        }
        if(key.equals("dewpt")){
            setDewpt(value);
        }
        if(key.equals("press")){
            setPress(value);
        }
        if(key.equals("rel_hum")){
            setRel_hum(value);
        }
        if(key.equals("wind_dir")){
            setWind_dir(value);
        }
        if(key.equals("wind_spd_kmh")){
            setWind_spd_kmh(value);
        }
        if(key.equals("wind_spd_kt")){
            setWind_spd_kt(value);
        }
    }

}
