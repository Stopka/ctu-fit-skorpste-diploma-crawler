package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.AbstractPassThroughProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;

/**
 * Passthrough rocessor to convert data
 * Created by stopka on 4.4.15.
 */
public class FactoryProcessor<IN,OUT> extends AbstractPassThroughProcessor<IN,OUT>{
    IFactory<IN,OUT> factory;

    /**
     * @param factory Factory to prepare every item
     * @param processor next procesor in chain
     */
    public FactoryProcessor(IFactory<IN,OUT> factory,IProcessor<OUT> processor){
        super(processor);
        this.factory=factory;
    }

    /**
     * Does the converting
     * @param item piece of data
     * @throws CrawlerException General crawler exception when something goes wrong in nex processor
     */
    @Override
    public void process(IN item) throws CrawlerException {
        processItem(factory.create(item));
    }
}
