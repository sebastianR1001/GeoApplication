package geo.geoapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SendGpsInformation {
	public SendGpsInformation() {
		new Thread(new Runnable() {
            public void run() {
            	while(true) {
            		if(GeoService.StaticData.points.size() > 0) {
		                try{
		                    URL url = new URL("http://192.168.43.149:8080/gis_server/GetGpsInformation");
		                    //URL url = new URL("http://10.237.140.71:8080/gis_server/GetGpsInformation");
		                    URLConnection connection = url.openConnection();
		
		                    //inputString = URLEncoder.encode(inputString, "UTF-8");
		
		                    connection.setDoOutput(true);
		                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		                    out.write(GeoService.StaticData.points.get(0));
		                    out.close();
		
		                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		                    String returnString="";
		                    String result = "";
		                   
		                    while ((returnString = in.readLine()) != null) 
		                    {
		                        result += returnString;
		                    }
		                    in.close();
		
		                    if(result.contains("true")) {
		                    	GeoService.StaticData.points.remove(0);
		                    }
		
		                }catch(Exception e)
		                {
		                     
		                }
            		}
            		try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}

            }
          }).start();
	}
	
	public static String getGpsMessage() {
		String gpsMessage = "";
		
		if(GeoService.StaticData.logged) {
			gpsMessage += GeoService.StaticData.user;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.verificationCode;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getLocation();
			//gpsMessage += "25.3456;14.2578";
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getSpeed().replace(',', '.');
			//gpsMessage += "12.5";
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getTime();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getDistance().replace(',', '.');
			//gpsMessage += "100.5";
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getAltitude().replace(',', '.');
			//gpsMessage += "124.5678";
			gpsMessage += "|";
		}
		else {
			gpsMessage = "empty";
		}
		return gpsMessage;
	}
}
