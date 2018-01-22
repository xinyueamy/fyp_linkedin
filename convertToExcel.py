import csv
import xlsxwriter
import sys
import os

# with open('test.csv',encoding='ISO-8859-1') as csvfile:
#     reader = csv.reader(csvfile)
#     rows = [row for row in reader]
#     for i in range(len(rows)):
#         print(i)
#         name = "user" + str(i) + '.xls'
#         workbook = xlsxwriter.Workbook(name)
#         worksheet = workbook.add_worksheet()
#         # file = csv.writer(open(name,"w"))
#         row = rows[i]
#         # print(row)
#         print(row)
#         worksheet.write(row)
#         workbook.close()           

directory = "/Users/apple/Desktop/"
# allFiles = os.listdir(directory)
# print(allFiles[0])
for file in os.listdir(directory):
    if file.endswith('.csv'):
    	tmp_f = file
    	file = file.replace(".csv",".xlsx")
    	wb = xlsxwriter.Workbook(file)
    	# print(tmp_f)
    	print(wb)
    	ws = wb.add_worksheet("WS1")    
    	with open(tmp_f, encoding='ISO-8859-1') as csvfile:
    		table = csv.reader(csvfile)
    		# print(table)
    		i = 0
    		for row in table:
    			ws.write_row(i, 0, row)
    			i += 1
    		wb.close()