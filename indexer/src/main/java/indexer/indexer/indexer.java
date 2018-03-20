package indexer.indexer;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.indexing.TaggedDocument;
import org.terrier.indexing.tokenisation.Tokeniser;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.queryexpansion.QueryExpansionModel;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.terms.TermPipeline;
import org.terrier.utility.ApplicationSetup;
import org.terrier.utility.Files;

public class indexer {
//	private Index diskIndex;

	public static void main(String[] args) throws Exception {

//		Set terrier home
		System.setProperty("terrier.home","/Users/apple/Desktop/COLLEGE/fyp/terrier-core-4.2");
////		Directory containing files to index
		String aDirectoryToIndex = "/Users/apple/Desktop/COLLEGE/fyp/data/data_xlxs_no_latest";
//
////		Configure Index
		ApplicationSetup.setProperty("indexer.meta.forward.keys", "docno,filename");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "26,2048");
		ApplicationSetup.setProperty("ignore.low.idf.terms","false");
//		ApplicationSetup.setProperty("stopwords.filename", "/Users/apple/Desktop/untitled.txt");
//	
////		Create a new Index
		Indexer indexer = new BasicIndexer("/Users/apple/Desktop/index", "data");
		SimpleFileCollection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
		indexer.index(new Collection[]{coll});
		
//		Access index on disk
		Index diskIndex = Index.createIndex("/Users/apple/Desktop/index", "data");
		System.out.println("Number of Documents: " + diskIndex.getCollectionStatistics().getNumberOfDocuments());
//		System.out.println("Number of Tokens: " + diskIndex.getCollectionStatistics().getNumberOfTokens());
//		System.out.println("Number of Unique Terms: " + diskIndex.getCollectionStatistics().getNumberOfUniqueTerms());
//		System.out.println("Number of Pointers: " + diskIndex.getCollectionStatistics().getNumberOfPointers());
//		System.out.println("Sucess");
		
////		Set up query manager
//		Manager queryingManager = new Manager(diskIndex);
//		queryingManager.setProperty("matching.retrieved_set_size", "300000");
//		SearchRequest srq = queryingManager.newSearchRequestFromQuery("+software +engineer");
////		srq.addMatchingModel("Matching","BM25");
////		srq.addMatchingModel("Matching","TF_IDF");
////		srq.addMatchingModel("Matching","BB2");
////		srq.addMatchingModel("Matching","IFB2");
////		srq.addMatchingModel("Matching","Hiemstra_LM");
////		srq.addMatchingModel("Matching","DFR_BM25");
//		srq.addMatchingModel("Matching","LemurTF_IDF");
//		srq.setControl("decorate", "on");
//		queryingManager.runSearchRequest(srq);
////		
////		Get result
//		ResultSet results = srq.getResultSet();
//		System.out.println(results.getExactResultSize()+" documents were scored");
		
//		List<String> fileNames = new ArrayList<String>();
//		List<Double> scores = new ArrayList<Double>();
//	    List<Integer> rankList = new ArrayList<Integer>();
//	    List<Integer> docids = new ArrayList<Integer>();
////        System.out.println("The top 5000 of those documents were returned");
//       
//	    System.out.println("Document Ranking");
//        String fileName = null;
//        int docid = 0;
//        
//        double score = 0;
//        for (int i =0; i<3000; i++) {
//            docid = results.getDocids()[i];
//            score = results.getScores()[i];
//            try {
//            
////            	fileName = results.getMetaItem("filename", docid);
////            System.out.println(fileName);	
//            	System.out.println("   Rank "+i+": "+docid+" "+score);
////            	fileNames.add(fileName);
////            	docid = docid-2;
//            	docids.add(docid);
//            	rankList.add(i);   	    
//            	scores.add(score);
//            	 } 
//            catch(Exception e) {
//            	System.out.println("Failed" + docid); 
//            	}
//            
//            }
////        System.out.println(fileNames);
////        System.out.println(rankList);
//        System.out.println("succ");
//        
//        PrintWriter pw = new PrintWriter(new File("/Users/apple/Desktop/fyp/ra_LemurTF_IDF_3000.csv"));
//        StringBuilder sb = new StringBuilder();
//        sb.append("docid");
//        sb.append(",");
//        sb.append("Rank");
//        sb.append(",");
//        sb.append("Score");
//        sb.append("\n");
//        
//        for (int i=0;i<docids.size();i++) {
//        	sb.append(docids.get(i).toString());
//        	sb.append(',');
//        	sb.append(rankList.get(i).toString());
//        	sb.append(',');
//        	sb.append(scores.get(i).toString());
//        	sb.append('\n');   	
//        }
//        pw.write(sb.toString());
//        pw.close();
//        System.out.println("Done to_csv");
//        System.out.println(results.getExactResultSize() + " were returned");
        }
}

