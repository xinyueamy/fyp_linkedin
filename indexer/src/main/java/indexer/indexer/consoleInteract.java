package indexer.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

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
import org.terrier.utility.ApplicationSetup;

public class consoleInteract {
	private Index diskIndex = null;
	private Manager queryingManager = null;
	private ResultSet result = null;
	private int count = 0;

	public static void main(String[] args) throws Exception {

		// Set terrier home
		System.setProperty("terrier.home", "/Users/apple/Desktop/COLLEGE/fyp/terrier-core-4.2");
		consoleInteract ci = new consoleInteract();

	}
	// Configure Index

	public consoleInteract() {

		System.out.println("Hello, do you have existing index to query? Please enter 'yes' or 'no'.");
		Scanner reader = new Scanner(System.in);

		String answer = reader.nextLine().toLowerCase();
		System.out.println(answer);
		if (answer.equals("yes")) {
			System.out.println("Please give the directory to your index below");
			String indexDir = reader.nextLine();

			try {
				if (isDir(indexDir)) {
					diskIndex = Index.createIndex(indexDir, "data");
					System.out.println(
							"Number of Documents: " + diskIndex.getCollectionStatistics().getNumberOfDocuments());
					System.out.println("Number of Tokens: " + diskIndex.getCollectionStatistics().getNumberOfTokens());
					System.out.println(
							"Number of Unique Terms: " + diskIndex.getCollectionStatistics().getNumberOfUniqueTerms());
					System.out.println(
							"Number of Pointers: " + diskIndex.getCollectionStatistics().getNumberOfPointers());
					System.out.println("Load Index Successful.");
				} else {
					System.out.println("Invalid directory or directory doesn't exist");
					quitApp();
				}
			} catch (Exception e) {
				System.out.println("Sorry, something went wrong");
				quitApp();
			}
		} else if (answer.equals("no")) {
			System.out.println("Please enter directory to files you want to index below, or press Q to quit.");
			String docDir = reader.nextLine();
			if (docDir.equals("Q") || docDir.equals("q")) {
				System.out.println("Quit sucessfully");
				quitApp();
			} else if (isDir(docDir)) {
				try {
					System.out.println("Please enter a directory to save index: ");
					String dirToIndex = reader.nextLine();
					// try {
					if (isDir(dirToIndex)) {
						createIndex(docDir, dirToIndex);
						diskIndex = Index.createIndex(dirToIndex, "data");
					} else {
						quitApp();
						// }
					}
					// catch (Exception e) {
					// System.out.println("Sorry, something went wrong");
					// }
				} catch (Exception e) {
					System.out.println("Invalid input");
					quitApp();
				}
			} else {
				System.out.println("Invalid Input.");
				quitApp();
			}
		} else {
			System.out.println("Invalid input");
			quitApp();
		}

		System.out.println("We can start to use query now.");

		if (diskIndex != null) {
			queryingManager = new Manager(diskIndex);
			queryingManager.setProperty("matching.retrieved_set_size", "300000");
		} else {
			System.out.println("No Index!");
			quitApp();
		}
		String queryInput = null;
		boolean go = true;

		while (go) {
			System.out.println("Please enter your query below: (Use '+' for mandotory term, '-'"
					+ " for excluding terms " + "or enter Q to quit.");
			String q = reader.nextLine();

			if ((q.equals("q") || q.equals("Q"))) {
				go = false;
				// quitApp();
			} else {
				System.out.println("Please enter a weighting model to use: ");
				String wModel = reader.nextLine();
				try {
					SearchRequest srq = queryingManager.newSearchRequestFromQuery(q);
					srq.addMatchingModel("Matching", wModel);
					srq.setControl("decorate", "on");
					queryingManager.runSearchRequest(srq);
					result = getResult(srq);
					System.out.println(
							"Do you want to save the rank and scores to file? Please answer with 'yes' if you need the file");
					String answer2 = reader.nextLine().toLowerCase();
					if (answer2.equals("yes")) {
						generateResultFile(result);
						System.out.println("Done to_csv");
						count +=1;
					}
				} catch (Exception e) {
					System.out.println("Invalid input.");
					System.out.println("Please reenter: ");
				}

			}
		}

		System.out.println("Thanks for using the service");

	}

	private void quitApp() {
		System.exit(0);
	}

	private Indexer createIndex(String dirFiles, String dirIndex) {
		ApplicationSetup.setProperty("indexer.meta.forward.keys", "docno,filename");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "26,2048");
		ApplicationSetup.setProperty("ignore.low.idf.terms", "false");
		Indexer indexer = new BasicIndexer(dirIndex, "data");
		SimpleFileCollection coll = new SimpleFileCollection(Arrays.asList(dirFiles), true);
		indexer.index(new Collection[] { coll });
		return indexer;
	}

	private boolean isDir(String dir) {
		return Files.isDirectory(Paths.get(dir));
	}

	private ResultSet getResult(SearchRequest srq) {
		ResultSet results = srq.getResultSet();
		System.out.println(results.getExactResultSize() + " documents were scored");
		return results;
	}

	private void generateResultFile(ResultSet rs) {
		String fileName = null;
		List<String> fileNames = new ArrayList<String>();
		List<Double> scores = new ArrayList<Double>();
		List<Integer> rankList = new ArrayList<Integer>();
		List<Integer> docids = new ArrayList<Integer>();
		for (int i = 0; i < rs.getExactResultSize(); i++) {
			int docid = rs.getDocids()[i];
			double score = rs.getScores()[i];
			try {
				fileName = rs.getMetaItem("filename", docid);
				fileNames.add(fileName);
				docids.add(docid);
				rankList.add(i);
				scores.add(score);
			} catch (Exception e) {
				fileName = "nan";
				fileNames.add(fileName);
				docids.add(docid);
				rankList.add(i);
				scores.add(score);
			}

			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new File("/Users/apple/Desktop/"+"result"+count+".csv"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			StringBuilder sb = new StringBuilder();
			sb.append("File Name");
			sb.append(",");
			sb.append("Docid");
			sb.append(",");
			sb.append("Rank");
			sb.append(",");
			sb.append("Score");
			sb.append("\n");

			for (int j = 0; j < docids.size(); j++) {
				sb.append(fileNames.get(j));
				sb.append(",");
				sb.append(docids.get(j).toString());
				sb.append(',');
				sb.append(rankList.get(j).toString());
				sb.append(',');
				sb.append(scores.get(j).toString());
				sb.append('\n');
			}
			pw.write(sb.toString());
			pw.close();
		}
	}
}
//

//
////
// System.out.println("The top 5000 of those documents were returned");
// System.out.println("Document Ranking");
//
//// String fileName = null;
// for (int i =0; i<5000; i++) {
// int docid = results.getDocids()[i];
// double score = results.getScores()[i];
// try {
//// System.out.println(" Rank "+i+": "+docid+"
//// "+results.getMetaItem("filename", docid)+" "+score);
// System.out.println(" Rank "+i+": "+docid+" Score: "+score);
//// String fileName = results.getMetaItem("filename", docid);
//// fileNames.add(fileName);
// docids.add(docid);
// rankList.add(i);
// scores.add(score);
// }
// catch(Exception e) {
// System.out.println("Failed" + docid);
// }
//
// }
//// System.out.println(fileNames);
//// System.out.println(rankList);
// System.out.println("succ");
//
// PrintWriter pw = new PrintWriter(new
//// File("/Users/apple/Desktop/se_Hiemstra_LM.csv"));
// StringBuilder sb = new StringBuilder();
// sb.append("Docid");
// sb.append(",");
// sb.append("Rank");
// sb.append(",");
// sb.append("Hiemstra_LM_Score");
// sb.append("\n");
//
// for (int i=0;i<docids.size();i++) {
// sb.append(docids.get(i).toString());
// sb.append(',');
// sb.append(rankList.get(i).toString());
// sb.append(',');
// sb.append(scores.get(i).toString());
// sb.append('\n');
// }
// pw.write(sb.toString());
// pw.close();
// System.out.println("Done to_csv");
// }
// }
//
