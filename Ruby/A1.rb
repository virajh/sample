def palindrome? (string)
string1 =string.downcase
string2 = String.new
string1.each_char{|c|
string2 << c if c =~ (%r/\w/i);}
string3 =string2.reverse
if string2.eql?string3
true
else
false
end
end

def count_words(string)
arr1,r1 = [],[];
arr1 = string.split;
result = Hash.new;
i,j=0,0;
arr2 = [];
arr1.each {|str|
arr2.push(str.downcase);}
until(arr2.empty?) do
str2,str1="","";
str1=arr2.pop;
str1.each_char{|c|;
str2<<c	if c=~(%r/\w/i);}
r1[i]=str2;
i=i+1;
end
until(r1.empty?) do
str3,str4="","";
str3=r1.last;
j=r1.count(str3);
str4=r1.pop;
result.store(str4,j) unless result.has_key?(str4);
end
result;
end

p "Hi. Welcome to Palindrome."
p 'Checking Jack is palindrome?'
x=palindrome? 'jack'
puts x

p 'Checking Jaj is palindrome?'
x=palindrome? 'Jaj'
puts x

p 'Checking FaF is palindrome?'
x=palindrome? 'FAF'
puts x