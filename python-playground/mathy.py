import math

print(math.floor(2.5))
print(math.floor(-2.5))
print(math.trunc(2.5))
print(math.trunc(-2.5))

print( 5 / 2, 5/-2)
print(5 // 2, 5 //-2)
print( 5 // 2, 5 //-2)

print( 5 / 2.0, 5 / -2.0)
print( 5 // 2.0, 5 //-2.0)

print( 5 // 2, 5 // -2)
print( 5 / 2.0, 5 / -2.0)
print( 5 // 2.0, 5 // -2.0)

print( 5 / 2, 5 / -2)
print( 5 // 2, 5 // -2)
print( 5 / 2.0, 5 / -2.0)
print( 5 // 2.0, 5 // -2.0)

print( math.trunc( 5 / -2))
print( math.trunc( 5 / float(-2)))

print((5 / 2), (5 / 2.0), (5 / −2.0), (5 / −2))

print(5 // 2), (5 // 2.0), (5 // −2.0), (5 // −2))    # 3.X floor division


print(0o1, 0o20, 0o377)           # Octal literals: base 8, digits 0-7 (3.X, 2.6+)

print(0x01, 0x10, 0xFF)  # Hex literals: base 16, digits 0-9/A-F (3.X, 2.X)
print(0b1, 0b10000, 0b11111111) # Binary literals: base 2, digits 0-1 (3.X, 2.6+)

print(0xFF, (15 * (16 ** 1)) + (15 * (16 ** 0)))  # How hex/binary map to decimal

print( 0x2F, (2  * (16 ** 1)) + (15 * (16 ** 0))) 

print(0xF, 0b1111, (1*(2**3) + 1*(2**2) + 1*(2**1) + 1*(2**0)))

print(oct(64), hex(64), bin(64))  # Numbers=>digit strings


