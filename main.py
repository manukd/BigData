import csv

file = open('wikirank-fr.tsv', 'r', encoding='utf-8')
read = csv.reader(file, delimiter="\t")
map = dict()
for line in read:
    verif = 1
    try:
        id = int(line[0])
        if id < 0 or map.get(id) is not None:
            verif = 0
        score = float(line[2])
        if score < 0 or score > 100:
            verif = 0
        popularite = int(line[3])
        if popularite < 0:
            verif = 0
        interet = int(line[4])
        if interet < 0:
            verif = 0
    except ValueError:
        verif = 0
    if(verif):
        map[line[0]] = line
print(len(map))
file.close()