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
            			if(GeoService.StaticData.points.get(0).equals("empty")) {
            				GeoService.StaticData.points.remove(0);
            				continue;
            			}
		                try{
		                    URL url = new URL(Connection.host +"/gis_server/GetGpsInformation");
		                    URLConnection connection = url.openConnection();

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
		                    	GeoService.StaticData.logged = true;
		                    	String verifyCode = "";
		                    	for(int i=4; i<result.length(); i++) {
		                    		verifyCode += result.charAt(i);
		                    	}
		                    	
		                    	GeoService.StaticData.verificationCode = verifyCode;
		                    	GeoService.StaticData.points.remove(0);
		                    }
		                    else if(result.contains("false")){
		                    	//GeoService.StaticData.logged = false;
		                    }
		
		                }catch(Exception e)
		                {
		                     
		                }
            		}
            		try {
						Thread.sleep(3000);
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
			/*gpsMessage += GeoService.StaticData.user;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.verificationCode;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getLocation();
			gpsMessage += "|";
			gpsMessage += "12.5";
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getTime();
			gpsMessage += "|";
			gpsMessage += "100.5";
			gpsMessage += "|";
			gpsMessage += "124.5678";
			gpsMessage += "|";
			*/
			
			gpsMessage += GeoService.StaticData.user;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.verificationCode;
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getLocation();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getSpeed().replace(',', '.');
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getTime();
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getDistance().replace(',', '.');
			gpsMessage += "|";
			gpsMessage += GeoService.StaticData.getAltitude().replace(',', '.');
			gpsMessage += "|";
			
		}
		else {
			gpsMessage = "empty";
		}
		return gpsMessage;
	}
}
