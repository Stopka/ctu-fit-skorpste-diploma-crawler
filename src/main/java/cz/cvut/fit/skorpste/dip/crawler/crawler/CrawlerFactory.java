package cz.cvut.fit.skorpste.dip.crawler.crawler;

import cz.cvut.fit.skorpste.dip.crawler.ConfigFactory;
import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.DumbCrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.FactoryProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.CollectorProcessor;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Abstract crawler factory to hold common methods
 * Created by stopka on 3.12.14.
 */
public abstract class CrawlerFactory<IN,OUT> {
    static Logger logger=Logger.getLogger(CrawlerFactory.class);
    private Configuration config;
    private Index index;

    /**
     * @param index Index model
     */
    protected CrawlerFactory(Index index){
        this.index=index;
    }

    /**
     * Index model getter
     * @return index model
     */
    protected Index getIndex(){
        return index;
    }

    /**
     * Config getter
     * @return config model
     */
    protected Configuration getConfig(){
        if(config==null) {
            config = ConfigFactory.getConfig();
        }
        return config;
    }

    /**
     * Main bild method
     * @return created crawler chain
     */
    abstract public ICrawler get();

    /**
     * Creates crawler for test and skipping purposes
     * @return dump crawler chain
     */
    protected ICrawler getNoneCrawler(){
        logger.debug("Creating none crawler");
        return new DumbCrawler();
    }

    /**
     * Creates factory processor
     * @return factory processor
     */
    protected FactoryProcessor<IN,OUT> getFactoryProcessor(){
        IFactory<IN,OUT> factory=getFactory();
        return new FactoryProcessor<>(factory,getCollector());
    }

    /**
     * Creates collector
     * - sets configured collection size collector.max_size=?
     * @return collector to create list of data
     */
    protected CollectorProcessor<OUT> getCollector(){
        IProcessor<List<OUT>> processor=getProcessor();
        logger.debug("Creating document collector");
        CollectorProcessor<OUT> collector = new CollectorProcessor<OUT>(processor);
        if(getConfig().containsKey("collector.max_size")) {
            int val=getConfig().getInt("collector.max_size");
            logger.debug("max_size="+val);
            collector.setMaxSize(val);
        }
        return collector;
    }

    /**
     * Creates factory
     * @return factory to convert data
     */
    abstract protected IFactory<IN,OUT> getFactory();

    /**
     * Creates processor
     * @return final processor
     */
    abstract protected IProcessor<List<OUT>> getProcessor();
}
