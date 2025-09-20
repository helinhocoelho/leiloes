
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "99@Hdwd99");

        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Driver do MySQL não encontrado: " + erro.getMessage());
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco: " + erro.getMessage());
        }
        return conn;
    }
}
