package cz.cvut.fit.skorpste.dip.crawler.crawler.common;

/**
 * Interface of component which crawls a resource
 * Created by stopka on 3.12.14.
 */
public interface ICrawler {
    /**
     * Does the crawling
     * @throws CrawlerException when anything goes wrong
     */
    public void crawl() throws CrawlerException;
}
