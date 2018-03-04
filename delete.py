import csv

fw = csv.writer(open('data_v3_new.csv','w'))

file = csv.reader(open('data_v3.csv',encoding='ISO-8859-1'))

i = 0
for row in file:
	if row[70] != '' or row[4] != '':
		# print(row)
		fw.writerow(row)
		i += 1
		print("===" + str(i))