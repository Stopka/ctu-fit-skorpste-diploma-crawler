package cz.cvut.fit.skorpste.dip.crawler.crawler.common;

import org.apache.log4j.Logger;

/**
 * Basic crawler that does nothing
 * Created by stopka on 2.12.14.
 */
public class DumbCrawler implements ICrawler {
    static Logger logger=Logger.getLogger(DumbCrawler.class);

    public void crawl(){
        logger.debug("Doing nothing");
    }
}


