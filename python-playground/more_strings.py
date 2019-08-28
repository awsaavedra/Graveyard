S = 'A\nB\tC'
len(S)

ord('\n')

S = 'A\OB\OC'
len(S)
print(S)

msg = """
aaaaaaaaaaaaa
bbb'''bbbbbbbbbb""bbbbbbb'bbbb
cccccccccccccc
"""

print(msg)

W = 'sp\xc4m'                     # 3.X: normal str strings are Unicode text
# 'spÄm'
X = b'a\x01c'                     # bytes strings are byte-based data
# b'a\x01c'
Z = u'sp\u00c4m'                  # The 2.X Unicode literal works in 3.3+: just str
# 'spÄm'

Zeta = '\u00A3', '\u00A3'.encode('latin1'), b'\xA3'.decode('latin1')
# ('£', b'\xa3', '£')


# In python REPL
>>> import re
>>> match = re.match('Hello[ \t]*(.*)world', 'Hello    Python world')
>>> match.group(1)
'Python '

>>> match = re.match('[/:](.*)[/:](.*)[/:](.*)', '/usr/home:lumberjack')
>>> match.groups()
('usr', 'home', 'lumberjack')

>>> re.split('[/:]', '/usr/home/lumberjack')
['', 'usr', 'home', 'lumberjack']
