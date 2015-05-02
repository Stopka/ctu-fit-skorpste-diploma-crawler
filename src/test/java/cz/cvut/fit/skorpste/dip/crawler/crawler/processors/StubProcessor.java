package cz.cvut.fit.skorpste.dip.crawler.crawler.processors;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by stopka on 4.4.15.
 */
public class StubProcessor<T> implements IProcessor<T> {
    public List<T> list=new LinkedList<T>();
    public boolean finished=false;

    @Override
    public void process(T item) throws CrawlerException {
        assert finished==false;
        list.add(item);
    }

    @Override
    public void finish() throws CrawlerException {
        finished=true;
    }
}
