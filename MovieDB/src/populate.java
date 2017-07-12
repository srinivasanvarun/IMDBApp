import java.io.*;
import java.sql.*;

public class populate {
	Connection  connection = null; 
	public static void main(String[] args) throws SQLException, IOException {
            populate p = new populate();
            p.insertMovies(args[0]);
            p.insertTags(args[1]);
            p.insertMovieCountries(args[2]);
            p.insertMovieGenre(args[3]);
            p.insertMovieTags(args[4]);
            p.insertMovieLocation(args[5]);
            p.insertMovieActors(args[6]);
            p.insertMovieDirectors(args[7]);
            p.insertUserTaggedMovies(args[8]);
	}
	
	public void insertMovies(String filepath) throws SQLException, IOException
	{   
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            PreparedStatement movies_sql = null; 
            try { 
                FileReader file = new FileReader(filepath);
                BufferedReader br = new BufferedReader(file);
                br.readLine();
                String fileData = null;
                int i=0;
                while((fileData = br.readLine())!= null){
                    i++;
                    String[] data = fileData.split("\t");
                    String rtAllCriticsRating = data[7];
                    String rtAllCriticsNumReviews = data[8];	
                    String rtAllCriticsNumFresh = data[9];
                    String rtAllCriticsNumRotten = data[10];	
                    String rtAllCriticsScore = data[11];
                    String rtTopCriticsRating = data[12];
                    String rtTopCriticsNumReviews = data[13];
                    String rtTopCriticsNumFresh = data[14];
                    String rtTopCriticsNumRotten = data[15];
                    String rtTopCriticsScore = data[16];
                    String rtAudienceRating = data[17];
                    String rtAudienceNumRatings = data[18];
                    String rtAudienceScore = data[19];
                    String rtPictureURL = data[20];
                    if(movies_sql == null){
                        String sql = "INSERT INTO MOVIES VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        movies_sql = connection.prepareStatement(sql);
                    }
                    movies_sql.setInt(1, Integer.parseInt(data[0]));
                    movies_sql.setString(2, data[1]);
                    movies_sql.setInt(3, Integer.parseInt(data[2]));
                    movies_sql.setString(4, data[3]);
                    movies_sql.setString(5, data[4]);
                    movies_sql.setInt(6, Integer.parseInt(data[5]));
                    movies_sql.setString(7, data[6]);
                    if(!rtAllCriticsRating.equals("\\N")) movies_sql.setFloat(8, Float.parseFloat(rtAllCriticsRating));
                    if(!rtAllCriticsNumReviews.equals("\\N"))movies_sql.setInt(9, Integer.parseInt(rtAllCriticsNumReviews));
                    if(!rtAllCriticsNumFresh.equals("\\N"))movies_sql.setInt(10, Integer.parseInt(rtAllCriticsNumFresh));
                    if(!rtAllCriticsNumRotten.equals("\\N"))movies_sql.setInt(11, Integer.parseInt(rtAllCriticsNumRotten));
                    if(!rtAllCriticsScore.equals("\\N"))movies_sql.setInt(12, Integer.parseInt(rtAllCriticsScore));
                    if(!rtTopCriticsRating.equals("\\N"))movies_sql.setFloat(13, Float.parseFloat(rtTopCriticsRating));
                    if(!rtTopCriticsNumReviews.equals("\\N"))movies_sql.setInt(14, Integer.parseInt(rtTopCriticsNumReviews));
                    if(!rtTopCriticsNumFresh.equals("\\N"))movies_sql.setInt(15, Integer.parseInt(rtTopCriticsNumFresh));
                    if(!rtTopCriticsNumRotten.equals("\\N"))movies_sql.setInt(16, Integer.parseInt(rtTopCriticsNumRotten));
                    if(!rtTopCriticsScore.equals("\\N"))movies_sql.setInt(17, Integer.parseInt(rtTopCriticsScore));
                    if(!rtAudienceRating.equals("\\N"))movies_sql.setFloat(18, Float.parseFloat(rtAudienceRating));
                    if(!rtAudienceNumRatings.equals("\\N"))movies_sql.setInt(19, Integer.parseInt(rtAudienceNumRatings));
                    if(!rtAudienceScore.equals("\\N"))movies_sql.setInt(20, Integer.parseInt(rtAudienceScore));
                    if(!rtPictureURL.equals("\\N"))movies_sql.setString(21, rtPictureURL);
                    movies_sql.executeUpdate();
                }
                System.out.println(i+" records pushed in MOVIES table");
            }catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                connection.close();
            }
	}
	
	public void insertTags(String filePath) throws SQLException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            PreparedStatement movies_sql = null; 
            try {
                FileReader file = new FileReader(filePath);
                BufferedReader br = new BufferedReader(file);
                br.readLine();
                String fileData = null;
                int i=0;
                while((fileData = br.readLine()) != null){
                    if(movies_sql == null){
                        String sql = "INSERT INTO TAGS VALUES(?,?)";
                        movies_sql = connection.prepareStatement(sql);
                    }
                    i++;
                    String[] data = fileData.split("\t");
                    String value = data[1];
                    movies_sql.setInt(1, Integer.parseInt(data[0]));
                    movies_sql.setString(2, value);
                    movies_sql.executeUpdate();
                }
                System.out.println(i+" records pushed in TAGS table");
            } catch (Exception e) {
                    e.printStackTrace();
            } finally{
                connection.close();
            }
        }
        public void insertMovieCountries(String filepath) throws SQLException, IOException
	{
		Connection  connection = null;
		connection =  dbconnector.getConnection();
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String strline=null;
		PreparedStatement businessSQL = null;
		br.readLine();
                int i=0;
		while((strline=br.readLine()) != null)
		{
                    if (businessSQL == null){
	                String sql = "INSERT INTO  MOVIE_COUNTRIES VALUES(?,?)";
	                businessSQL = connection.prepareStatement(sql);
                    }
                    i++;
                    String lines[]= strline.split("\t");
                    Integer num1;
                    String str;
                    if(lines.length==2){ 
                        num1 =  Integer.parseInt(lines[0]);
                        str =   lines[1];
                    }
                    else{
                        num1 =  Integer.parseInt(lines[0]);
                        str="";
                    }	
                    businessSQL.setInt(1,num1);
                    businessSQL.setString(2,str);
                    businessSQL.executeUpdate();	
		}
                System.out.println(i+" records pushed in MOVIE_COUNTRIES table");
		connection.close();
	}
        public void insertMovieGenre(String filepath) throws SQLException, IOException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            Statement stmt = null;
            stmt = (Statement) connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String strline=null;
            PreparedStatement businessSQL = null;
            br.readLine();
            int i=0;
            while((strline=br.readLine()) != null)
            {
                if (businessSQL == null){
                    String sql = "INSERT INTO MOVIE_GENRES VALUES(?,?)";
                    businessSQL = connection.prepareStatement(sql);
                }
                i++;
                String lines[]= strline.split("\t");
                Integer num1 =  Integer.parseInt(lines[0]);
                String num2 =   lines[1];
                businessSQL.setInt(1,num1);
                businessSQL.setString(2,num2);
                businessSQL.executeUpdate();
            }
            System.out.println(i+" records pushed in MOVIE_GENRES table");
            connection.close();
	}
        public void insertMovieTags(String filepath) throws SQLException, IOException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            Statement stmt = null;
            stmt = (Statement) connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String strline=null;
            PreparedStatement businessSQL = null;
            br.readLine();
            int i=0;
            while((strline=br.readLine()) != null)
            {
                if (businessSQL == null){
                    String sql = "INSERT INTO MOVIE_TAGS VALUES(?,?,?)";
                    businessSQL = connection.prepareStatement(sql);
                }
                i++;
                String lines[]= strline.split("\t");
                Integer num1 =  Integer.parseInt(lines[0]);
                Integer num2 =  Integer.parseInt(lines[1]);
                Integer num3 =  Integer.parseInt(lines[2]);
                businessSQL.setInt(1,num1);
                businessSQL.setInt(2,num2);
                businessSQL.setInt(3,num3);
                businessSQL.executeUpdate();
            }
            System.out.println(i+" records pushed in MOVIE_TAGS table");
            connection.close();
	}
	public void insertMovieLocation(String filepath) throws SQLException, IOException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            Statement stmt = null;
            stmt = (Statement) connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String strline=null;
            PreparedStatement businessSQL = null;
            br.readLine();
            int i=0;
            while((strline=br.readLine()) != null)
            {
                if (businessSQL == null){
                    String sql = "INSERT INTO MOVIE_LOCATIONS VALUES(?,?,?,?,?)";
                    businessSQL = connection.prepareStatement(sql);
                }
                String lines[]= strline.split("\t");
                Integer id = null;
                String country=null, state=null, city=null, street=null;
                if(lines.length>1){
                    for(int j=0;j<lines.length;j++){
                        if(j==0)
                            id=Integer.parseInt(lines[j]);
                        else if(j==1)
                            country = lines[j];
                        else if(j==2)
                            state = lines[j];
                        else if(j==3)
                            city = lines[j];
                        else if(j==4)
                            street = lines[j];
                    }
                }
                if(id!=null) businessSQL.setInt(1,id);
                if(country!=null) businessSQL.setString(2,country);
                else businessSQL.setString(2,"");
                if(state!=null) businessSQL.setString(3,state);
                else businessSQL.setString(3,"");
                if(city!=null) businessSQL.setString(4,city);
                else businessSQL.setString(4,"");
                if(street!=null) businessSQL.setString(5,street);
                else businessSQL.setString(5,"");
                if(id!=null){
                    i++;
                    businessSQL.executeUpdate();
                }
            }
            System.out.println(i+" records pushed in MOVIE_LOCATIONS table");
            connection.close();	
        }
	public void insertMovieActors(String filePath) throws SQLException, IOException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();	
            PreparedStatement movies_sql = null; 
            FileReader file = new FileReader(filePath);
            BufferedReader br = new BufferedReader(file);
            br.readLine();
            String fileData = null;
            int i=0;
            while((fileData = br.readLine()) != null){
                i++;
                String[] data = fileData.split("\t");
                Integer movieID = Integer.parseInt(data[0]);
                String actorID = data[1];
                String actorName = data[2];
                Integer ranking = Integer.parseInt(data[3]);
                if(movies_sql == null){
                    String sql = "INSERT INTO MOVIE_ACTORS VALUES(?,?,?,?)";
                    movies_sql = connection.prepareStatement(sql);
                }
                movies_sql.setInt(1, movieID);
                movies_sql.setString(2, actorID);
                movies_sql.setString(3, actorName);
                movies_sql.setInt(4, ranking);
                movies_sql.executeUpdate();
            }
            System.out.println(i+" records pushed in MOVIE_ACTORS table");
            connection.close();
	}
	public void insertMovieDirectors(String filepath) throws SQLException, IOException
	{
            Connection  connection = dbconnector.getConnection();
            Statement stmt = connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String strline=null;
            PreparedStatement businessSQL = null;
            br.readLine();
            int i=0;
            while((strline=br.readLine()) != null)
            {
                i++;
                String lines[]= strline.split("\t");
                Integer num1 =  Integer.parseInt(lines[0]);
                String num2 =   lines[1];
                String num3 =   lines[2];
                if (businessSQL == null){
                    String sql = "INSERT INTO MOVIE_DIRECTORS VALUES(?,?,?)";
                    businessSQL = connection.prepareStatement(sql);
                }
                businessSQL.setInt(1,num1);
                businessSQL.setString(2,num2);
                businessSQL.setString(3,num3);
                businessSQL.executeUpdate();
            }
            System.out.println(i+" records pushed in MOVIE_DIRECTORS table");
            connection.close();
	}
	public void insertUserTaggedMovies(String filepath) throws SQLException, IOException
	{
            Connection  connection = null;
            connection =  dbconnector.getConnection();
            Statement stmt = null;
            stmt = (Statement) connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String strline=null;
            PreparedStatement businessSQL = null;
            br.readLine();
            int i=0;
            while((strline=br.readLine()) != null)
            {
                i++;
                String lines[]= strline.split("\t");
                Integer userid =  Integer.parseInt(lines[0]);
                Integer movieid =  Integer.parseInt(lines[1]);
                Integer tagid =  Integer.parseInt(lines[2]);
                if (businessSQL == null){
                    String sql = "INSERT INTO  user_taggedmovies VALUES(?,?,?)";
                    businessSQL = connection.prepareStatement(sql);
                }
                businessSQL.setInt(1,userid);
                businessSQL.setInt(2,movieid);
                businessSQL.setInt(3,tagid);

                businessSQL.executeUpdate();
            }
            System.out.println(i+" records pushed in USER_TAGGEDMOVIES table");
            connection.close();
	}
}
