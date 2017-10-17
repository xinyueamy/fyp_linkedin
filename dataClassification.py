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

def categoricalDataClassification(column):
    # for i in range(len(column)):
    for i in range(100):
        column[i] = str(column[i]).lower().replace(" ", "")
        print(i)
    return Counter(column)

dataset = pd.read_csv('parserData.csv',encoding = "ISO-8859-1")

collegeNames = dataset.iloc[:,45]
c_collegeNames = categoricalDataClassification(collegeNames)
print(c_collegeNames)

# for key, count in c_collegeNames.items():
#     name = key
    # writer.writerow([name, count])

titles = dataset.iloc[:,3]
c_titles = categoricalDataClassification(titles)
print(c_titles)

durations = dataset.iloc[:, 6]
c_durations = categoricalDataClassification(durations)
print(c_durations)


