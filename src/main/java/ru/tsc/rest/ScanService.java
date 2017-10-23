package ru.tsc.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsc.dao.ScanDataManager;
import ru.tsc.model.Scan;

@RestController
@RequestMapping(value = "/scan")
public class ScanService {

    @Autowired
    ScanDataManager scanDataManager;

    @RequestMapping(value = "/scan/{id}", method = RequestMethod.GET)
    public Scan getScan(@PathVariable Long id) {
        return scanDataManager.getScan(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    void addScan(@RequestBody Scan scan) {
        scanDataManager.addScan(scan);
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
