n = int(input())
for i in range(n):
    tmp = input()
    tmp = tmp.upper()
    '''
    size = len(tmp)
    for j in range(size // 2):
        if tmp[j] != tmp[-1-j]:
            print("#%d NO" % (i+1))
            break
    else:
        print("#%d YES" % (i+1))
    '''
    if tmp == tmp[::-1]:
        print("#%d YES" % (i+1))
    else:
       print("#%d NO" % (i+1)) 

