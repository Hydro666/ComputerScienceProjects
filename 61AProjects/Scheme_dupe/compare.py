m = open('scheme_my_nch.py', 'r')
r = open('scheme_ry.py', 'r')
my = m.read()
ry = r.read()

my_en = {pair[0]:pair[1] for pair in enumerate(my)}
ry_en = {pair[0]:pair[1] for pair in enumerate(ry)}

def compare(dic1, dic2):
    lst = []
    for i in range(len(list(my))):
        if dic1[i] != dic2[i]:
            lst.append([dic1[i], dic2[i]])
    return lst