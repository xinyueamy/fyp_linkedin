import pandas as pd 
import csv
import numpy as np

tmpdataset = pd.read_csv("/Users/apple/Desktop/softwareengineer.csv", encoding = "ISO-8859-1")

tmpdataset['filename'] = tmpdataset['filename'].str.replace('/Users/apple/Desktop/COLLEGE/fyp/data/data_xlxs/user','')
tmpdataset['filename'] = tmpdataset['filename'].str.replace('.xlsx','')
# print(len(tmpdataset['filename']))
# print(tmpdataset['filename'])

count = 0
dataset = pd.read_csv("/Users/apple/Desktop/COLLEGE/fyp/data/data_v3.csv", encoding = "ISO-8859-1",low_memory=False)

file = csv.writer(open("first_rank_se.csv","w"))
row = []

for i in tmpdataset['filename']:
	print(i)
	row = dataset.iloc[int(i)-1]
	# row.append(str(count+1))
	file.writerow(row)
	count+=1
	print("===" + str(count))





