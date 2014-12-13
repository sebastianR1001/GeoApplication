package geo.geoapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class SendNote {

	public SendNote(boolean publicNote, String note) {
		
		String access = publicNote ? "public" : "private";
		try{
            URL url = new URL(Connection.host +"/gis_server/GetNoteFromAndroid");
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(GetLetter(access, note));
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

            }
            else if(result.contains("false")){
            	
            }

        }catch(Exception e)
        {
             
        }
	}
	
	private String GetLetter(String  access, String note) {
		String letter = "";
		
		if(GeoService.StaticData.logged) {
			letter += GeoService.StaticData.user;
			letter += "|";
			letter += GeoService.StaticData.verificationCode;
			letter += "|";
			letter += GeoService.StaticData.getLocation();
			letter += "|";
			letter += GeoService.StaticData.getAltitude();
			letter += "|";
			letter += access;
			letter += "|";
			letter += note;
			letter += "|";
		}
		else {
			letter = "empty";
		}
		
		return letter;
	}
}
