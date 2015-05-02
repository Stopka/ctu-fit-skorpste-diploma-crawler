package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;

/**
 * Factory component to convert data
 * Created by stopka on 2.12.14.
 */
public interface IFactory<IN,OUT> {
    /**
     * Creates another format of data from original one
     * @param in original data
     * @return converted data
     * @throws CrawlerException Thrown on any creation errors
     */
    public OUT create(IN in) throws CrawlerException;
}
