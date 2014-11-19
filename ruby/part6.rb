class Numeric
@@currencies = {'dollar' => 1, 'yen' => 0.013, 'euro' => 1.292, 'rupee' => 0.019}

def in (cur)
cur1 = cur.to_s.gsub(/s$/,'')
if @@currencies.has_key?(cur1)
 self / @@currencies[cur1]
else
self
end
end

 def method_missing(method_id)
  singular_currency = method_id.to_s.gsub( /s$/,'')
  if @@currencies.has_key?(singular_currency)
     self * @@currencies[singular_currency]
  else
     super
  end
 end
end

class String
def palindrome?
string1 =self.downcase
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
end


#Unsuccessful part below-------
module Enumerable
def palindrome?

end
end