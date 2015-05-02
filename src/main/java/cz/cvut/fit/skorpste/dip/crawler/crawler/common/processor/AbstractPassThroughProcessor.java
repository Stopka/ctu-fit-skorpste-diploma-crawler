package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;

/**
 * Basics of passtrought processors
 * Created by stopka on 4.4.15.
 */
abstract public class AbstractPassThroughProcessor<IN,OUT> implements IProcessor<IN> {
    private IProcessor<OUT> processor;

    /**
     *
     * @param processor  next procesor in chain
     */
    public AbstractPassThroughProcessor(IProcessor<OUT> processor){
        this.processor=processor;
    }

    /**
     * Lets process item by next processor in chain
     * @param item item to process
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    public void processItem(OUT item)throws CrawlerException{
        processor.process(item);
    }

    /**
     * Nofifies about processing finish
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    @Override
    public void finish() throws CrawlerException {
        processor.finish();
    }
}
