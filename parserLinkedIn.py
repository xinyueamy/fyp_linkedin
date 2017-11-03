import requests
from bs4 import BeautifulSoup
import csv
import os

class CurrentJob:
	title = ""
	org_summary = ""
	org_detail = ""
	duration = ""
	location = ""
	description = ""

	def clear(self):
		self.title = ""
		self.org_summary = ""
		self.org_detail = ""
		self.duration = ""
		self.location = ""
		self.description = ""

	def printAll(self):
		print("title: "+self.title)
		print("org_summary: "+self.org_summary)
		print("org_detail: "+self.org_detail)
		print("duration: "+self.duration)
		print("location: "+ self.location)
		print("description: "+self.description)

class PastJobs:
	title = []
	org_summary = []
	org_detail = []
	duration = []
	location = []
	description = []

	def __init__(self):
		# up to (pastJobsNum) number of past jobs
		for item in range(pastJobsNum):
			self.title.append("")
			self.org_summary.append("")
			self.org_detail.append("")
			self.duration.append("")
			self.location.append("")
			self.description.append("")

	def clear(self):
		for item in range(pastJobsNum):
			self.title[item] = ""
			self.org_summary[item] = ""
			self.org_detail[item] = ""
			self.duration[item] = ""
			self.location[item] = ""
			self.description[item] = ""

	def printAll(self, i):
		print("title"+str(i+1)+": "+self.title[i])
		print("org_summary"+str(i+1)+": "+self.org_summary[i])
		print("org_detail"+str(i+1)+": "+self.org_detail[i])
		print("duration"+str(i+1)+": "+self.duration[i])
		print("location"+str(i+1)+": "+self.location[i])
		print("description"+str(i+1)+": "+self.description[i])

class Educations:
	highestLevel_universityName = ""
	highestLevel_degree = ""
	highestLevel_major = ""
	highestLevel_endDate = ""
	highestLevel_detail = ""

	otherLevel_universityName = []
	otherLevel_degree = []
	otherLevel_major = []
	otherLevel_endDate = []
	otherLevel_detail = []

	def __init__(self):
		for i in range(educationNum):
			self.otherLevel_degree.append("")
			self.otherLevel_universityName.append("")
			self.otherLevel_major.append("")
			self.otherLevel_endDate.append("")
			self.otherLevel_detail.append("")

	def clear(self):
		for i in range(educationNum):
			self.otherLevel_degree[i] = ""
			self.otherLevel_universityName[i] = ""
			self.otherLevel_major[i] = ""
			self.otherLevel_endDate[i] = ""
			self.otherLevel_detail[i] = ""

	def printHighest(self):
		print("highestLevel_universityName: "+self.highestLevel_universityName)
		print("highestLevel_degree: "+self.highestLevel_degree)
		print("highestLevel_major: "+self.highestLevel_major)
		print("highestLevel_endDate: "+self.highestLevel_endDate)
		print("highestLevel_detail: "+self.highestLevel_detail)

	def printOthers(self, i):
		print("otherLevel_universityName"+str(i)+": "+self.otherLevel_universityName[i])
		print("otherLevel_degree"+str(i)+": "+self.otherLevel_degree[i])
		print("otherLevel_major"+str(i)+": "+self.otherLevel_major[i])
		print("otherLevel_endDate"+str(i)+": "+self.otherLevel_endDate[i])
		print("otherLevel_detail"+str(i)+": "+self.otherLevel_detail[i])

def Try(item):
	if item==None:
		# print("No object")
		result = ""
		return(result)
	else:
		# print("Has object")
		return item.text.encode("utf8").strip().replace("$", "")

def TryDuration(item):
	if item==None:
		# print("No object")
		result = ""
		return(result)
	else:
		# print("Has object")
		return item.text.encode("utf8").strip().replace("$", "").replace("(", "").replace(")","")

def Merge1(items):
	result = ""
	for item in items:
		if item!="":
			result = result + item + ","

	return result

def Merge2(items1, items2):
	result = ""
	for i in range(len(items1)):
		item1 = items1[i]
		item2 = items2[i]
		if item1!="":
			result = result + item1 + item2 + ","
	return result

def GetRow(id, name, connections, currentJob, pastJobs, educations, skills, languages, languages_proficiency):
	row = []	
	row.append(id)
	row.append(name)
	row.append(connections)
	# Add current job details
	row.append(currentJob.title)
	row.append(currentJob.org_summary)
	row.append(currentJob.org_detail)
	row.append(currentJob.duration)
	row.append(currentJob.location)
	row.append(currentJob.description)
	# Add past job details
	for i in range(pastJobsNum):
		row.append(pastJobs.title[i])
		row.append(pastJobs.org_summary[i])
		row.append(pastJobs.org_detail[i])
		row.append(pastJobs.duration[i])
		row.append(pastJobs.location[i])
		row.append(pastJobs.description[i])
	# Add highest-level-education details
	row.append(educations.highestLevel_universityName)
	row.append(educations.highestLevel_degree)
	row.append(educations.highestLevel_major)
	row.append(educations.highestLevel_endDate)
	row.append(educations.highestLevel_detail)
	# Add other-levels-education details
	for i in range(educationNum):
		row.append(educations.otherLevel_universityName[i])
		row.append(educations.otherLevel_degree[i])
		row.append(educations.otherLevel_major[i])
		row.append(educations.otherLevel_endDate[i])
		row.append(educations.otherLevel_detail[i])
	# Add skills
	skill = Merge1(skills)
	row.append(skill)
	language = Merge2(languages,languages_proficiency)
	row.append(language)

	return row

def WriteToCSV(url):
	
	content = open(url,'r')
	try:
		soup = BeautifulSoup(content, "html.parser")
	except:
		pass

	global countFailed

	# Initialize the value
	name = ""
	id = os.path.splitext(os.path.basename(url))[0]
	
	currentJob = CurrentJob()
	pastJobs = PastJobs()
	educations = Educations()
	currentJob.clear()
	pastJobs.clear()
	educations.clear()

	skills = []
	connections = "0"
	languages = []
	languages_proficiency = []

	try:
		name = soup.find("span", {"id": "name"}).text.encode("utf8").strip().replace("$", "")
		print("name: "+name)
		
		# Get current job detail
		try:
			current_job = soup.find("div", {"class": "position first experience vevent vcard summary-current"})
		except:
			pass

		try:
			jobs_period = soup.find_all("p", {"class": "period"})[0]
		except:
			pass

		currentJob.title = Try(current_job.find("span", {"class": "title"}))
		currentJob.org_summary = Try(current_job.find("span", {"class": "org summary"}))
		currentJob.org_detail = Try(soup.find("p", {"class": "orgstats organization-details current-position"}))
		currentJob.duration = TryDuration(jobs_period.find("span", {"class": "duration"}))
		currentJob.location = Try(jobs_period.find("span", {"class": "location"}))
		currentJob.description = Try(soup.find("p", {"class": " description current-position"}))

		# Print current job
		# currentJob.printAll()

		# Get past jobs' details
		try:
			past_jobs = soup.find_all("div", {"class": "position experience vevent vcard summary-past"})
			for i in range(len(past_jobs)):
				past_job = past_jobs[i]

				pastJobs.title[i] = Try(past_job.find("span", {"class": "title"}))
				pastJobs.org_summary[i] = Try(past_job.find("span", {"class": "org summary"}))
				pastJobs.org_detail[i] = Try(soup.find_all("p", {"class": "orgstats organization-details past-position"})[i])
				pastJobs.duration[i] = TryDuration(soup.find_all("p", {"class": "period"})[i+1].find("span", {"class": "duration"}))
				pastJobs.location[i] = Try(soup.find_all("p", {"class": "period"})[i+1].find("span", {"class": "location"}))
				pastJobs.description[i] = Try(soup.find_all("p", {"class": " description past-position"})[i])
				# Print past jobs
				# pastJobs.printAll(i)
		except:
			pass

		# Get highest-level-education details
		try:
			
			highestLevel = soup.find("div", {"class": "position first education vevent vcard"})
			educations.highestLevel_universityName = Try(highestLevel.find("h3", {"class": "summary fn org"}))
			educations.highestLevel_degree = Try(highestLevel.find("span", {"class": "degree"}))
			educations.highestLevel_major = Try(highestLevel.find("span", {"class": "major"}))
			educations.highestLevel_endDate = Try(highestLevel.find("abbr", {"class": "dtend"}))
			educations.highestLevel_detail = Try(highestLevel.find("p", {"class": " desc details-education"}))+Try(highestLevel.find("p", {"class": "desc details-education"}))
			# Print highest level of education
			# educations.printHighest()
		except:
			pass

		# Get other-levels-education details
		try:
			otherLevels = soup.find_all("div", {"class": "position education vevent vcard"})
			for i in range(len(otherLevels)):
				item = otherLevels[i]
				educations.otherLevel_universityName[i] = Try(item.find("h3", {"class": "summary fn org"}))
				educations.otherLevel_degree[i] = Try(item.find("span", {"class": "degree"}))
				educations.otherLevel_major[i] = Try(item.find("span", {"class": "major"}))
				educations.otherLevel_endDate[i] = Try(item.find("abbr", {"class": "dtend"}))
				educations.otherLevel_detail[i] = Try(item.find("p", {"class": " desc details-education"}))+Try(item.find("p", {"class": "desc details-education"}))
				# print other levels of education
				# educations.printOthers(i)
		except:
			pass

		# Get skills
		try:
			allSkills = soup.find("div", {"id": "profile-skills"}).find_all("span", {"class": "jellybean"})
			for i in range(len(allSkills)):
				skills.append(Try(allSkills[i]))
		except:
			pass

		# Get number of connections
		try:
			connections = soup.find("dd", {"class": "overview-connections"}).find("strong").text
			# print connections
		except:
			pass

		# Get languages
		try:
			allLanguages = soup.find_all("li", {"class": "competency language"})
			for language in allLanguages:
				languages.append(Try(language.find("h3")))
				languages_proficiency.append(Try(language.find("span", {"class": "proficiency"})))
				# print(Try(language.find("span", {"class": "proficiency"})))
		except:
			pass

		# one row 
		row = GetRow(id, name, connections, currentJob, pastJobs, educations, skills, languages, languages_proficiency)

		# Write the content into csv file
		file.writerow(row)

	except:

		# If there is no name, file was orginal linkedin page
		# count +1
		countFailed = countFailed+1
		pass

# total count for html files
count = 0
# global variables
global countFailed, pastJobsNum, educationNum
countFailed = 0	
pastJobsNum = 6
educationNum = 3

file = csv.writer(open("data.csv", "w"))

# row name in csv file
rowName = []
rowName.append('id')
rowName.append('name')
rowName.append('connections')
rowName.append('title')
rowName.append('org_summary')
rowName.append('org_detail')
rowName.append('duration')
rowName.append('location')
rowName.append('description')
for i in range(pastJobsNum):
	rowName.append('past_job_title'+str(i+1))
	rowName.append('past_job_org_summary'+str(i+1))
	rowName.append('past_job_org_detail'+str(i+1))
	rowName.append('past_job_duration'+str(i+1))
	rowName.append('past_job_location'+str(i+1))
	rowName.append('past_job_description'+str(i+1))
rowName.append('highestLevel_universityName')
rowName.append('highestLevel_degree')
rowName.append('highestLevel_major')
rowName.append('highestLevel_endDate')
rowName.append('highestLevel_detail')
for i in range(educationNum):
	rowName.append('otherLevel_universityName'+str(i+1))
	rowName.append('otherLevel_degree'+str(i+1))
	rowName.append('otherLevel_major'+str(i+1))
	rowName.append('otherLevel_endDate'+str(i+1))
	rowName.append('otherLevel_detail'+str(i+1))
rowName.append('skills')
rowName.append('languages')
# Write headers to file
file.writerow(rowName)

directory = "/Users/apple/Desktop/COLLEGE/fyp/dataset/"

# allFiles = os.listdir(directory)
# print(allFiles[0])
# for filename in os.listdir(directory):
# 	# filename = os.path.join(directory, filename)
#     if filename.endswith(".html"):
#     	filename = os.path.join(directory, filename)
#     	count = count+1
#     	print("==="+str(count))
#     	print(countFailed)
#     	WriteToCSV(filename)

WriteToCSV("/Users/apple/Desktop/COLLEGE/fyp/dataset/0.html")

print("\n")
print("Total number of links: " + str(count))
print("The number of failed links: " + str(countFailed))
print("\n")





























