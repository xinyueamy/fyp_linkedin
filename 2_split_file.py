import csv
# with open('/Users/apple/Desktop/COLLEGE/fyp/data/data_no_latest.csv',encoding='ISO-8859-1') as csvfile:
with open('/Users/apple/Desktop/COLLEGE/fyp/data/data_v4.csv',encoding='ISO-8859-1') as csvfile:
    reader = csv.reader(csvfile)    
    reader = csv.reader(csvfile)
    rows = [row for row in reader]
    for i in range(len(rows)):
        print(i)
        name = "/Users/apple/Desktop/COLLEGE/fyp/data/data_csv/" + str(i) + '.csv'
        file = csv.writer(open(name,"w"))
        row = rows[i]
        # print(row)
        file.writerow(row)
            
