package cz.cvut.fit.skorpste.dip.crawler.crawler.processors;

import cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.factory.IdFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory.TikaDocumentFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory.TxtDocumentFactory;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by stopka on 4.4.15.
 */
public class TestFactories {
    @Test
    public void testIdFactory(){
        SolrDocument doc=new SolrDocument();
        doc.addField("id","abcd");
        doc.addField("abcd","dsdad");
        IdFactory factory=new IdFactory();
        assertEquals(factory.create(doc), "abcd");
        doc=new SolrDocument();
        doc.addField("id", "uhij");
        assertEquals(factory.create(doc), "uhij");
    }

    @Test
    public void testTxtDocumentFactory(){
        File file=new File("test/1/test1.txt");
        TxtDocumentFactory factory=new TxtDocumentFactory("UTF-8");
        SolrInputDocument doc=factory.create(file);
        assertEquals(doc.getField("name").getValue().toString(), "test1.txt");
        assertTrue(doc.getField("id").getValue().toString().contains("test/1/test1.txt"));
        assertEquals(doc.getField("cat").getValue().toString(), "default");
        assertEquals(doc.getField("text").getValue().toString(), "Test1text haha");

        file=new File("test/1/tst2.txt");
        doc=factory.create(file);
        assertEquals(doc.getField("name").getValue().toString(), "tst2.txt");
        assertTrue(doc.getField("id").getValue().toString().contains("test/1/tst2.txt"));
        assertEquals(doc.getField("cat").getValue().toString(), "default");
        assertEquals(doc.getField("text").getValue().toString(), "Lorem ipsum\n" +
                "dolor sit amet");
    }

    @Test
    public void testTikaDocumentFactory(){
        File file=new File("test/1/test1.txt");
        TikaDocumentFactory factory=new TikaDocumentFactory();
        SolrInputDocument doc=factory.create(file);
        assertEquals(doc.getField("name").getValue().toString(), "test1.txt");
        assertTrue(doc.getField("id").getValue().toString().contains("test/1/test1.txt"));
        assertEquals(doc.getField("cat").getValue().toString(), "default");
        System.out.println(doc.getField("text").getValue());
        assertTrue(doc.getField("text").getValue().toString().contains("Test1text haha"));
    }
}
