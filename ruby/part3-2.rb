def combine_anagrams(words)
raise StandardError unless words.class==Array
result=words.group_by{|x| x.chars.sort}.values
result
end