package ru.tsc.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsc.dao.ScanDataManager;
import ru.tsc.model.Scan;

import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/scan")
public class ScanService {

    @Autowired
    ScanDataManager scanDataManager;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getScan(@PathVariable Long id) throws SQLException {
        Scan scan = scanDataManager.getScan(id);
        String format = scan.getFormat();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(format)); //MediaType.APPLICATION_OCTET_STREAM
//        httpHeaders.setContentLength();

        Blob blob = scan.getScanfile();
//        Path path = Path.get()
        byte[] bytes = blob.getBytes(1, (int) blob.length());//Files.readAllBytes(path);

        return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
    }


    @RequestMapping(value = "/uploadDocumentFile", method = RequestMethod.POST, consumes = "application/octet-stream", produces = "application/json;charset=utf-8")
    public void addScan(@RequestBody byte[] bytes, @RequestParam String fileName) throws SQLException{
//    public void addScan(@RequestBody Scan scan, @RequestParam("scanfile") MultipartFile scanfile){
        Blob blob = new SerialBlob(bytes);

        Scan scan = new Scan(fileName, "format", blob);

        scanDataManager.addScan(scan);

//        String name = "scan";
//        if (!scanfile.isEmpty()) {
//            try {
//                byte[] bytes = scanfile.getBytes();
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
//                stream.write(bytes);
//                stream.close();
//
//                Scan scan = new Scan("fileName", "fileFormat", new SerialBlob(bytes));
//                scanDataManager.addScan(scan);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    //-------------------------------------------------------------------------------------------

/*
    @RequestMapping(value = "/{documentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getDocumentFile(@PathVariable String documentId) {
        byte[] resultBody = new byte[]{}; //получаем файл из базы
        ResponseEntity<byte[]> result = new ResponseEntity<>(resultBody, HttpStatus.OK);
        return result;
    }
*/

/*
    @RequestMapping(value = "/uploadDocumentFile", method = RequestMethod.POST, consumes = "application/octet-stream", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String uploadDocumentFile(@RequestBody byte[] bytes, @RequestParam String fileName) {
        if (bytes.length != 0) {
            // сохраняем файл
            return "fileId";
        } else {
            System.out.println("File not found");
            return null;
        }
    }
*/

    //-------------------------------------------------------------------------------------------



/*    @RequestMapping(method = RequestMethod.POST)
    public void addScan(@RequestParam("scanfile") MultipartFile scanfile){
//    public void addScan(@RequestBody Scan scan, @RequestParam("scanfile") MultipartFile scanfile){
        String name = "scan";
        if (!scanfile.isEmpty()) {
            try {
                byte[] bytes = scanfile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();

                Scan scan = new Scan("fileName", "fileFormat", new SerialBlob(bytes));
                scanDataManager.addScan(scan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/

    @RequestMapping(value = "/scan", method = RequestMethod.PUT)
    public void updateScan(@RequestBody Scan scan) {
        scanDataManager.mergeScan(scan);
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public void removeScan(@RequestBody Scan scan) {
//        scanDataManager.removeScan(scan);
//    }

}
