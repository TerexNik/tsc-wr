package ru.tsc.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsc.dao.ScanDataManager;
import ru.tsc.model.Scan;

import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping(value = "/scan")
public class ScanService {

    @Autowired
    ScanDataManager scanDataManager;

    @RequestMapping(value = "/scan/{id}", method = RequestMethod.GET)
    public Scan getScan(@PathVariable Long id) { // а тут формат возвращаемого значания должен быть byte[] ?
        return scanDataManager.getScan(id);
    }

    @RequestMapping(method = RequestMethod.POST)
//    void addScan(@RequestBody Scan scan) {
//        scanDataManager.addScan(scan);
//    }
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

    @RequestMapping(value = "/scan", method = RequestMethod.PUT)
    public void updateScan(@RequestBody Scan scan) {
        scanDataManager.mergeScan(scan);
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public void removeScan(@RequestBody Scan scan) {
//        scanDataManager.removeScan(scan);
//    }

}
