
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DBExecute {
	Connection  connection = null; 
	Statement stmt = null;
	String genreValue ;
	ArrayList<String> genre = new ArrayList<String>();
        
	DBExecute()
	{
		connection =  dbconnector.getConnection();
	}
	
	public ArrayList<String> getGenres() throws SQLException
	{
		String sql =  "SELECT DISTINCT genre FROM movie_genres ORDER BY genre";
		stmt = connection.createStatement();
		ResultSet rs1 = stmt.executeQuery(sql);
		 while (rs1.next())
		 {
			 genreValue= rs1.getString("GENRE");
			 genre.add(genreValue);
		 }
		System.out.println("Genres SQL -- "+sql);
		return genre;
		 		
	}
	
	public ArrayList<String> getCountries(List<String> genres ,String from, String to, String condition)
	{
		ArrayList<String> countries = new ArrayList<String>();
		String operation = "";
                String yearoperation = "";
		int count = 0;
                if(genres.size()>0){
                    for(String genre : genres){
                        count++;
                        if(count == 1)
                            operation = "SELECT movieid FROM movie_genres WHERE GENRE = " + "'"+genre+"'";
                        else
                            operation = operation + " " +condition + " GENRE = " + "'"+genre+"'";
                    }
                }
                if(!from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+" AND year <= "+Integer.parseInt(to)+")";
                }else if(from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year <= "+Integer.parseInt(to)+")";
                }else if(!from.equals("") && to.equals("")){
                    yearoperation = "INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+")";
                }
                String sql = "";
                if(!operation.equals(""))
                    sql = "SELECT DISTINCT country FROM movie_countries WHERE movieID IN (" + operation + yearoperation +") ORDER BY country";
		else
                    sql = "SELECT DISTINCT country FROM movie_countries ORDER BY country";
		
		System.out.println("Countries SQL -- "+sql);
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
		countries.add(rs.getString(1));
		}
		stmt.close();
		} catch (SQLException e) {
		
		e.printStackTrace();
		}
		return countries;	
	}
        
        public ArrayList<String> getActors(List<String> countries, List<String> genres,String from, String to, String condition)
	{
		ArrayList<String> actors = new ArrayList<String>();
		String operation = "";
		String operation1 = "";
                String yearoperation = "";
		int count = 0;
		int cnt = 0;
                String sql4 = "";
                if(genres.size()>0){
                    for(String genre : genres){
                        count++;
                        if(count == 1)
                            operation = "SELECT movieID FROM movie_genres WHERE genre = '"+genre+"'";
                        else
                            operation = operation +" "+ condition + " genre = '"+genre+"'";
                    }
                }
                if(!from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+" AND year <= "+Integer.parseInt(to)+")";
                }else if(from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year <= "+Integer.parseInt(to)+")";
                }else if(!from.equals("") && to.equals("")){
                    yearoperation = "INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+")";
                }
                if(countries.size()>0){
                    for(String country : countries){
			cnt++;
			if(cnt == 1)
			operation1 = "SELECT movieID FROM movie_countries WHERE country = '"+country+"'";
			else
			operation1 = operation1 +" "+ condition + " country = '"+country+"'";
                    }
                }
		if(cnt!=0 && count!=0)
                    {sql4 = "(" +operation+")" + " INTERSECT "+ "("+operation1+")";}
		 else
                    if(count!=0 && cnt==0)
                    {sql4 = "(" +operation+")" ;}
                    else if (cnt!=0 && count==0)
                    {sql4 = "(" +operation1 + ")" ;}
		
                
		String sql = "";
                if(sql4.equals(""))
                    sql = "SELECT DISTINCT actorname FROM movie_actors ORDER BY actorname";
                else
                    sql = "SELECT DISTINCT actorname FROM movie_actors WHERE movieid in ("+ sql4 +yearoperation+") ORDER BY actorname";
		
                System.out.println("Actors SQL -- "+sql);
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
                    actors.add(rs.getString(1));
		}
		stmt.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return actors;
	}
        
        public ArrayList<String> getDirectors(List<String> countries,List<String> genres ,String from, String to, String condition)
	{
		ArrayList<String> directors = new ArrayList<String>();
		String operation = "";
		String operation1 = "";
                String yearoperation = "";
                String sql4 = "";
		int count = 0;
		int cnt = 0;
                if(!from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+" AND year <= "+Integer.parseInt(to)+")";
                }else if(from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year <= "+Integer.parseInt(to)+")";
                }else if(!from.equals("") && to.equals("")){
                    yearoperation = "INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+")";
                }
		for(String genre : genres){
		count++;
		if(count == 1)
		operation = "SELECT movieID FROM movie_genres WHERE genre = '"+genre+"'";
		else
		operation = operation +" "+ condition + " genre = '"+genre+"'";
		}
		
		for(String country : countries){
                    cnt++;
                    if(cnt == 1)
                    operation1 = "SELECT movieID FROM movie_countries WHERE country = '"+country+"'";
                    else
                    operation1 = operation1 +" "+ condition + " country = '"+country+"'";
                }
		
                if(cnt!=0 && count!=0)
                    {sql4 = "(" +operation+")" + " INTERSECT "+ "("+operation1+")";}
		else
                    if(count!=0 && cnt==0)
                    {sql4 = "(" +operation+")" ;}
                    else if (cnt!=0 && count==0)
                    {sql4 = "(" +operation1 + ")" ;}
                
                String sql3 = "";
		if(sql4.equals(""))
                    sql3 = "SELECT DISTINCT directorname FROM movie_directors ORDER BY directorname";
                else
                    sql3 = "SELECT DISTINCT directorname FROM movie_directors WHERE movieid in ("+ sql4 + yearoperation +") ORDER BY directorname";
		
                System.out.println("Directors SQL -- "+sql3);
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql3);
		while(rs.next()){
                    directors.add(rs.getString(1));
		}
		stmt.close();
		} catch (SQLException e) {
                    e.printStackTrace();
		}
		
		return directors;
	
	}
	
        public ArrayList<String> getTags(List<String> countries, List<String> genres , List<String> actors, List<String> directors,String from,String to, String condition)
	{
		ArrayList<String> tags = new ArrayList<String>();
		String operation = "";
		String operation1 = "";
                String operation2 = "";
                String operation3 = "";
                String yearoperation = "";
                String sql4 = "";
                if(!from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+" AND year <= "+Integer.parseInt(to)+")";
                }else if(from.equals("") && !to.equals("")){
                    yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year <= "+Integer.parseInt(to)+")";
                }else if(!from.equals("") && to.equals("")){
                    yearoperation = "INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+")";
                }
                int genrecount = 0, countriescount = 0, actorscount = 0, directorscount=0;
		
		for(String genre : genres){
                    genrecount++;
                    if(genrecount == 1)
                        operation = "SELECT movieID FROM movie_genres WHERE genre = '"+genre+"'";
                    else
                        operation = operation +" "+ condition + " genre = '"+genre+"'";
		}
		for(String country : countries){
                    countriescount++;
                    if(countriescount == 1)
                        operation1 = "SELECT movieID FROM movie_countries WHERE country = '"+country+"'";
                    else
                        operation1 = operation1 +" "+ condition + " country = '"+country+"'";
                }
                if(actors.size()>0){
                    for(String actor : actors){
                        actorscount++;
                        if(actorscount ==1)
                            operation2 = "SELECT movieID FROM movie_actors WHERE actorname = '"+actor+"'";
                        else
                            operation2 = operation2 +" "+condition+" actorname = '"+actor+"'";
                    }
                }
                if(directors.size()>0){
                    for(String director : directors){
                        directorscount++;
                        if(directorscount ==1)
                            operation3 = "SELECT movieID FROM movie_directors WHERE directorname = '"+director+"'";
                        else
                            operation3 = operation3 +" "+condition+" actorname = '"+director+"'";
                    }
                }
		if(!operation.equals("") && !operation1.equals("")){
                    sql4 = "(" +operation+")" + " INTERSECT "+ "("+operation1+")";
                    if(!operation2.equals(""))
                        sql4 = sql4+" INTERSECT "+"("+operation2+")";
                    if(!operation3.equals(""))  
                        sql4 = sql4+" INTERSECT "+"("+operation3+")";
                }
                
		String sql3 = "";
                
                if(sql4.equals(""))
                    sql3 = "SELECT DISTINCT t.tagid, t.tagtext FROM tags t ORDER BY tagid";
                else
                    sql3 = "SELECT DISTINCT t.tagid, t.tagtext FROM tags t, movie_tags m WHERE m.movieid IN ("+ sql4 +yearoperation+") AND m.tagid = t.tagid ORDER BY tagid";
		System.out.println("Tags SQL -- "+sql3);
		Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql3);
		while(rs.next()){
                    String x = String.valueOf(rs.getInt(1))+", "+rs.getString(2);
                    tags.add(x);
		}
		stmt.close();
		} catch (SQLException e) {
		
		e.printStackTrace();
		}
		
		return tags;
	
	}
        //Get query for movies
        public String getMoviesQuery(List<String> countries, List<String> genres, List<String> actors, List<String> directors, List<String> tags,String from,String to, String condition){
            String operation = "";
            String operation1 = "";
            String operation2 = "";
            String operation3 = "";
            String operation4 = "";
            String yearoperation="";
            String sql4 = "";
            if(!from.equals("") && !to.equals("")){
                yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+" AND year <= "+Integer.parseInt(to)+")";
            }else if(from.equals("") && !to.equals("")){
                yearoperation = " INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year <= "+Integer.parseInt(to)+")";
            }else if(!from.equals("") && to.equals("")){
                yearoperation = "INTERSECT (SELECT DISTINCT movieid FROM movies WHERE year >= "+Integer.parseInt(from)+")";
            }
            int genrecount = 0, countriescount = 0, actorscount = 0, directorscount=0, tagscount = 0;

            for(String genre : genres){
                genrecount++;
                if(genrecount == 1)
                    operation = "SELECT movieID FROM movie_genres WHERE genre = '"+genre+"'";
                else
                    operation = operation +" "+ condition + " genre = '"+genre+"'";
            }
            for(String country : countries){
                countriescount++;
                if(countriescount == 1)
                    operation1 = "SELECT movieID FROM movie_countries WHERE country = '"+country+"'";
                else
                    operation1 = operation1 +" "+ condition + " country = '"+country+"'";
            }
            if(actors.size()>0){
                for(String actor : actors){
                    actorscount++;
                    if(actorscount ==1)
                        operation2 = "SELECT movieID FROM movie_actors WHERE actorname = '"+actor+"'";
                    else
                        operation2 = operation2 +" "+condition+" actorname = '"+actor+"'";
                }
            }
            if(directors.size()>0){
                for(String director : directors){
                    directorscount++;
                    if(directorscount ==1)
                        operation3 = "SELECT movieID FROM movie_directors WHERE directorname = '"+director+"'";
                    else
                        operation3 = operation3 +" "+condition+" actorname = '"+director+"'";
                }
            }
            if(tags.size()>0){
                for(String tag : tags){
                    tagscount++;
                    String t = tag.substring(0,tag.indexOf(","));
                    if(tagscount == 1)
                        operation4 = "SELECT movieID FROM movie_tags WHERE tagid = "+t;
                    else
                        operation4 = operation4 +" "+condition+" tagid = "+t;
                }
            }
            if(!operation.equals("") && !operation1.equals("")){
                sql4 = "(" +operation+")" + " INTERSECT "+ "("+operation1+")";
                if(!operation2.equals(""))
                    sql4 = sql4+" INTERSECT "+"("+operation2+")";
                if(!operation3.equals(""))  
                    sql4 = sql4+" INTERSECT "+"("+operation3+")";
                if(!operation4.equals(""))
                    sql4 = sql4+" INTERSECT "+"("+operation4+")";
            }
            String query = "";
            if(sql4.equals(""))
                query = "SELECT movieid,title FROM movies ORDER BY movieid";
            else    
                query = "SELECT movieid, title FROM movies WHERE movieid IN ("+sql4+yearoperation+") ORDER BY movieid";
            return query;
        }
        public ArrayList<String> getMovies(String query){
            ArrayList<String> movies = new ArrayList<String>();
            System.out.println("Movies SQL -- "+query);
            Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
                    String x = String.valueOf(rs.getInt(1))+", "+rs.getString(2);
                    movies.add(x);
		}
		stmt.close();
		} catch (SQLException e) {
		
		e.printStackTrace();
		}
            return movies;
        }
        public List<String> getMovieDetails(List<String> movies){
            List<String> moviedat = new ArrayList<String>();
            if(movies.size()>0){
                String movie = movies.get(0);
                String mid = movie.substring(0,movie.indexOf(","));
                String query = "SELECT movieid,title,year,rtAudienceRating,rtAudienceNumRatings FROM movies WHERE movieid = "+mid;
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    String x = Integer.toString(rs.getInt(1));
                    String y = Integer.toString(rs.getInt(3));
                    String z = Float.toString(rs.getFloat(4));
                    String a = Integer.toString(rs.getInt(5));
                    moviedat.add("Movie ID -- "+x);
                    moviedat.add("Movie Title -- "+ rs.getString(2));
                    moviedat.add("Release Year -- "+y);
                    moviedat.add("Avg RT Audience Rating -- "+z);
                    moviedat.add("Number of RT Rated Audience -- "+a);
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    String tem = "Genre -- ";
                    stmt=connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT genre FROM movie_genres WHERE movieid = "+mid);
                    while(rs.next()){
                        tem = tem+rs.getString(1)+", ";
                    }
                    moviedat.add(tem);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return moviedat;
        }
        public String getUsersQuery(List<String> tags, List<String> movies){
            String query = "";
            String operation = "";
            String operation1 = "";
            String condition = "OR";
            int moviescount = 0, tagscount = 0;

            for(String movie : movies){
                moviescount++;
                String m = movie.substring(0,movie.indexOf(","));
                if(moviescount == 1)
                    operation = "SELECT movieid FROM movies WHERE movieid = "+m;
                else
                    operation = operation +" "+ condition + " movieid = "+m;
            }
            for(String tag : tags){
                tagscount++;
                String t = tag.substring(0,tag.indexOf(","));
                if(tagscount == 1)
                    operation1 = "SELECT tagID FROM movie_tags WHERE tagid = "+t;
                else
                    operation1 = operation1 +" "+condition+" tagid = "+t;
            }
            if(!operation.equals("") && !operation1.equals("")){
                query = "SELECT userid from user_taggedmovies where movieid IN ("+ operation + ") AND tagid IN ("+ operation1 +") ORDER BY userid";
            }
            else if(operation.equals("") && !operation1.equals("")){
                query = "SELECT userid from user_taggedmovies where movieid IN ("+operation+") ORDER BY userid";
            }
            else if(!operation.equals("") && operation1.equals("")){
                query = "SELECT userid from user_taggedmovies where tagid IN ("+ operation1 +") ORDER BY userid";
            }else{
                query = "SELECT userid from user_taggedmovies ORDER BY userid";
            }
                
            return query;
        }
        public ArrayList<String> getUsers(String query){
            ArrayList<String> users = new ArrayList<String>();
            System.out.println("Users SQL -- "+query);
            Statement stmt = null;
		try {
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
                    String x = String.valueOf(rs.getInt(1));
                    users.add(x);
		}
		stmt.close();
		} catch (SQLException e) {
		
		e.printStackTrace();
		}
            return users;
        }
}