package Servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Jawmie on 07/12/2015.
 */

@WebServlet("/myServlet")
public class formServlet extends HttpServlet {
    private Connection connection;
    private PreparedStatement /*updateVotes, totalVotes,*/ result, insertdata;

    // set up database connection and prepare SQL statements
    public void init( ServletConfig config )
            throws ServletException
    {
        // attempt database connection and create PreparedStatements
        try {
         /*Class.forName( "COM.cloudscape.core.RmiJdbcDriver" );
         connection = DriverManager.getConnection(
            "jdbc:rmi:jdbc:cloudscape:animalsurvey" );*/

            String driver = "com.mysql.jdbc.Driver";
            //String url = "jdbc:mysql://localhost:3306/animalsurvey";

            Class.forName( driver );
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/information", "root", "SafePandaw9210!!");
            // PreparedStatement to add one to vote total for a
            // specific animal
            /*updateVotes =
                    connection.prepareStatement(
                            "UPDATE surveyresults SET votes = votes + 1 " +
                                    "WHERE id = ?"
                    );

            // PreparedStatement to sum the votes
            totalVotes =
                    connection.prepareStatement(
                            "SELECT sum( votes ) FROM surveyresults"
                    );*/
            insertdata =
                    connection.prepareStatement(
                            "INSERT INTO names (firstName, lastName) " +
                                           "VALUES ( ? , ? )" );

            // PreparedStatement to obtain surveyoption table's data
            result =
                    connection.prepareStatement(
                            "SELECT firstName, lastName " +
                                    "FROM names ORDER BY personID"
                    );
        }

        // for any exception throw an UnavailableException to
        // indicate that the servlet is not currently available
        catch ( Exception exception ) {
            exception.printStackTrace();
            throw new UnavailableException( exception.getMessage() );
        }

    }  // end of init method

    // process "post" request from client
    protected void doPost( HttpServletRequest request,
                           HttpServletResponse response )
            throws ServletException, IOException
    {

        String firstName = request.getParameter( "firstname" );
        String lastName = request.getParameter( "lastname" );

        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();

        //send data to database
        try{
            insertdata.setString( 1, firstName);
            insertdata.setString( 2, lastName );
            insertdata.executeUpdate();
        }
        // if database exception occurs, return error page
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            out.println( "<title>Error</title>" );
            out.println( "</head>" );
            out.println( "<body><p>Database error occurred. " );
            out.println( "Try again later.</p></body></html>" );
            out.close();
        }

        // send XHTML page to client

        // start XHTML document
        out.println( "<?xml version = \"1.0\"?>" );

        out.println( "<!DOCTYPE html PUBLIC \"-//W3C//DTD " +
                "XHTML 1.0 Strict//EN\" \"http://www.w3.org" +
                "/TR/xhtml1/DTD/xhtml1-strict.dtd\">" );

        out.println(
                "<html xmlns = \"http://www.w3.org/1999/xhtml\">" );

        // head section of document
        out.println( "<head>" );
        out.println(
                "<title>Processing post requests with example data</title>" );
        out.println( "</head>" );

        // body section of document
        out.println( "<body>" );
        out.println( "<h1>Hello " + firstName + " " + lastName + ",<br />" );
        out.println( "Welcome to Form!</h1>" );
        out.println( "Data saved is as follows");
        out.println( "<h2>1 Row and 2 Columns:</h2>");
        out.println( "<table><tr><td>" + firstName + "</td><td>" + lastName + "</td></tr></table>");
        out.println( "<h3>SUCCESS!!!</h3>" );
        out.println( "<form action = \"/myServlet\" method=\"post\">");
        out.println( "<INPUT TYPE=\"button\" onClick=\"history.go(-1)\" VALUE=\"Add Another\"></form>" );
        out.println( "</body>" );

        // end XHTML document
        out.println( "</html>" );
        out.close();  // close stream to complete the page
    }

    // close SQL statements and database when servlet terminates
    public void destroy()
    {
        // attempt to close statements and database connection
        try {
            //updateVotes.close();
            //totalVotes.close();
            insertdata.close();
            result.close();
            //connection.close();
        }

        // handle database exceptions by returning error to client
        catch( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
    }  // end of destroy method
}
