import csv

file = open('../../../wikirank-fr.tsv', 'r', encoding='utf-8')
read = csv.reader(file, delimiter="\t")
map = dict()
best20 = []
worse20 = []
for line in read:
    verif = True
    try:
        id = int(line[0])
        if id < 0 or map.get(id) is not None:
            verif = False
        score = float(line[2])
        if score < 0 or score > 100:
            verif = False
        popularite = int(line[3])
        if popularite < 0:
            verif = False
        interet = int(line[4])
        if interet < 0:
            verif = False
    except ValueError:
        verif = False
    if verif:
        map[int(line[0])] = line
        # Les 20 les plus populaires
        if len(best20) < 20:
            best20.append(line)
        elif int(best20[19][3]) < int(line[3]):
            best20.remove(best20[19])
            best20.append(line)
        best20 = sorted(best20, key=lambda a: int(a[3]), reverse=True)
        if len(worse20) < 20 :
            worse20.append(line)
        elif int(worse20[19][3]) > int(line[3]):
            worse20.remove(worse20[19])
            worse20.append(line)
        worse20 = sorted(worse20, key=lambda a: int(a[3]), reverse=False)
print('\nLes plus populaires :\n')
for i in range(0,19):
    print(best20[i])
print('\nLes moins populaires :\n')
for i in range(0,19):
    print(worse20[i])
print(len(map))
file.close()
