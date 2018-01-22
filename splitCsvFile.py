import csv
with open('data_v2.csv',encoding='ISO-8859-1') as csvfile:
    reader = csv.reader(csvfile)
    rows = [row for row in reader]
    for i in range(len(rows)):
        print(i)
        name = "user" + str(i) + '.csv'
        file = csv.writer(open(name,"w"))
        row = rows[i]
        # print(row)
        file.writerow(row)
            
