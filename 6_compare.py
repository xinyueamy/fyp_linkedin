import pandas as pd

def intersect(a, b):
    return list(set(a) & set(b))

def precision(a,b):
	return a/(a+b)

def recall(a,b):
	return a/(a+b)

def accuracy(a,b):
	return (a+b)/169932

def F1_score(a,b,c):
	return 2*a/(2*a+b+c)

total = 169932
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_BM25_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_Hiemstra_LM_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_TFIDF_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_BB2_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_BM25.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_DFR_BM25_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_IFB2_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/se_LemurTF_IDF_3000.csv', encoding = "ISO-8859-1",engine='python')
# se = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/all_se_docid.csv', encoding = "ISO-8859-1",engine='python')
# se = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/recent_se_docid.csv', encoding = "ISO-8859-1",engine='python')

# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_BM25_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_Hiemstra_LM_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_TFIDF_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_BB2_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_DFR_BM25_3000.csv', encoding = "ISO-8859-1",engine='python')
# result = pd.read_csv('/Users/apple/Desktop/fyp/ra_IFB2_3000.csv', encoding = "ISO-8859-1",engine='python')
result = pd.read_csv('/Users/apple/Desktop/fyp/ra_LemurTF_IDF_3000.csv', encoding = "ISO-8859-1",engine='python')
# se = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/recent_ra_docid.csv', encoding = "ISO-8859-1",engine='python')

r = result['docid'].values
n_r = len(r)
# print(n_r)
gt = se['docid'].values
n_gt = len(gt)

tmp=intersect(r,gt)

TP = len(tmp)
FN = n_gt - TP
FP = n_r - TP
TN = total - n_gt - FP

print("TP:"+ str(TP) +" FN:"+ str(FN)+" FP:"+ str(FP) +" TN:"+ str(TN))

accuracy = accuracy(TP,TN)
precision = precision(TP,FP)
recall = recall(TP,FN)
F1 = F1_score(TP,FP,FN)
print("accuracy " + str(accuracy))
print("precision " + str(precision))
print("recall " + str(recall))
print("f1 " + str(F1))


