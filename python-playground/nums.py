print(123 + 222)
print(1.5 * 4)
'''
Python 3.X’s integer type automatically provides extra precision for large 
numbers like this when needed (in 2.X, a separate long integer type handles 
numbers too large for the normal integer type in similar ways).'''
print(2 ** 100)

len(str(2 ** 1000000)) #How does this get evaluated?
