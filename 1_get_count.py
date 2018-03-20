import pandas as pd
import numpy as np
import csv

def to_lower(dataset, column):
	dataset[column] = dataset[column].str.lower().str.replace(' ','')

dataset = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/data_v4.csv', encoding = "ISO-8859-1",engine='python')
print("loaded")

to_lower(dataset,'title')
to_lower(dataset,'past_job_title1')
to_lower(dataset,'past_job_title2')
to_lower(dataset,'past_job_title3')
to_lower(dataset,'past_job_title4')
to_lower(dataset,'past_job_title5')
to_lower(dataset,'past_job_title6')
to_lower(dataset,'past_job_title7')
to_lower(dataset,'past_job_title8')
to_lower(dataset,'past_job_title9')
to_lower(dataset,'past_job_title10')


# softwareEngineer = dataset.loc[dataset['title'].isin(['softwareengineer','softwaredeveloper','seniorsoftwareengineer'])]
# # recent_se_docid = softwareEngineer['docid']
# # recent_se_docid.to_csv('/Users/apple/Desktop/tmp/recent_se_docid.csv')
# softwareEngineer.to_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/softwareEngineer_title0.csv')

# for i in range(1,11):
# 	softwareEngineer = dataset.loc[dataset['past_job_title'+str(i)].isin(['softwareengineer'])]
# 	softwareEngineer.to_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/softwareEngineer_title'+str(i)+'.csv')
# print("Saved to csv")

# # Merge all the engineers
# engineer0 = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/softwareEngineer_title0.csv',  encoding = "ISO-8859-1")
# engineer0_id = engineer0['docid']
# print(len(engineer0_id))
# result_array = engineer0_id.values
# result = np.array(result_array)

# for i in range(1, 11):
# 	temp_engineer = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/softwareEngineer_title'+str(i)+'.csv',  encoding = "ISO-8859-1")
# 	temp_id = temp_engineer['docid']
# 	temp_array = temp_id.values
# 	b = np.array(temp_array)
# 	result = np.append(result,b)
# 	print("result now ")
# 	print(result)

# np.savetxt("/Users/apple/Desktop/COLLEGE/fyp/data/se_docid.csv", result, delimiter=",")
# print("Result generated")


researchAssistant = dataset.loc[dataset['title'].isin(['researchassistant'])]
recent_ra_docid = researchAssistant['docid']
recent_ra_docid.to_csv('/Users/apple/Desktop/COLLEGE/fyp/data/recent_ra_docid.csv')
# researchAssistant.to_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/researchAssistant_title0.csv')

# for i in range(1,11):
# 	researchAssistant = dataset.loc[dataset['past_job_title'+str(i)].isin(['researchassistant'])]
# 	researchAssistant.to_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/researchAssistant_title'+str(i)+'.csv')
# print("Saved to csv")

# assistant0 = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/researchAssistant_title0.csv',  encoding = "ISO-8859-1")
# assistant0_id = assistant0['docid']
# print(len(assistant0_id))
# result_array = assistant0_id.values
# result = np.array(result_array)

# for i in range(1, 11):
# 	temp_assistant = pd.read_csv('/Users/apple/Desktop/COLLEGE/fyp/data/tmp/researchAssistant_title'+str(i)+'.csv',  encoding = "ISO-8859-1")
# 	temp_id = temp_assistant['docid']
# 	temp_array = temp_id.values
# 	b = np.array(temp_array)
# 	result = np.append(result,b)
# 	print("result now ")
# 	print(result)

# np.savetxt("/Users/apple/Desktop/COLLEGE/fyp/data/all_ra_docid.csv", result, delimiter=",")
# print("Result generated")

