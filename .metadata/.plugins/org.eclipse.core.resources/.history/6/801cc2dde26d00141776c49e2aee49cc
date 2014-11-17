package geo.geoapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	public Login(final String user, final String password) {
		new Thread(new Runnable() {
            public void run() {
                try{
                	String md5Password = getMd5Hash(password);
                    URL url = new URL(Connection.host +"/gis_server/LoginFromAndroid");
                    
                    URLConnection connection = url.openConnection();
                    
                    //inputString = URLEncoder.encode(inputString, "UTF-8");

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    
                    out.write(user+"|"+md5Password);
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
                    	GeoService.StaticData.user = user;
                    	String verifyCode = "";
                    	for(int i=4; i<result.length(); i++) {
                    		verifyCode += result.charAt(i);
                    	}
                    	
                    	GeoService.StaticData.verificationCode = verifyCode;
                    }
                    
                    else {
                    	GeoService.StaticData.user = "login error";
                    }

                }catch(Exception e)
                {
                	GeoService.StaticData.user = "connection error";
                }

            }
          }).start();
	}
	
	private static String getMd5Hash(String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] messageDigest = md.digest(input.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        String md5 = number.toString(16);

	        while (md5.length() < 32)
	            md5 = "0" + md5;

	        return md5;
	    } catch (NoSuchAlgorithmException e) {
	        //Log.e("MD5", e.getLocalizedMessage());
	        return null;
	    }
	}
}
