package cz.cvut.fit.skorpste.dip.crawler;

import cz.cvut.fit.skorpste.dip.crawler.crawler.CleanerFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.CrawlerFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.IndexerFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import org.apache.log4j.Logger;

/**
 * Crawler main class
 * Created by stopka on 2.12.14.
 */
public class Main {
    static Logger logger=Logger.getLogger(Main.class);

    /**
     * Main metod
     * @param args no arguments are processed. App is controled using -D properties
     */
    public static void main(String[] args) {
        logger.info("Index update started");

        Index index=IndexFactory.create();
        ICrawler crawler;

        crawler= new CleanerFactory(index).get();
        logger.info("Cleaning index phase started");
        crawler.crawl();

        crawler= new IndexerFactory(index).get();
        logger.info("Adding to index phase started");
        crawler.crawl();

        logger.info("Index update finished");
    }
}
