package com.tsystem.crawler.service;

import com.tsystem.crawler.model.CrawlDetail;
import com.tsystem.crawler.model.FinalInteger;
import com.tsystem.crawler.model.PageDetail;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class CrawlService {

    private static final int MAX_PAGES_TO_SEARCH = 10;
    private Set<String> pagesVisited = new HashSet<String>();
    private Set<String> pagesToVisit = new HashSet<String>();
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private static final Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private boolean isUrlImage(String url) {
        if (filters.matcher(url).matches()) {
            System.out.println(url + "is image!!");
            return true;
        }
        return false;
    }

    public CrawlDetail search(String url) {

        CrawlDetail crawlDetail = new CrawlDetail();
        List<PageDetail> pageDetailList = new ArrayList<>();
        FinalInteger counter = new FinalInteger(0);
        Set<String> linksSet = new HashSet<>();
        try {
            Document document = fetchDocument(url);
            if (document != null) {
                Elements linksOnPage = document.select("a[href]");
                linksOnPage.forEach(link -> {
                    String absUrl = link.absUrl("href");
                    if (!linksSet.contains(absUrl)) {
                        linksSet.add(absUrl);
                        if (isUrlImage(absUrl)) {
                            counter.increment();
                        } else if (!absUrl.contains("mailto:")) {
                            PageDetail pageDetail = new PageDetail();
                            FinalInteger imageCountOnPage = new FinalInteger(0);
                            Document doc = fetchDocument(absUrl);
                            if (doc != null) {
                                pagesVisited.add(absUrl);
                                Elements pageLinks = doc.select("a[href]");
                                pageLinks.forEach(link1 -> {
                                    String absUrl1 = link1.absUrl("href");
                                    if (isUrlImage(absUrl1)) {
                                        counter.increment();
                                        imageCountOnPage.increment();
                                    }
                                });
                                pageDetail.setPage_title(doc.title());
                                pageDetail.setPage_link(absUrl);
                                pageDetail.setImage_count(imageCountOnPage.getCounter());
                                pageDetailList.add(pageDetail);
                            }
                        }
                    }
                });
            }
            crawlDetail.setTotal_links(pagesVisited.size());
            crawlDetail.setTotal_images(counter.getCounter());
            crawlDetail.setDetails(pageDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return crawlDetail;
    }

    private Document fetchDocument(final String url) {
        Document document = null;
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            document = connection.get();
        } catch (Exception e) {}
        return document;
    }
}
