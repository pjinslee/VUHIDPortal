/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package vuhid_tools.server;

import java.sql.*;



/**
 * @author Long Phan <long2@pdx.edu>
 *
 * This class handles the database connection. 
 */
public class DatabaseHandler
{
	private
		static Connection db_connection;
		static Statement stmt;
		static ResultSet rs;

	/**
	 * Connect to the database server.
	 */
	public void connect()
	{
		try
	    {
			Class.forName(Config.DATABASE_DRIVER);
			// only 1 connection
			if(db_connection == null)
			{
				db_connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_LOGIN, Config.DATABASE_PASSWORD);
			}
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	/**
	 * Connect to the database server.
	 * 
	 * @param query    The SQL query
	 */
	public void query(String query)
	{
		try
	    {
			if(stmt != null)
			{
				stmt.close();
			}
			if(rs != null)
			{
				rs.close();
			}
			stmt = db_connection.createStatement();
			String[] query_type = query.split("\\s+", 2);
			if(query_type[0].toUpperCase().equals("SELECT"))
			{
				rs = stmt.executeQuery(query);
				rs.next();
			}
			else
			{
				stmt.executeUpdate(query);
			}
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	/**
	 * Disconnect from the database server.
	 */
	public void disconnect()
	{
		try { rs.close(); } catch (Exception e) { /* ignored */ } 
	    try { stmt.close(); } catch (Exception e) { /* ignored */ } 
	    try { db_connection.close(); } catch (Exception e) { /* ignored */ } 
	}
	
	/**
	 * Get the connection.
	 * 
	 * @return	Returns a Connection object.
	 */
	public Connection getConnection()
	{
		return db_connection;
	}
	
	/**
	 * Get the result set.
	 * 
	 * @return	Returns a ResultSet object.
	 */
	public ResultSet getResult()
	{
		return rs;
	}
}