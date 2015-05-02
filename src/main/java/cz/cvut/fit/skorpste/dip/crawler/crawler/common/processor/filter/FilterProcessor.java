package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;

/**
 * Filters items which should be processed
 * Created by stopka on 3.4.15.
 */
public class FilterProcessor<I> implements IProcessor<I> {
    IFilter<I> filter;
    IProcessor<I> processor;
    public FilterProcessor(IFilter<I> filter,IProcessor<I> processor){
        this.filter=filter;
        this.processor=processor;
    }

    /**
     * Lets the proceessor process items which passes the filter
     * @param item piece of data
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    @Override
    public void process(I item) throws CrawlerException {
        if(filter!=null&&!filter.isAccepted(item)){
            return;
        }
        processor.process(item);
    }

    /**
     * Processing finished
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    @Override
    public void finish() throws CrawlerException {
        processor.finish();
    }
}
