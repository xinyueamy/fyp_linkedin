#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Oct 11 13:48:55 2017

@author: Xinyue
"""

import pandas as pd
import numpy as np
import csv
from collections import Counter

# count occurencies 
def categoricalDataClassification(column):
    for i in range(len(column)):
    # for i in range(100):
        column[i] = str(column[i]).lower().replace(" ", "")
        print(i)
    return Counter(column)

def writeToCSV(name, counter):
	file = csv.writer(open(name, "w"))
	for key, value in counter.items():
		file.writerow([str(key), str(value)])
		# print(key, value)

def combineColumns(column1,column2):
	column1=dataset.loc[:, column1]
	column2=dataset.loc[:, column2]
	return column1+column2

dataset = pd.read_csv('parserData.csv',encoding = "ISO-8859-1")

# Count College 
collegeNames = dataset.loc[:,'highestLevel_universityName']
c_collegeNames = categoricalDataClassification(collegeNames)
print(c_collegeNames)
# print(c_collegeNames.keys())
writeToCSV("collegeNames.csv", c_collegeNames)


# file = csv.writer(open("categories.csv", "w"))
# for key, value in c_collegeNames.items():
# 	file.writerow([str(key), str(value)])
# 	print(key, value)

# Count Title
titles = dataset.loc[:,'title']
c_titles = categoricalDataClassification(titles)
# print(c_titles)
writeToCSV("titles.csv", c_titles)

# Count Duration
durations = dataset.loc[:,'duration']
c_durations = Counter(durations)
writeToCSV("durations.csv", c_durations)
print(c_durations)

jobCollege = combineColumns('past_job_title1','highestLevel_universityName')
# for i in range(len(combine)):
#         combine[i] = combine[i].lower().replace(" ", "")
#         print(i)
# c_c = Counter(combine)
# print(c_c)
c_jobCollege = categoricalDataClassification(jobCollege)
writeToCSV("firstjob_college.csv", c_jobCollege)


