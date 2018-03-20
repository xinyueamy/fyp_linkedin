import matplotlib.pyplot as plt
import numpy as np 
import pandas as pd

def drawBarChart(x, y, x_label, y_label, title):
	# x = data.iloc[:, 0]
	# y = data.loc[:, 1]
	y_pos = np.arange(len(x))
	plt.bar(y_pos,y, align = 'center')
	plt.xticks(y_pos, x)
	plt.ylabel(y_label)
	plt.xlabel(x_label)
	plt.title(title)
	plt.show()
	# plt.savefig(os.path.join(title+'.png'), dpi=300, format='png', bbox_inches='tight')

collegeNames = pd.read_csv('CollegeNames.csv',encoding = "ISO-8859-1")
collegeName = collegeNames.iloc[0:9, 0]
nameFreq = collegeNames.iloc[0:9, 1]
# print(dataset)
drawBarChart(collegeName, nameFreq, 'College Names','Occurency of College Names', 'collegeNameFreq')

jobTitles = pd.read_csv('titles.csv',encoding = "ISO-8859-1")
jobTitle = jobTitles.iloc[0:9, 0]
titleFreq = jobTitles.iloc[0:9, 1]
drawBarChart(jobTitle, titleFreq, 'Job Titles', 'Occurency of Job Titles', 'jobTitleFreq')