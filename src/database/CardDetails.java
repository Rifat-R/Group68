package src.database;
import java.text.SimpleDateFormat;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;
import java.sql.*;


public class CardDetails {

    public static void addCardDetailToDB(Integer userID, Integer cardNumber, String expiryDate, Integer securityCode, String cardTypeName) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        java.util.Date parsedDate = dateFormat.parse(expiryDate);

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

        EasyDatabase db = new EasyDatabase();
        
        try{
            User user = new User(userID);
            String salt = user.getSalt();
            String hashedCardNumber = Encryption.generateHash(cardNumber.toString(), salt);
            String hashedSecurityCode = Encryption.generateHash(securityCode.toString(), salt);


            String insertSQL = "UPDATE Users SET cardNumber = ?, expiryDate = ?, securityCode = ?, cardTypeName = ? WHERE id = ?";
            PreparedStatement preparedStatement = db.con.prepareStatement(insertSQL);
            preparedStatement.setString(1, hashedCardNumber);
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setString(3, hashedSecurityCode);
            preparedStatement.setString(4, cardTypeName);
            preparedStatement.setInt(5, userID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
        catch (NoSuchAlgorithmException e) {e.printStackTrace();}
        finally {db.close();}
    }


}