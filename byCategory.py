import pandas as pd
import numpy as np
import csv

# dataset = pd.read_csv('data.csv',encoding = "ISO-8859-1")
# dataset = dataset.drop(['name','connections','org_detail','location','past_job_org_detail1',
# 	'past_job_location1','past_job_description1','past_job_org_detail2',
# 	'past_job_location2','past_job_description2','past_job_org_detail3'], axis=1)
# 	# 'past_job_location3','past_job_description3','past_job_org_detail4',
# 	# 'past_job_location4','past_job_description4','past_job_org_detail5',
# 	# 'past_job_location5','past_job_description5','past_job_org_detail6',
# 	# 'past_job_location6','past_job_description6','highestLevel_detail',
# 	# 'otherLevel_universityName2','otherLevel_degree2','otherLevel_major2',
# 	# 'otherLevel_endDate2','otherLevel_detail2','otherLevel_universityName3',
# 	# 'otherLevel_degree3','otherLevel_major3','otherLevel_endDate3',
# 	# 'otherLevel_detail3'],axis =1 ) 
# print("dropped")
dataset = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/fyp_linkedin/data/data_v3.csv', encoding = "ISO-8859-1")
dataset['title'] = dataset['title'].str.lower().str.replace(' ', '')

softwareEngineer = dataset.loc[dataset['title'].isin(['softwareengineer','seniorsoftwareengineer'])]
# print(softwareengineer)
softwareEngineer.to_csv('softwareEngineer.csv')
print("success1")

# researchAssistant = dataset.loc[dataset['title'].isin(['researchassistant'])]
# researchAssistant.to_csv('researchAssistant.csv')
# print("success2")


