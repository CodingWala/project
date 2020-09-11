	package com.agoda.hotelSearch;
	
	import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
	
	public class DateSelection {
	
	static String date;
	static String checkIn_Date;
	static String checkOut_Date;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	//Method to change the date format to DD/MM/YYYY and returning new date after adding days  
	public static String addDays(String date,long days)throws Exception {		 
		DateTimeFormatter.ofPattern("DD/MM/YYYY");
		String date2=formatter.format(LocalDate.parse(date,formatter).plusDays(days));
		return date2;
	}
	//Method to generate System Current Date in (DD/MM/YYYY)
	public static String systemDate() throws Exception{
		 
		 // Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 
		 //get current date time with Date()
		 Date date = new Date();
		 
		 // Now format the date
		 String date1= dateFormat.format(date);
		 
		 // Print the Date
		 //System.out.println(date1);
		 
		 return date1;
		 
	}
	//Method to convert the date DD/MM/YYYY format to "day MMM DD YYYY" eg 21/09/2020 to "Mon Sep 21 2020"
	public static String changeDateFormat(String date,String date_day) {
		String newDate=date_day+" ";
		String [] divString=date.split("/");
		int month, year;
		//day=Integer.parseInt(divString[0]);
		month=Integer.parseInt(divString[1]);
		year=Integer.parseInt(divString[2]);
		switch(month) {
		case 1:
			newDate+="Jan";
			break;
		case 2:
			newDate+="Feb";
			break;
		case 3:
			newDate+="Mar";
			break;
		case 4:
			newDate+="Apr";
			break;
		case 5:
			newDate+="May";
			break;
		case 6:
			newDate+="Jun";
			break;
		case 7:
			newDate+="Jul";
			break;
		case 8:
			newDate+="Aug";
			break;
		case 9:
			newDate+="Sep";
			break;
		case 10:
			newDate+="Oct";
			break;
		case 11:
			newDate+="Nov";
			break;
		case 12:
			newDate+="Dec";
			break;
		}
		
		newDate=newDate+" "+divString[0]+" "+year;
		
		//System.out.println(newDate);
		
		return newDate;
	}
	//Method to generate random checkIn and CheckOut dates in next week with reference to system current date 
	public static void generateDates() throws Exception {

		String tempDate = null;
		Random r = new Random();
		int randomInteger = r.nextInt(5);
		
		String[] newDay = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
		String[] list= {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};
		
		date=DateSelection.systemDate();
		DateTimeFormatter.ofPattern("DD/MM/YYYY");

		l1:{
		for(int i=1;i<=7;i++) 
			{	
				tempDate=DateSelection.addDays(date, i);
				
				// Parses the date 
			    LocalDate dt = LocalDate.parse(tempDate,formatter); 
			    String day=dt.getDayOfWeek().toString();
			    
			    if(day.equals("MONDAY"))
					for(int j=0;j<5;j++)
					{
						tempDate= DateSelection.addDays(date, i+j); 
						LocalDate dtNew = LocalDate.parse(tempDate,formatter); 
					    String dayNew=dtNew.getDayOfWeek().toString();
						 if(dayNew.equals(list[randomInteger]))
							break l1;
					} 
			 }
		}
		checkIn_Date=DateSelection.changeDateFormat(tempDate,newDay[randomInteger]);
		checkOut_Date=DateSelection.changeDateFormat(DateSelection.addDays(tempDate, 2),newDay[randomInteger+2]);
		System.out.println("CheckIn Date : "+checkIn_Date+"\n"+"CheckOut Date : "+checkOut_Date); 	
	}
}