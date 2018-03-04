from bs4 import BeautifulSoup
import os

def countPublication(s):
	# count = 0
	try: 
		publication = soup.find("li", {"class":"publication vevent vcard first"})
		if publication is not None:
			return True
	except:
		pass


# def countNumberOfJobExperiences(s):
# 	try:
def countAssociations(s):
	try:
		association = soup.find("li",{"class":"affiliation vcard"})
		if association is not None:
			return True
	except:
		pass
	
directory = "/Users/apple/Desktop/COLLEGE/fyp/dataset/Linkedin_data/"
allFiles = os.listdir(directory)
# print(allFiles[0])
count = 1
count_publication = 0
count_association = 0
for filename in os.listdir(directory):
	# filename = os.path.join(directory, filename)
    if filename.endswith(".html"):
    	print("===file" + str(count))
    	# print("1")
    	filename = os.path.join(directory, filename)
    	try:
    		soup = BeautifulSoup(open(filename),"html.parser")
    	except:
    		pass
    	# print(filename)
    	# try:
    	# 	# print("1")
    	# 	publication = soup.find("li", {"class":"publication vevent vcard first"})
    	# 	print(publication.text)
    	# 	count_publication += 1 
    	# except:
    	# 	pass
    	if countPublication(soup):
    		count_publication += 1
 
    	if countAssociations(soup):
    		count_association += 1

    	count += 1
 

print("Number of people who have publications: " + str(count_publication))
print("Number of people who have associations: " + str(count_association))
# print(count_publication)
    	# print(countFailed)
    	# WriteToCSV(filename)
