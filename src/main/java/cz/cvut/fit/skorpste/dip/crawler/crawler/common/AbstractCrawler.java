package cz.cvut.fit.skorpste.dip.crawler.crawler.common;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;

/**
 * Genral crawler implementation
 * Created by stopka on 3.4.15.
 */
abstract public class AbstractCrawler<T> implements ICrawler {
    private IProcessor<T> processor;

    /**
     *
     * @param processor processor to process found items
     */
    protected AbstractCrawler(IProcessor<T> processor){
        this.processor=processor;
    }

    /**
     * Initiates and finishes the crawling
     */
    public void crawl(){
        processAll();
        processor.finish();
    }

    /**
     * Does the crawling
     * @throws CrawlerException when anything goes wrong
     */
    abstract public void processAll() throws CrawlerException;

    /**
     * Processes the item using processor
     * @param item item to let process by  processor chain
     * @throws CrawlerException when crawling goes wrong
     */
    protected void processItem(T item)throws CrawlerException{
        processor.process(item);
    }
}
