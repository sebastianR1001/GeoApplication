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
		                    URL url = new URL("http://192.168.1.6:8080/gis_server/GetGpsInformation");
		                    URLConnection connection = url.openConnection();
		
		                    //inputString = URLEncoder.encode(inputString, "UTF-8");
		
		                    connection.setDoOutput(true);
		                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		                    out.write(GeoService.StaticData.points.indexOf(0));
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
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.user;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.verificationCode;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getLocation();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getSpeed();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getTime();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getDistance();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getAltitude();
			gpsMessage += "|";
		}
		else {
			gpsMessage = "empty";
		}
		return gpsMessage;
	}
}
