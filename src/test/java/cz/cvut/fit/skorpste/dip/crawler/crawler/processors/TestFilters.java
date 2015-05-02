package cz.cvut.fit.skorpste.dip.crawler.crawler.processors;

import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.filter.FileExistsFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.NegationFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.TrueFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by stopka on 4.4.15.
 */
public class TestFilters {
    @Test
    public void testTrueFilter() {
        IFilter<Double> filter = new TrueFilter<Double>();
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
    }

    @Test
    public void testNegationFilter() {
        IFilter<Double> filter = new NegationFilter<Double>(new TrueFilter<Double>());
        assertFalse(filter.isAccepted(Math.random()));
        assertFalse(filter.isAccepted(Math.random()));
        assertFalse(filter.isAccepted(Math.random()));
        assertFalse(filter.isAccepted(Math.random()));
        assertFalse(filter.isAccepted(Math.random()));
        assertFalse(filter.isAccepted(Math.random()));
        filter = new NegationFilter<Double>(filter);
        assertTrue(filter.isAccepted(Math.random()));
        assertTrue(filter.isAccepted(Math.random()));
    }

    @Test
    public void testFileExistsFilter() {
        FileExistsFilter<String> filter = new FileExistsFilter<String>(new IFactory<String, String>() {
            @Override
            public String create(String s) throws CrawlerException {
                return s;
            }
        });
        assertTrue(filter.isAccepted("test/1/test1.txt"));
        assertFalse(filter.isAccepted("test/1/none.txt"));
    }
}