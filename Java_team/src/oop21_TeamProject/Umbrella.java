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
     * ����ð� ��������
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
     * �ʿ䵥���� : �浵 �� ����, ���糯¥ �� �ð�, �߱޹��� ����Ű
     * ���� �浵�� ���� ���� ���� ���� ���� ������
     * 3�ð� ������ ���� ������ ������Ʈ(���û)
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

        
       // System.out.println(address); �ּ� ������ �ּ� Ȯ�ο�
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
                System.out.println("���䵥���� == NULL");
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
     * JSON ������ �Ľ��Լ�
     * ������ ����
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
                
                info = "����Ȯ��";
                DataValue  = DataValue+" %";
            }
            if(info.equals("REH")) {
                
                info = "����";
                DataValue = DataValue+" %";
            }
            if(info.equals("SKY")) {
                info = "�ϴû���";
                if(DataValue.equals("1")) {
                    DataValue = "����";
                }else if(DataValue.equals("2")) {
                    DataValue = "��";
                }else if(DataValue.equals("3")) {
                    DataValue = "��������";
                }else if(DataValue.equals("4")) {
                    DataValue = "�帲";
                }
            }
            if(info.equals("UUU")) {
                info = "��������ǳ��";
                DataValue = DataValue+" m/s";
            }
            if(info.equals("VVV")) {
                info = "���ϼ���ǳ��";
                DataValue = DataValue+" m/s";
            }
            if(info.equals("T1H")) {
                info = "���";
                DataValue = DataValue+"��";
            }
            if(info.equals("R06")) {
                info = "6�ð�������";
                DataValue = DataValue + " mm";
                
            }
            if(info.equals("S06")) {
                info = "6�ð�������";
                DataValue = DataValue + " mm";
            }
            if(info.equals("PTY")){
                info = "��������";
                if(DataValue.equals("0")) {
                    DataValue = "����";
                }else if(DataValue.equals("1")) {
                    DataValue = "��";
                }else if(DataValue.equals("2")) {
                    DataValue = "��/��";
                }else if(DataValue.equals("3")) {
                    DataValue = "��";
                }
            }
            if(info.equals("T3H")) {
                info = "3�ð����";
                DataValue = DataValue + " ��";
            }
            if(info.equals("VEC")) {
                info = "ǳ��";
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
     * ����ð��� �����ͼ� ex) 1000 ���·� �������
     * 3�ð� ���� ������Ʈ �Ǳ� ������ �� �ð��� ���� ������Ʈ �ð����� ����
     * @param timedata
     * @return 
     * *********************************************************************/
    public String fn_timeChange(String timedata) 
    {
            String hh = timedata.substring(9, 11);
            String baseTime = "";
            hh = hh + "00";
            
            // ���� �ð��� ���� ������ �ð� ����(3�ð� ���� ������Ʈ) //
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
        
        //fn_time �Լ��� ����Ͽ� ����ð� �޾ƿ���
        String timedata = WeatherAPI.fn_time();
        
        //����ð��� Ȱ���Ͽ� ���û �ð������� ���Ŀ� �°� ��ȯ
        String HH = WeatherAPI.fn_timeChange(timedata);
       // String HH = "0500";
        String YMD = "20210602";
        //String YMD = WeatherAPI.fn_date();
        
        
        // System.out.println("�ð�:"+HH+"��¥"+YMD);
        
        //�浵 //����
        String nx = "60";
        String ny = "127";
        
        //�����ϱ� ���� �Ķ���͵�
        Response = WeatherAPI.fn_APIConnect(YMD, HH, nx, ny);
        ResponseData = Response.split(",");
         
        // ��� �ʿ� ���� �Ǵ�
        String[] rainfallProbability = ResponseData[1].split("%");
        double needUmbrella = Double.valueOf(rainfallProbability[0]);
        if(needUmbrella > 80) {
        	System.out.println("����� �� ì�ܰ�����!");
        }
        else if((needUmbrella > 50) && (needUmbrella <= 80)) {
        	System.out.println("����� ì��� ���� �����ϴ�.");
        }
        else {
        	System.out.println("����� ì���� �ʾƵ� �����ƿ�!");
        }
        System.out.println("********************************");
        
        
        System.out.print(ResponseData[0]);
        System.out.println(" " + ResponseData[1]);
        System.out.print("��¥(YYYYMMDD) : ");
        System.out.print(ResponseData[2]+"/ �ð� :");
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
