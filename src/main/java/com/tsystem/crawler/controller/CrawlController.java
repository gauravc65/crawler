package com.tsystem.crawler.controller;

import com.tsystem.crawler.model.CrawlDetail;
import com.tsystem.crawler.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawlController")
public class CrawlController {

    @Autowired
    private CrawlService crawlService;

    @GetMapping(value = "/crawl")
    public ResponseEntity<CrawlDetail> crawlURI(@RequestParam String url) {
        CrawlDetail crawlDetail = crawlService.search(url);
        return new ResponseEntity<CrawlDetail>(crawlDetail, HttpStatus.OK);
    }
}
