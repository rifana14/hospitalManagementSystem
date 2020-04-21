
import java.sql.*;

public class HospitalModel { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3000/hospital", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insert( String hosName, String regDate, String hosType , String hosAddress) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into hopital_table(`hospitalID`,`hospitalName`,`hospitalRegistereDate`,`hospitalType`,`hospitalAddress`)"
					 + " values (?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, hosName);
			preparedStmt.setDouble(3, Double.parseDouble(regDate));
			preparedStmt.setString(4, hosType);
            preparedStmt.setString(5, hosAddress);
        

// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String hospitalItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Hospital Email</th><th>Hospital Name</th><th>Hospital Age</th><th>Hospital Diseas</th>><th>Hospital Address</th>><th>Hospital Gender</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from hospital_table";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String hospitalID = Integer.toString(rs.getInt("hospitalID"));
				String hospitalName = rs.getString("hospitalName");
				String hospitalRegistereDate = Double.toString(rs.getDouble("hospitalRegistereDate"));
				String hospitalType = rs.getString("hospitalType");
                String hospitalAddress = rs.getString("hospitalAddress");
               
				// Add into the html table
				output += "<td>" + hospitalName + "</td>";
				output += "<td>" + hospitalRegistereDate+ "</td>";
				output += "<td>" + hospitalType+ "</td>";
                output += "<td>" + hospitalAddress+ "</td>";
               

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Hospital.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"hospitalID \" type=\"hidden\" value=\"" + hospitalID + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}


	public String updateItem(String ID, String hosName, String regDate, String hosType , String hosAddress) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE hopital_table SET hospitalName=?,hospitalRegistereDate=?,hospitalType=?,hospitalAddress=? WHERE hospitalID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, hosName);
			preparedStmt.setDouble(2, Double.parseDouble(regDate));
			preparedStmt.setString(3, hosType);
            preparedStmt.setString(4, hosAddress);
          


			preparedStmt.setInt(6, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(String hospitalID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from item_table where hospitalID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hospitalID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}