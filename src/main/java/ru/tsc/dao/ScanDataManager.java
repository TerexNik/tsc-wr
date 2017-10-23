package ru.tsc.dao;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tsc.model.Scan;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.rowset.serial.SerialBlob;


//import java.io.File;
//import java.io.FileInputStream;
//import java.sql.*;

@Repository
@DependsOn(value = "jpaTransactionManager")
public class ScanDataManager {
   /* public static void main(String[] args) {

        Connection connection = null;
        String url = "jdbc:oracle:thin:@ 89.108.84.144:1521/BPM8";
        String name = "test01";
        String password = "test01";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("Соединение установлено");
            Statement statement;

            statement = connection.createStatement();

//            ResultSet rset = statement.executeQuery("SELECT * FROM test01.WR_SCAN");
//            while (rset.next ()) {
                File file = new File("scan.jpg");
                byte[] bytes = new byte[(int) file.length()]; //file.getBytes();
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bytes);
                    fileInputStream.close();
                } catch (Exception e) {}

                Blob blob = new SerialBlob(bytes);
                Scan scan = new Scan("name", "format", blob);
            System.out.println(bytes);
            System.out.println(blob);
            System.out.println(scan);
//                CHAR id = (CHAR) ((OracleResultSet)rset).getOracleObject (1);
//                CHAR department = (CHAR) ((OracleResultSet)rset).getOracleObject (2);
//                System.out.println(id + " " + department);
//            }
            ScanDataManager scanDataManager = new ScanDataManager();
            scanDataManager.addScan(scan);

        } catch (Exception ex) { ex.printStackTrace();
        } finally { if (connection != null) {try {connection.close();} catch (SQLException ex) {} } }

    }*/

/*    public static void main(String[] args) {

        public void addScan(@RequestParam("scanfile") MultipartFile scanfile){
//    public void addScan(@RequestBody Scan scan, @RequestParam("scanfile") MultipartFile scanfile){
            if (!scanfile.isEmpty()) {
                try {
                    byte[] bytes = scanfile.getBytes();
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
//                stream.write(bytes);
//                stream.close();

                    Scan scan = new Scan("fileName", "fileFormat", new SerialBlob(bytes));
                    scanDataManager.addScan(scan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }*/


    @PersistenceContext(unitName = "tscPersistentUnit")
    private EntityManager entityManager;

    @Transactional
    public Scan getScan(Long id) {
        try {
            return entityManager.createQuery("select a from Scan a where a.id = :id", Scan.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void addScan(Scan scan) {
//        entityManager.persist(scan);
        entityManager.merge(scan);
    }

    @Transactional
    public void mergeScan(Scan scan) {
        entityManager.merge(scan);
    }

//    @Transactional
//    public void removeScan(Scan scan) {
//        entityManager.remove(scan);
//    }

}
