package xinyue.linkedInSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.terrier.indexing.Collection;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.querying.parser.Query;
import org.terrier.querying.parser.QueryParser;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.structures.indexing.classical.BlockIndexer;
import org.terrier.structures.indexing.singlepass.BasicSinglePassIndexer;
import org.terrier.structures.indexing.singlepass.BlockSinglePassIndexer;
import org.terrier.utility.ApplicationSetup;

public class App extends JFrame
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
	private JTextField jTextField = null;
//	private JLabel jLabel = null;
	
	private Manager queryingManager = null;
	private static String matching = ApplicationSetup.getProperty("desktop.matching","Matching");
	private static String weighting = ApplicationSetup.getProperty("desktop.model", "PL2");

	public static void main(String[] args) {
	
	}
	
	public App() {
		ApplicationSetup.BLOCK_INDEXING = true;
		if (( ApplicationSetup.getProperty("querying.allowed.controls", null)) == null)
		{
			ApplicationSetup.setProperty("querying.allowed.controls", "c,start,end,qe");
		}
		if ((ApplicationSetup.getProperty("querying.postprocesses.order", null)) == null)
		{
			ApplicationSetup.setProperty("querying.postprocesses.order", "QueryExpansion");
		}
		if ((ApplicationSetup.getProperty("querying.postprocesses.controls", null)) == null)
		{
			ApplicationSetup.setProperty("querying.postprocesses.controls", "qe:QueryExpansion");
		}
		ApplicationSetup.setProperty("indexer.meta.forward.keys","docno,filename");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens","26,2048");
		ApplicationSetup.setProperty("indexing.max.tokens", "10000");
		ApplicationSetup.setProperty("invertedfile.processterms","25000");
		ApplicationSetup.setProperty("ignore.low.idf.terms","false");
		
//		String query = jTextField.getText();
		String query = "software engineer";
//		if (query == null) {
//			return;
//		}
		
		try {
			Query q = null;
			try{
				q = QueryParser.parseQuery(query);
			} catch (Exception e) {
				q = QueryParser.parseQuery(query.replaceAll("[^a-zA-Z0-9 ]", ""));	
			}
			if (q == null)
			{
				return;
			}
//			if (queryingManager == null)
//			{
//				return;
//			}
//			jTextField.setText(q.toString());	
			SearchRequest srq = queryingManager.newSearchRequest();
			srq.setQuery(q);
//			srq.addMatchingModel(matching, weighting);
			srq.addMatchingModel("Matching", "BM25");
			srq.setControl("c", "1.0d");
			queryingManager.runPreProcessing(srq);
			queryingManager.runMatching(srq);
			queryingManager.runPostProcessing(srq);
			queryingManager.runPostFilters(srq);
//			renderResults(srq.getResultSet());
		} catch (Exception e) {
			logger.error("An exception when running the query: #"+query +"# :",e);	
		}
		
//		private void runIndex() {		
//			jLabel.setText("Number of Documents: ");
//			jLabel1.setText("Number of Tokens: ");
//			jLabel2.setText("Number of Unique Terms: ");
//			jLabel3.setText("Number of Pointers: ");
		
//			if (folderList == null || folderList.size() == 0)
//			{
//				logger.error("No folders specified to index. Aborting indexing process.");
//				return;
//			}
		
//			try {
//				//deleting existing files
//				if (diskIndex!=null) {
//					diskIndex.close();
//					diskIndex = null;
//					
//				}
//				queryingManager = null;	

				//remove any existing index
//				File indexPath = new File(ApplicationSetup.TERRIER_INDEX_PATH);
//				if (indexPath.exists())
//					deleteFiles(indexPath);
//				else
//					if (! indexPath.mkdirs()) //ensure that the index folder exists
//					{
//						logger.error("ERROR: Could not create the index folders at: "+ indexPath);
//						logger.error("Aborting indexing process");
//						return;
//					}
		
		
//				Indexer indexer;
//				final boolean useSinglePass = Boolean.parseBoolean(ApplicationSetup.getProperty("desktop.indexing.singlepass", "false"));
//				indexer = ApplicationSetup.BLOCK_INDEXING
//					? useSinglePass 
//						? new BlockSinglePassIndexer(ApplicationSetup.TERRIER_INDEX_PATH, ApplicationSetup.TERRIER_INDEX_PREFIX)  
//						: new BlockIndexer(ApplicationSetup.TERRIER_INDEX_PATH, ApplicationSetup.TERRIER_INDEX_PREFIX)
//					: useSinglePass
//						? new BasicSinglePassIndexer(ApplicationSetup.TERRIER_INDEX_PATH, ApplicationSetup.TERRIER_INDEX_PREFIX)
//						: new BasicIndexer(ApplicationSetup.TERRIER_INDEX_PATH, ApplicationSetup.TERRIER_INDEX_PREFIX);
//		
//				SimpleFileCollection sfc = new SimpleFileCollection(folderList, true);
//		
//				indexer.index(new Collection[] { sfc });
		

	}
}
	
