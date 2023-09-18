import sys
import tiktoken

text = sys.argv[0]

tiktoken_encoding = tiktoken.encoding_for_model("gpt-3.5-turbo")
encoded = tiktoken_encoding.encode(text)
token_count = len(encoded)
 
print(token_count)