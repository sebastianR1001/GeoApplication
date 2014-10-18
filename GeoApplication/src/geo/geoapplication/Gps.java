package geo.geoapplication;

import java.util.Date;

import android.location.Location;
import android.location.LocationManager;

public class Gps {

	public Gps(LocationManager locMan, Location loc) {
		this.locationManager = locMan;
		
		this.location = loc;
		
		precision.setMaximumFractionDigits(1); 
	    precision.setMinimumFractionDigits(0);
	}
	
	public LocationManager locationManager;
	public Location location;
	private Location lastLocation;
	
	private float totalDistance = 0;
	
	private java.text.DecimalFormat precision =new java.text.DecimalFormat(); 
	
	public Location savedLocation = null;
	
	public String getGpsStatus() {
		
		String gpsStatus = "";
		
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            gpsStatus = "GPS";
        else
        	gpsStatus = "GPS Disabled. Please, turn it on";
        
        
        return gpsStatus;
	}
	
	public String showLocation() {
        String latitude  = "Latitude: ";
        String longitude = "Longitude: ";
        if(this.location == null) {
            latitude  += "...";
            longitude += "...";
        } else {
            latitude  += this.location.getLatitude();
            longitude += this.location.getLongitude();
        }
        
        return latitude + " " + longitude;
    }
   
	
	
	
	public String showSpeed() {
        String speed = "Speed: ";
        Double speedValue; 
                
        if(savedLocation == null || this.location == null) {
        	speed += "0 km/h";
        }
        else {
        	speedValue = this.location.getSpeed()*3.6;
            speed += precision.format(speedValue) + "km/h";
        }            
        
        return speed;
    }
	
	public String showDistance() {
        String distance = "Distance: ";
        float distanceValue;
       
        if(savedLocation == null || this.location == null) {
        	distance += "0 m";
        }
        else {
        	if(lastLocation == null) {
        		lastLocation = savedLocation;
        	}
        	totalDistance += lastLocation.distanceTo(this.location);
        	distanceValue = totalDistance;
        	
        	if(distanceValue >= 1000) {
        		distance += precision.format(distanceValue/1000) + " km";
        	}
        	else {
        		distance += precision.format(distanceValue) + " m";
        	}
        	
        	lastLocation = location;
        }            
        
        return distance;
    }
	
	public String showElevation() {
        String elevation = "Elevation: ";
        double elevationValue;
        
        if(savedLocation == null || this.location == null) {
        	elevation += "...";
        }
        else {
        	elevationValue = this.location.getAltitude();
            elevation += precision.format(elevationValue) + " AMSL";
        }            
        
        return elevation;
    }
	
	
	public String showAccuracy() {
        String accuracy = "Accuracy: ";
        if(savedLocation == null || this.location == null) {
        	accuracy += "can't calculate";
        }
        else {
        	accuracy += this.location.getAccuracy() + "m \n";
        }            
        
        return accuracy;
    }
	
	
	public String showLastFix() {
        String lastFix = "Last fix: ";
        if(savedLocation == null || this.location == null) {
        	lastFix += "can't calculate";
        }
        else {
            lastFix += new Date(this.location.getTime()) + "\n";
        }            
        
        return lastFix;
    }
}
