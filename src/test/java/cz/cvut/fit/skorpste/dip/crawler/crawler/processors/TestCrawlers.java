package cz.cvut.fit.skorpste.dip.crawler.crawler.processors;

import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.FileSystemCrawler;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by stopka on 4.4.15.
 */
public class TestCrawlers {
    @Test
    public void testFileSystem() {
        List<String> paths = new LinkedList<>();
        paths.add("test/1");
        paths.add("test/2");
        ArrayList<String> ok = new ArrayList<>();
        ok.add("test1.txt");
        ok.add("tst2.txt");
        ok.add("file.txt");
        ok.add("file1.txt");
        ok.add("file5.txt");
        ArrayList<String> no = new ArrayList<>();
        no.add("test7.txt");
        no.add("test6.txt");
        StubProcessor<File> endpoint = new StubProcessor<>();
        FileSystemCrawler crawler = new FileSystemCrawler(paths, endpoint);
        crawler.setDepthLimit(2);
        crawler.crawl();
        assertTrue(endpoint.finished);
        for (File f : endpoint.list){
            assertTrue("Should contain file", ok.contains(f.getName()));
            assertFalse("Shuldn't contain file",no.contains(f.getName()));
        }
        System.out.println(endpoint.list);
    }
}
