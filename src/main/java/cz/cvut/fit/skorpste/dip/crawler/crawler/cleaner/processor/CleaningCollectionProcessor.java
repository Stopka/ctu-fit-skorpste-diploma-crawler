package cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor;

import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by stopka on 3.12.14.
 */
public class CleaningCollectionProcessor implements IProcessor<List<String>> {
    Index index;
    static Logger logger=Logger.getLogger(CleaningCollectionProcessor.class);
    public CleaningCollectionProcessor(Index index){
        this.index=index;
    }

    @Override
    public void process(List<String> ids) throws CrawlerException{
        logger.debug("Removing collection from index");
        try {
            index.delete(ids);
        } catch (Exception e) {
            throw new CrawlerException("Document removing failed.",e);
        }
    }

    @Override
    public void finish() throws CrawlerException {
        logger.debug("Finishing cleaning collection processor");
    }
}
