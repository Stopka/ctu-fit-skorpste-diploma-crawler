package cz.cvut.fit.skorpste.dip.crawler.crawler;

import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.IndexCrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.factory.IdFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.FilterProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.TrueFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.filter.FileExistsFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.CleaningCollectionProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.NegationFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * Created by stopka on 3.12.14.
 */
public class CleanerFactory extends CrawlerFactory<SolrDocument,String> {
    static Logger logger=Logger.getLogger(CleanerFactory.class);

    public CleanerFactory(Index index){
        super(index);
    }

    /**
     * Creates index cleaner
     * @return index cleaner component chain
     */
    public ICrawler get(){
        logger.debug("Building clean chain");
        return getCrawler();
    }

    /**
     * Creates crawler
     * - selects type using config crawler=index|none
     * @return index or none crawler
     */
    protected ICrawler getCrawler(){
        logger.debug("Creating crawler");
        String type=getConfig().getString("cleaner.crawler");
        if(type!=null && type.equals("index")) {
            logger.debug("crawler=index");
            return getIndexCrawler();
        }
        logger.debug("crawler=none");
        return getNoneCrawler();
    }

    /**
     * Creates index crawler
     * @return index crawler
     */
    protected ICrawler getIndexCrawler(){
        Index index=getIndex();
        IFilter filter=getFilter();
        FilterProcessor filterProcessor=new FilterProcessor(filter,getFactoryProcessor());
        logger.debug("Creating index crawler");
        IndexCrawler crawler= new IndexCrawler(index,filterProcessor);
        return crawler;
    }

    /**
     * Creates filter
     * - selects filter using config filter=file|all
     * @return file filter or all filter
     */
    protected IFilter getFilter(){
        logger.debug("Creating filter");
        String type=getConfig().getString("cleaner.filter");
        if(type!=null && type.equals("all")) {
            logger.debug("filter=all");
            return getAllFilter();
        }
        logger.debug("filter=file");
        return getFileFilter();
    }

    /**
     * Creates file filter
     * @return file validator
     */
    protected IFilter getFileFilter(){
        logger.debug("Creating file validator filter");
        return new NegationFilter<SolrDocument>(new FileExistsFilter<SolrDocument>(getFactory()));
    }

    /**
     * Creates all filter
     * @return false validator
     */
    protected IFilter getAllFilter(){
        logger.debug("Creating all files filter");
        return new TrueFilter();
    }

    /**
     * Creates cleaner processor
     * @return cleaner processor
     */
    protected IProcessor<List<String>> getProcessor(){
        logger.debug("Creating cleaner processor");
        return new CleaningCollectionProcessor(getIndex());
    }

    /**
     * Creates document id factory
     * @return id factory
     */
    protected IFactory<SolrDocument,String> getFactory(){
        logger.debug("Creating ID factory");
        return new IdFactory();
    }
}
