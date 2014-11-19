def wordCompare(w1, w2)
flag =true
raise StandardError unless w1.class==String && w2.class==String
s1=String.new
s2=String.new
s1=w1.downcase
s2=w2.downcase
if(s1.length!=s2.length)
flag=false;
end
s2.each_char {|c|
if(!s1.include?c)
flag=false;
end
}
flag;
end

def combine_anagrams(words)
raise StandardError unless words.class==Array
h1 = Hash.new;
h1.store(words[0],Array.new)
h1.fetch(words[0]).push(words[0])
words.each{|x|
h1.each_pair{|h,j|
if(wordCompare(x,h) && wordCompare(h,x))
h1.fetch(h).push(x)
else
h1.store(x,Array.new);
h1.fetch(x).push(x);
end
}
}
result= Array.new
h1.each_value{|x|
result.push(x);
}
result
end

