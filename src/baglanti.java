import java.sql.*;
import java.util.Scanner;

public class baglanti {
    private String kullaniciAdi = "root";
    private String parola = "";

    private String dbIsmi = "demo";
    private String host = "localhost";
    private int port = 3306;

    private Connection con = null;

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public void preparedCalisanlariGetir() {
        String sorgu = "Select * From calisanlar";//and ad like ?
        try {
            preparedStatement = con.prepareStatement(sorgu);

            //preparedStatement.setString(2, "M%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String email = rs.getString("email");
                System.out.println("ID: " + id +" Ad: " + ad + " " + "Soyad: " + soyad + " " + "Email: " + email + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void calisanSil() {
        try {
            statement = con.createStatement();

            String sorgu = "Delete from calisanlar where id > 3";
            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " kadar veri etkilendi.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void calisanGuncelle() {
        try {
            statement = con.createStatement();

            String sorgu = "Update calisanlar Set email = 'example@gmail.com' where id > 3";

            statement.executeUpdate(sorgu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calisanlariGetir() {
        String sorgu = "Select * From calisanlar";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String email = rs.getString("email");

                System.out.println("ID: " + id + " " + "Ad: " + ad + " " + "Soyad: " + soyad + " " + "EMail: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calisanEkle() {

        try {
            String ad = "Semih2";
            String soyad = "Akta??2";
            String email = "semihakta??2@gmail.com";
            String sorgu = "Insert Into calisanlar (ad,soyad,email) VAlUES (?,?,?)";
            preparedStatement = con.prepareStatement(sorgu);

            preparedStatement.setString(1,ad);
            preparedStatement.setString(2,soyad);
            preparedStatement.setString(3,email);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try {
            statement = con.createStatement();

            String ad = "Semih";
            String soyad = "Akta??";
            String email = "semihakta??@gmail.com";

            String sorgu = "Insert Into calisanlar (ad,soyad,email) VALUES (" + "'" + ad + "'," + "'" + soyad + "'," + "'" + email + "')";
            statement.executeUpdate(sorgu);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }

    public baglanti() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbIsmi + "?useUnicode=true&characterEncoding=utf8";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Bulunamad??.");
        }
        try {
            con = DriverManager.getConnection(url, kullaniciAdi, parola);
            System.out.println("Ba??lant?? Ba??ar??l??.");
        } catch (SQLException e) {
            System.out.println("Ba??lant?? Ba??ar??s??z.");
        }

    }
    public void commitVeRollback () {
        Scanner scanner = new Scanner(System.in);
        try {
            con.setAutoCommit(false);
            String sorgu1 = "Delete from calisanlar where id > 3";
            String sorgu2 = "Update calisanlar set email  = 'denizlibilgehan@gmail.com' where id = 1";

            System.out.println("G??ncellenmeden ??nce");
            preparedCalisanlariGetir();
            Statement statement = con.createStatement();

            statement.executeUpdate(sorgu1);
            statement.executeUpdate(sorgu2);
            System.out.print("I??lemleriniz kaydedilsin mi?: ");
            String cevap = scanner.nextLine();

            if (cevap.equals("yes")) {
                con.commit();
                preparedCalisanlariGetir();
                System.out.println("Veritaban?? g??ncellendi");
            }else {
                con.rollback();
                System.out.println("Veritaban?? g??ncellenmesi iptal edildi.");
                preparedCalisanlariGetir();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        baglanti baglanti = new baglanti();
        /*System.out.println("Eklenmeden ??nce.....");
        baglanti.calisanlariGetir();
        System.out.println("**************************");*/
        //baglanti.calisanEkle();
        //baglanti.calisanGuncelle();
        //baglanti.calisanSil();
        //baglanti.preparedCalisanlariGetir();
        //baglanti.calisanlariGetir();
        //baglanti.preparedCalisanlariGetir();
        baglanti.commitVeRollback();

    }
}
