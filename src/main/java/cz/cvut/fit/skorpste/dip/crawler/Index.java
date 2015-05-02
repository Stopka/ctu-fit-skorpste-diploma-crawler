package cz.cvut.fit.skorpste.dip.crawler;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

/**
 * Model class for interacting with solr index
 * Encapsulation of Solrj
 * Created by stopka on 2.12.14.
 */
public class Index {
    private SolrServer server;

    /**
     * Default number of rows on page retrieved by queries
     */
    public final int DEFAULT_ROWS=50;

    /**
     * @param host Host adress of solr servers
     */
    public Index(String host){
        server=new HttpSolrServer(host);
    }

    /**
     * Deletes all documents from index
     * @return server response
     * @throws IOException when sorl connection fails
     * @throws SolrServerException when sorl deletion fails
     */
    public UpdateResponse deleteAll() throws IOException, SolrServerException {
        return server.deleteByQuery( "*:*" );
    }

    /**
     * Queries server for all documents in index
     * @param start start offset
     * @param rows how many documents should be retrieved in a batch
     * @return Solr document list sized by params
     * @throws SolrServerException when  sorl search fails
     */
    public SolrDocumentList findAll(int start,int rows) throws SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery( "id:*" );
        query.setRows(rows);
        query.setStart(start);
        QueryResponse rsp = server.query( query );
        return rsp.getResults();
    }

    /**
     * Queries server for all documents in index
     * @param start offset of search result
     * @return solr document list, a page of documents selected by start param and sized by default row count
     * @throws SolrServerException when  sorl search fails
     */
    public SolrDocumentList findAll(int start) throws SolrServerException {
        return findAll(start, DEFAULT_ROWS);
    }

    /**
     * Queries server for all documents in index
     * @return solr document list, a first page of documents sized by default row count
     * @throws SolrServerException when sorl search fails
     */
    public SolrDocumentList findAll() throws SolrServerException {
        return findAll(0);
    }

    /**
     * Deletes document from index
     * @param ids list of document ids
     * @return server response
     * @throws IOException when sorl comunication fails
     * @throws SolrServerException  when deletion from sorl index fails
     */
    public UpdateResponse delete(List<String> ids) throws IOException, SolrServerException {
        UpdateResponse response=server.deleteById(ids) ;
        server.commit();
        return response;
    }

    /**
     * Adds (on existing doc id updates) document to index
     * @param docs list of documents
     * @return server response
     * @throws IOException when sorl comunication fails
     * @throws SolrServerException when adding to sorl index fails
     */
    public UpdateResponse add(List<SolrInputDocument> docs) throws IOException, SolrServerException {
        UpdateResponse response= server.add(docs);
        server.commit();
        return response;
    }


}
