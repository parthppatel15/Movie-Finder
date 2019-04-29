package Movie;


//STEP 1. Import required packages
import java.sql.*;
import java.util.*;

public class Movie_Main {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost/IMDB";

 //  Database credentials
 static final String USER = "root";
 static final String PASS = "mysql11!";
 
 Connection conn = null;
 Statement stmt = null;
 ResultSet rs = null;
 
 Scanner reader;
 
 //USER 
 String id ;
 String password;
 
 public void getConnection()
 {
	   try{
		      //STEP 2: Register JDBC driver (automatically done since JDBC 4.0)
		      Class.forName("com.mysql.cj.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating a statement...");
		      stmt = conn.createStatement();
		      //stmt.executeUpdate("INSERT INTO MOVIE_INFO" + " (tconst, ordering, title) VALUES ('TEST', 1, 'TEST')");
		      //rs = stmt.executeQuery("select * from MOVIE_INFO");
		      
		      //STEP 5: Process the results
//		      while(rs.next()){
//		    	  System.out.println("Between");
//		         System.out.println("seasonNumber ID="+rs.getString("tconst")+", tconst="+rs.getString("ordering"));
//		      }
		      
		   }catch(SQLException se){
		      //Handle errors for JDBC
			   System.out.println("SQL ERROR");
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		     
		   }//end try
		   System.out.println("Goodbye!");
 }
 
 public void MainPage() throws SQLException
 {
	   System.out.println("WELCOME TO MOVIE FINDER APPLICATION");
	   while(true) {
	   System.out.println("PLEASE LOGIN OR SIGN UP");
	   System.out.println("1 LOGIN");
	   System.out.println("2 SIGN UP");
	   reader = new Scanner(System.in);
	   int input = reader.nextInt();
	   if(input == 1)
	   {
		   login();
	   }else
	   {
		   signup();
	   }
	   
	   }
	   
 }
 
 public void login() throws SQLException
 {
	   System.out.print("ID:");
	   id = reader.next();
	   System.out.print("PASSWORD:");
	   password = reader.next();
	   
	   rs = stmt.executeQuery("select password from USER where id ='"+ id+"'");
	      
	      //STEP 5: Process the results
	   
	      if(rs.next() && rs.getString("password").equals(password)){
	    	 System.out.println("LOGIN SUCCESSFULLY");
	    	 movieApp();
	    	
	      }else
	      {
	    	  System.out.println("NOT VALID USER ID OR PASSWORD");
	      }
	   
 }
 
 public void signup() throws SQLException
 {
	   System.out.print("ID: ");
	   id = reader.next();
	   System.out.print("email: ");
	   String email = reader.next();
	   System.out.print("region: ");
	   String region = reader.next();
	   System.out.print("age: ");
	   int age = reader.nextInt();
	   System.out.print("language: ");
	   String language = reader.next();
	   System.out.print("PASSWORD:");
	   password = reader.next();
	   
	   stmt.executeUpdate("INSERT INTO USER (id, password, email, region, language, age)" + "VALUES ('"+ id+ "', '"+ password+"', '"
			   + email +"', '"+ region + "', '" + language  + "', '" + age +
			   "')");
	   System.out.println("Successfully Created new ID");
 }
 
 public void movieApp() throws SQLException
 {
	   
	   System.out.println("WELCOME IN");
	   System.out.println("1 SEARCH MOVIES");
	   System.out.println("2 LIST ALL MOVIES");
	   System.out.println("3 MOVIE RATINGS");
	   System.out.println("4 GIVE MOVIE RATING");
	   System.out.println("5 SEARCH ACTORS");
	   System.out.println("6 SEARCH DIRECTOR AND/OR WRITERS");
	   
	   
	   while(true)
	   {
		   int res = reader.nextInt();
		   if(res == 1)
		   {
			   
			   search_movie();
			   break;
		   }
		   else if(res ==  2)
		   {
			   list_movie(); 
			   break; 
		   }
	   }
	   
	   
 }
 
 public void list_movie() throws SQLException
 {
	 System.out.println("List All Movies");
	 System.out.println("1.List Movie based on age");
	 System.out.println("2.List Movie based on Rating");
	 System.out.println("3.List recent Movies");
	 System.out.println("4.List Movies based on genres");
	 System.out.println("5.List the highest votes");
	 
	 while(true)
	 {
		 int res = reader.nextInt();
		 if(res == 1)
		 {
			 movie_age();
			 break; 
		 }
		 else if(res == 2)
		 {
			 rating_onMovie_year();
			 break; 
		 }
		 else if(res == 3)
		 {
			 recent_movies(); 
			 break; 
		 }
		 else if(res == 4)
		 {
			 genres_movie();
			 break;
		 }
		 else if(res ==5)
		 {
			 asc_movie(); 
			 break; 
		 }
			
	 }
	 
 }
 
 public void movie_age() throws SQLException
 {
	 System.out.println("Press 1 to list PG-13 Movies");
	 System.out.println("Press 2 to list Rated R Movies");
	 int res = reader.nextInt();
	 if(res == 1)
	 {
		 rs = stmt.executeQuery("select * from MOVIE_INFO Natural Join BASICS   where title = primaryTitle AND isAdult = 0 LIMIT 100");
		 while(rs.next())
		 {
			 System.out.println(" Title =" + rs.getString("title") + "\t PG-13 " );
			 
		 }
	 }
	 else if(res == 2)
	 {
		 rs = stmt.executeQuery("select * from MOVIE_INFO Inner Join BASICS  where title = primaryTitle AND isAdult = 1 LIMIT 100");
		 while(rs.next())
		 {
			 System.out.println("Title =" + rs.getString("title") + "\t Rated R");
			 
		 }
	 }
 }
 
 public void rating_onMovie_year() throws SQLException
 {
	 System.out.println("Enter a movie year:");
	 int res = reader.nextInt();
	 System.out.println("Enter a movie Rating:");
	 int ratingRes = reader.nextInt();
	 
	 rs = stmt.executeQuery("select * From BASICS Natural Join RATING  where startYear like '%" + res + "%' AND tconst in (select tconst From Rating where averageRating like '%" + ratingRes + "%'  ) LIMIT 100; ");
	 while(rs.next())
	 {
		 System.out.println("Title =" + rs.getString("primaryTitle") + ", \t Rating =" +rs.getFloat("averageRating"));
	 }
 }
 
 public void recent_movies() throws SQLException
 {
	 System.out.println("List of new Movies produced");
	 rs = stmt.executeQuery("Select * From BASICS where startYear = 2018 LIMIT 100");
	 while(rs.next())
	 {
		 System.out.println("Title = " + rs.getString("primaryTitle") + ", \t Year = " + rs.getInt("startYear"));
	 }
 }
 
 public void genres_movie() throws SQLException
 {
	 System.out.println("Enter Movie genre:");
	 String res = reader.next();
	 
	 rs = stmt.executeQuery("select * From BASICS where genre like '%" + res + "%' ORDER BY startYear ASC,  primaryTitle  LIMIT 100 ");
	 while(rs.next())
	 {
		 System.out.println("Title = " + rs.getString("primaryTitle") + ", \t genre = " + rs.getString("genre"));
	 }
	 
 }
 
 public void  asc_movie() throws SQLException
 {
	 System.out.println("List of Movies:");
	 rs = stmt.executeQuery("select primaryTitle, numVotes From BASICS Inner Join ratings using(tconst) where numVotes > 100  ORDER BY averageRating ASC LIMIT 100" );
	 
	 while(rs.next())
	 {
		 System.out.println("Title = " +rs.getString("primaryTitle") + ", \t number of votes" + rs.getInt("numVotes"));
	 }
 }
 
 public void search_movie() throws SQLException
 {
	   System.out.println("SEARCH MOVIES");
	   System.out.println("1.Search movies by name");
	   System.out.println("2.Search movies based on region");
	   System.out.println("3.Search movies by Actors");
	   System.out.println("4.Search Movies by directors");
	   System.out.println("5.Search Movies by writers");
	   System.out.println("6.Search movies based on languages");
	   while(true)
	   {
		   int res = reader.nextInt();
		   if(res == 1)
		   {
			   search_movie_by_name();
			   break;
		   }
	   }
 }
 
 public void search_movie_by_name() throws SQLException
 {
	   System.out.print("Please enter movie title: ");
	   String res = reader.next();
	  
	   rs = stmt.executeQuery("select * from MOVIE_INFO where title like  '%" + res + "%' ");
	   while(rs.next()){
	    	 //System.out.println("Between");
	         System.out.println("Title ID="+rs.getString("tconst")+", Ordering ="+rs.getString("ordering")+", Title ="+rs.getString("title"));
	      }
 }
 
 public void region_procedure() throws SQLException
 {
	 System.out.println("Enter a Region:");
	 String res = reader.next();
	 
	 //rs = stmt.executeQuery("CREATE PROCEDURE getMovieByRegion(IN regionName varchar(50), OUT result varchar(50)" + "Select * INTO Result From MOVIE_INFO 	where region = regionName; END");
	 CallableStatement cs = conn.prepareCall("{CALL getMovieByRegion(?)}"); 
	 cs.setString(1, res);
	 boolean hasResult = cs.execute();
	 while(hasResult)
	 {
		 System.out.println("Movies based on region: " + cs.getString("result"));
	 }
	 
 }
 


 
 
 public static void main(String[] args) {
	   Movie_Main main = new Movie_Main();
	   
	   main.getConnection();
	   
	   try {
		main.MainPage();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   try{
	         if(main.stmt!=null)
	            main.stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(main.conn!=null)
	            main.conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   
 
 }//end main
}//end Movie_Main