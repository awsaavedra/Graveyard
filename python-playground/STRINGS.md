>>> S = 'Spam'           # Make a 4-character string, and assign it to a name
>>> len(S)               # Length
4
>>> S[0]                 # The first item in S, indexing by zero-based position
'S'
>>> S[1]                 # The second item from the left
'p'

>>> S[-1]                # The last item from the end in S
'm'
>>> S[-2]                # The second-to-last item from the end
'a'

>>> S[-1]                # The last item in S
'm'
>>> S[len(S)-1]          # Negative indexing, the hard way
'm'

>>> S
'Spam'
>>> S + 'xyz'             # Concatenation
'Spamxyz'
>>> S                     # S is unchanged
'Spam'
>>> S * 8                 # Repetition
'SpamSpamSpamSpamSpamSpamSpamSpam'

>>> S
'Spam'

>>> S[0] = 'z'             # Immutable objects cannot be changed
...error text omitted...
TypeError: 'str' object does not support item assignment

>>> S = 'z' + S[1:]        # But we can run expressions to make new objects
>>> S
'zpam'

>>> S = 'shrubbery'
>>> L = list(S)                                     # Expand to a list: [...]
>>> L
['s', 'h', 'r', 'u', 'b', 'b', 'e', 'r', 'y']
>>> L[1] = 'c'                                      # Change it in place
>>> ''.join(L)                                      # Join with empty delimiter
'scrubbery'

>>> B = bytearray(b'spam')                          # A bytes/list hybrid (ahead)
>>> B.extend(b'eggs')                               # 'b' needed in 3.X, not 2.X
>>> B                                               # B[i] = ord(x) works here too
bytearray(b'spameggs')
>>> B.decode()                                      # Translate to normal string
'spameggs'

>>> S = 'Spam'
>>> S.find('pa')                 # Find the offset of a substring in S
1
>>> S
'Spam'
>>> S.replace('pa', 'XYZ')       # Replace occurrences of a string in S with another
'SXYZm'
>>> S
'Spam'

>>> line = 'aaa,bbb,ccccc,dd'
>>> line.split(',')              # Split on a delimiter into a list of substrings
['aaa', 'bbb', 'ccccc', 'dd']

>>> S = 'spam'
>>> S.upper()                    # Upper- and lowercase conversions
'SPAM'
>>> S.isalpha()                  # Content tests: isalpha, isdigit, etc.
True

>>> line = 'aaa,bbb,ccccc,dd\n'
>>> line.rstrip()                # Remove whitespace characters on the right side
'aaa,bbb,ccccc,dd'
>>> line.rstrip().split(',')     # Combine two operations
['aaa', 'bbb', 'ccccc', 'dd']

>>> '%s, eggs, and %s' % ('spam', 'SPAM!')          # Formatting expression (all)
'spam, eggs, and SPAM!'

>>> '{0}, eggs, and {1}'.format('spam', 'SPAM!')    # Formatting method (2.6+, 3.0+)
'spam, eggs, and SPAM!'

>>> '{}, eggs, and {}'.format('spam', 'SPAM!')      # Numbers optional (2.7+, 3.1+)
'spam, eggs, and SPAM!'

>>> '{:,.2f}'.format(296999.2567)                   # Separators, decimal digits
'296,999.26'
>>> '%.2f | %+05d' % (3.14159, −42)                 # Digits, padding, signs
'3.14 | −0042'
