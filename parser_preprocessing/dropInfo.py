import pandas as pd
import numpy as np
import csv


def dropInfo(dataset,droplist,filename):
	dataset = dataset.drop(droplist,axis =1 ) 
	print("dropped")
	dataset.to_csv(filename)


list1 = ['name','connections','org_detail','location','duration',
	'past_job_org_detail1','past_job_duration1','past_job_location1',
	'past_job_org_detail2','past_job_duration2','past_job_location2',
	'past_job_org_detail3','past_job_duration3','past_job_location3',
	'past_job_org_detail4','past_job_duration4','past_job_location4',
	'past_job_org_detail5','past_job_duration5','past_job_location5',
	'past_job_org_detail6','past_job_duration6','past_job_location6',
	'past_job_org_detail7','past_job_duration7','past_job_location7',
	'past_job_org_detail8','past_job_duration8','past_job_location8',
	'past_job_org_detail9','past_job_duration9','past_job_location9',
	'past_job_org_detail10','past_job_duration10','past_job_location10',
	'highestLevel_endDate','otherLevel_endDate1','otherLevel_endDate2',
	'otherLevel_endDate3','otherLevel_endDate4','otherLevel_endDate5',
	'otherLevel_endDate6','otherLevel_endDate7','highestLevel_degree',
	'otherLevel_degree1','otherLevel_degree2','otherLevel_degree3',
	'otherLevel_degree4','otherLevel_degree5','otherLevel_degree6',
	'otherLevel_degree7']

dataset = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/data_v4.csv', encoding = "ISO-8859-1",engine='python')
print("loaded")
data_no_latest = "/Users/apple/Desktop/COLLEGE/fyp/data/data_no_latest.csv"
list2 = ['title','org_summary','description']
dropInfo(dataset,list2,data_no_latest)



