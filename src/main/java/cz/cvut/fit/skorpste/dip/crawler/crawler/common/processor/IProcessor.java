package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;

/**
 * Interface for basic component processing data
 * Created by stopka on 2.12.14.
 */
public interface IProcessor<I> {
    /**
     * Processes data
     * @param item piece of data
     * @throws CrawlerException when anything goes wrong
     */
    public void process(I item) throws CrawlerException;

    /**
     * Ends the processing session
     * @throws CrawlerException when anything goes wrong
     */
    public void finish() throws CrawlerException;
}
