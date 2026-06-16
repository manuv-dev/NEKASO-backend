import java.sql.*;
import java.util.*;

public class DbQuery {
  public static void main(String[] args) throws Exception {
    String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://ep-restless-fog-alpk3fgn-pooler.c-3.eu-central-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require");
    String user = System.getenv().getOrDefault("DB_USERNAME", "neondb_owner");
    String password = System.getenv().getOrDefault("DB_PASSWORD", "npg_uWUmp7znV9Xc");

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      System.out.println("CONNECTED");
      DatabaseMetaData md = conn.getMetaData();
      try (ResultSet rs = md.getTables(null, "public", null, new String[]{"TABLE"})) {
        System.out.println("TABLES");
        while (rs.next()) {
          System.out.println(rs.getString("TABLE_NAME"));
        }
      }

      String[] queries = {
        "SELECT id, telephone, role FROM utilisateur ORDER BY id LIMIT 20",
        "SELECT id, telephone, role FROM users ORDER BY id LIMIT 20",
        "SELECT id, telephone, role FROM users ORDER BY id LIMIT 20",
        "SELECT id, nom, prenom, telephone FROM locataire ORDER BY id LIMIT 20",
        "SELECT id, montant_loyer FROM contrat_bail ORDER BY id LIMIT 20",
        "SELECT id, montant, contrat_id FROM paiement ORDER BY id LIMIT 20"
      };
      for (String sql : queries) {
        try {
          System.out.println("\nQUERY " + sql);
          try (PreparedStatement ps = conn.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData rmd = rs.getMetaData();
            int cols = rmd.getColumnCount();
            while (rs.next()) {
              List<String> values = new ArrayList<>();
              for (int i = 1; i <= cols; i++) values.add(rs.getObject(i).toString());
              System.out.println(String.join(" | ", values));
            }
          }
        } catch (Exception e) {
          System.out.println("ERROR: " + e.getMessage());
        }
      }
    }
  }
}
