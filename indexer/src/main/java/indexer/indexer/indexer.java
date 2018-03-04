package indexer.indexer;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.indexing.TaggedDocument;
import org.terrier.indexing.tokenisation.Tokeniser;
import org.terrier.structures.Index;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.utility.ApplicationSetup;
import org.terrier.utility.Files;

public class indexer {
//	private Index diskIndex;

	public static void main(String[] args) throws Exception {

		// Directory containing files to index
//		String aDirectoryToIndex = "/Users/apple/Desktop/COLLEGE/fyp/fyp_linkedin/data/data_xlxs/";
		String aDirectoryToIndex = "/Users/apple/Desktop/test/";

		// Configure Terrier
		ApplicationSetup.setProperty("indexer.meta.forward.keys", "docno,filename");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "26,2048");
		ApplicationSetup.setProperty("ignore.low.idf.terms","false");

		// Create a new Index
		Indexer indexer = new BasicIndexer("/Users/apple/Desktop/COLLEGE/fyp/index", "data");
//		Indexer indexer = ; 
//		= new BasicIndexer("/Users/apple/Desktop/index/", "data");

		//		for (String filename : new File(aDirectoryToIndex).list()) {
//			String fullPath = aDirectoryToIndex + filename;
//			Document document = new TaggedDocument(Files.openFileReader(fullPath), new HashMap(),
//					Tokeniser.getTokeniser());
//			document.getAllProperties().put("docno", filename);
//			indexer.indexDocument(document);
//		}

//		File indexPath = new File(ApplicationSetup.TERRIER_INDEX_PATH);
		
		SimpleFileCollection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
		indexer.index(new Collection[] { coll });

		Index diskIndex = Index.createIndex("/Users/apple/Desktop/index", "data");
//		Index diskIndex = Index.createIndex();
		diskIndex.close();
		System.out.println("Number of Documents: " + diskIndex.getCollectionStatistics().getNumberOfDocuments());
		System.out.println("Number of Tokens: " + diskIndex.getCollectionStatistics().getNumberOfTokens());
		System.out.println("Number of Unique Terms: " + diskIndex.getCollectionStatistics().getNumberOfUniqueTerms());
		System.out.println("Number of Pointers: " + diskIndex.getCollectionStatistics().getNumberOfPointers());
		
	}
}
//
//
//// enable decoration
// ApplicationSetup.setProperty("querying.postfilters.order",
// "org.terrier.querying.SimpleDecorate");
// ApplicationSetup.setProperty("querying.postfilters.controls",
// "decorate:org.terrier.querying.SimpleDecorate");
//
//// new manager to run query
// Manager queryingManager = new Manager(memIndex);
// Query q = null;
// q = QueryParser.parseQuery("sample");
//
//// Query q = QueryParser.parseQuery("sample");
//// new search request
// SearchRequest srq = queryingManager.newSearchRequest();
// srq.setOriginalQuery("sample");
//
//// specify the model
// srq.addMatchingModel("Matching", "BM25");
//
//// turn on decoration
// srq.setControl("decorate", "on");
//
//// run search
// queryingManager.runSearchRequest(srq);
//// queryingManager.runMatching(srq);
//// queryingManager.runPostProcessing(srq);
//// queryingManager.runPostFilters(srq);
//// renderResults(srq.getResultSet());
//
//// get result
// ResultSet results = srq.getResultSet();
//
// System.out.println(results.getExactResultSize() + " documents were scored");
// System.out.println("The top " + results.getResultSize() + " of those
// documents were returned");
// System.out.println("Document Ranking");
// for (int i =0; i< results.getResultSize(); i++) {
// int docid = results.getDocids()[i];
// double score = results.getScores()[i];
// System.out.println(" Rank "+i+": "+docid+" "+results.getMetaItem("docno",
// docid)+" "+score);
// }
// }
// }
