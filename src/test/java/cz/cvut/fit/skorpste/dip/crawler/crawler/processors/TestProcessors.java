package cz.cvut.fit.skorpste.dip.crawler.crawler.processors;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.CollectorProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.FactoryProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.FilterProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Created by stopka on 4.4.15.
 */
public class TestProcessors {
    @Test
    public void collector(){
        StubProcessor<List<Integer>> endpoint = new StubProcessor<>();
        CollectorProcessor<Integer> collector= new CollectorProcessor<>(endpoint);
        collector.setMaxSize(3);
        for(int i=0;i<4;i++){
            assertFalse("Finished too early",endpoint.finished);
            collector.process(i);
        }
        collector.finish();
        assertTrue("Did not finish", endpoint.finished);
        assertEquals("Max size size division error", endpoint.list.size(), 2);
        assertEquals("3 numbers in first", endpoint.list.get(0).size(), 3);
        assertEquals("1 number in 2nd", endpoint.list.get(1).size(), 1);
        assertEquals("0.1 should be 1",endpoint.list.get(0).get(1).intValue(),1);
        assertEquals("Last should be 3",endpoint.list.get(1).get(0).intValue(), 3);
    }

    @Test
    public void filter(){
        StubProcessor<Integer> endpoint = new StubProcessor<>();
        FilterProcessor<Integer> filter= new FilterProcessor<>(new IFilter<Integer>() {
            @Override
            public boolean isAccepted(Integer item) {
                return item.intValue()%2==0;
            }
        },endpoint);
        for(int i=0;i<6;i++){
            assertFalse("Finished too early",endpoint.finished);
            filter.process(i);
        }
        filter.finish();
        assertTrue("Did not finish", endpoint.finished);
        assertEquals("Should select 3 items", endpoint.list.size(), 3);
        assertEquals("1st should be 0",endpoint.list.get(0).intValue(),0);
        assertEquals("last should be 4",endpoint.list.get(2).intValue(),4);
    }

    @Test
    public void factory(){
        StubProcessor<Integer> endpoint = new StubProcessor<>();
        FactoryProcessor<Integer,Integer> filter= new FactoryProcessor<>(new IFactory<Integer, Integer>() {
            @Override
            public Integer create(Integer integer) throws CrawlerException {
                return -integer.intValue();
            }
        }, endpoint);
        for(int i=0;i<3;i++){
            assertFalse("Finished too early",endpoint.finished);
            filter.process(i);
        }
        filter.finish();
        assertTrue("Did not finish", endpoint.finished);
        assertEquals("Should result 3 items", endpoint.list.size(), 3);
        assertEquals("1st should be 0",endpoint.list.get(0).intValue(),0);
        assertEquals("last should be -2",endpoint.list.get(2).intValue(),-2);
    }
}
