s = input()
sum = 0
for x in s:
    if x.isdecimal():
        sum = sum * 10 + int(x)
print(sum)