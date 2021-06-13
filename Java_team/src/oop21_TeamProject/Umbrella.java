package oop21_TeamProject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
 
class Umbrella{
    
    
    /***********************************************************************
     * 현재시간 가져오기
     * 
     * @return
     * *********************************************************************/
    public String fn_time() 
    {
    
    SimpleDateFormat Format = new SimpleDateFormat("yyyyMMDD HHmmss");
    Date time = new Date();
    
    String timedata = Format.format(time);
    
    return timedata;
    
    }
    
    public String fn_date()
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar c1 = Calendar.getInstance();

        String strToday = sdf.format(c1.getTime());
        
        return strToday;
	 
    }
    
    /********************************************************************************
     * 필요데이터 : 경도 및 위도, 현재날짜 및 시간, 발급받은 서비스키
     * 위도 경도에 따른 지역 날씨 예보 정보 가져옴
     * 3시간 단위로 날씨 데이터 업데이트(기상청)
     * @param baseDate
     * @param baseTime
     * @param nx
     * @param ny
     * @return
     * *******************************************************************************/
    public String fn_APIConnect(String baseDate, String baseTime, String nx, String ny) 
    {
        String ConnectValue = "";
        BufferedReader br;
        String servicekey = "1dOmwoEa7y2plmRQ15XsJdX%2FoPwKu8b7kJvKz%2B5LNkeWVppLld%2BLg5yAQMqUnP8jxaXA87LIz9j4LkZTIg2mrA%3D%3D";
        
        String address = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey="
                + servicekey +"&pageNo=1&numOfRows=10&dataType=JSON"+ "&base_date=" + baseDate + "&base_time=" + baseTime + "&nx=" + nx + "&ny=" + ny;

        
       // System.out.println(address); 주소 오류시 주소 확인용
        try 
        {
            URL url = new URL(address);
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            else 
            {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String ResData = br.readLine();
            
            if(ResData == null) 
            {
                System.out.println("응답데이터 == NULL");
            }
            else 
            {
                 ConnectValue = fn_Jsonp(ResData);
                
            }
            br.close();
            conn.disconnect();
        }catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
        return ConnectValue;
    }
    /****************************************************************************
     * JSON 데이터 파싱함수
     * 데이터 추출
     * @param Data
     * **************************************************************************/
    public String fn_Jsonp(String Data)
    {
        
        JSONObject WeatherData;
        String VALUE = "";
        String date = "";
        String time = "";
        String DataValue = "";
        String info = "";
        
        try 
        {
        JSONParser parsar = new JSONParser();
        JSONObject obj = (JSONObject) parsar.parse(Data);
        JSONObject response = (JSONObject) obj.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject)body.get("items");
        JSONArray item = (JSONArray)items.get("item");
        
            for(int i = 0; i < item.size(); i++) 
            {
                WeatherData = (JSONObject) item.get(i);
                
                date = WeatherData.get("baseDate").toString();
                time = WeatherData.get("baseTime").toString();
                DataValue = WeatherData.get("fcstValue").toString();
                info = WeatherData.get("category").toString();
            
            
            if(info.equals("POP")) {
                
                info = "강수확률";
                DataValue  = DataValue+" %";
            }
            if(info.equals("REH")) {
                
                info = "습도";
                DataValue = DataValue+" %";
            }
            if(info.equals("SKY")) {
                info = "하늘상태";
                if(DataValue.equals("1")) {
                    DataValue = "맑음";
                }else if(DataValue.equals("2")) {
                    DataValue = "비";
                }else if(DataValue.equals("3")) {
                    DataValue = "구름많음";
                }else if(DataValue.equals("4")) {
                    DataValue = "흐림";
                }
            }
            if(info.equals("UUU")) {
                info = "동서성분풍속";
                DataValue = DataValue+" m/s";
            }
            if(info.equals("VVV")) {
                info = "남북성분풍속";
                DataValue = DataValue+" m/s";
            }
            if(info.equals("T1H")) {
                info = "기온";
                DataValue = DataValue+"℃";
            }
            if(info.equals("R06")) {
                info = "6시간강수량";
                DataValue = DataValue + " mm";
                
            }
            if(info.equals("S06")) {
                info = "6시간적설량";
                DataValue = DataValue + " mm";
            }
            if(info.equals("PTY")){
                info = "강수형태";
                if(DataValue.equals("0")) {
                    DataValue = "없음";
                }else if(DataValue.equals("1")) {
                    DataValue = "비";
                }else if(DataValue.equals("2")) {
                    DataValue = "눈/비";
                }else if(DataValue.equals("3")) {
                    DataValue = "눈";
                }
            }
            if(info.equals("T3H")) {
                info = "3시간기온";
                DataValue = DataValue + " ℃";
            }
            if(info.equals("VEC")) {
                info = "풍향";
                DataValue = DataValue + " m/s";
            }
            
            
            VALUE += info + "," +  DataValue + "," + date + "," + time + ","; 
        }
 
        }catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
        return VALUE;
    }
 
    /**********************************************************************
     * 현재시간을 가져와서 ex) 1000 형태로 만들어줌
     * 3시간 마다 업데이트 되기 때문에 각 시간에 따라 업데이트 시간으로 설정
     * @param timedata
     * @return 
     * *********************************************************************/
    public String fn_timeChange(String timedata) 
    {
            String hh = timedata.substring(9, 11);
            String baseTime = "";
            hh = hh + "00";
            
            // 현재 시간에 따라 데이터 시간 설정(3시간 마다 업데이트) //
            switch(hh) {
            
            case "0200":
            case "0300":
            case "0400":
                baseTime = "0200";
                break;
            case "0500":
            case "0600":
            case "0700":
                baseTime = "0500";
                break;
            case "0800":
            case "0900":
            case "1000":
                baseTime = "0800";
                break;
            case "1100":
            case "1200":
            case "1300":
                baseTime = "1100";
                break;
            case "1400":
            case "1500":
            case "1600":
                baseTime = "1400";
                break;
            case "1700":
            case "1800":
            case "1900":
                baseTime = "1700";
                break;
            case "2000":
            case "2100":
            case "2200":
                baseTime = "2000";
                break;
            default: 
                baseTime = "2300";
                
            }
            return baseTime;
    }
    public static void PrintWeather() {
        
        String Response = "";
        String[] ResponseData ;
        String end = "";
        Umbrella WeatherAPI = new Umbrella();
        
        //fn_time 함수를 사용하여 현재시간 받아오기
        String timedata = WeatherAPI.fn_time();
        
        //현재시간을 활용하여 기상청 시간데이터 형식에 맞게 변환
        String HH = WeatherAPI.fn_timeChange(timedata);
       // String HH = "0500";
        String YMD = "20210602";
        //String YMD = WeatherAPI.fn_date();
        
        
        // System.out.println("시간:"+HH+"날짜"+YMD);
        
        //경도 //위도
        String nx = "60";
        String ny = "127";
        
        //연결하기 위한 파라미터들
        Response = WeatherAPI.fn_APIConnect(YMD, HH, nx, ny);
        ResponseData = Response.split(",");
         
        // 우산 필요 여부 판단
        String[] rainfallProbability = ResponseData[1].split("%");
        double needUmbrella = Double.valueOf(rainfallProbability[0]);
        if(needUmbrella > 80) {
        	System.out.println("우산을 꼭 챙겨가세요!");
        }
        else if((needUmbrella > 50) && (needUmbrella <= 80)) {
        	System.out.println("우산을 챙기는 편이 좋습니다.");
        }
        else {
        	System.out.println("우산을 챙기지 않아도 괜찮아요!");
        }
        System.out.println("********************************");
        
        
        System.out.print(ResponseData[0]);
        System.out.println(" " + ResponseData[1]);
        System.out.print("날짜(YYYYMMDD) : ");
        System.out.print(ResponseData[2]+"/ 시간 :");
        System.out.println(" " + ResponseData[3]);
        System.out.print(ResponseData[4]);
        System.out.println(" " + ResponseData[5]);
        System.out.print(ResponseData[8]);
        System.out.println(" " + ResponseData[9]);
        System.out.print(ResponseData[12]);
        System.out.println(" " + ResponseData[13]);
        System.out.print(ResponseData[16]);
        System.out.println(" " + ResponseData[17]);
        System.out.print(ResponseData[20]);
        System.out.println(" " + ResponseData[21]);
        System.out.print(ResponseData[24]);
        System.out.println(" " + ResponseData[25]);
        System.out.print(ResponseData[28]);
        System.out.println(" " + ResponseData[29]);
        System.out.print(ResponseData[32]);
        System.out.println(" " + ResponseData[33]);
        System.out.println("********************************");
         
       
 
    }
    
}
