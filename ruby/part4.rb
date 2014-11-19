class Dessert

attr_accessor(:name,:calories);
def initialize(name, calories)

self.name= name;
self.calories= calories;
end

def healthy?
 self
i=0
i=self.calories
i<200?true:false
end

def delicious?

true
end
end


class JellyBean < Dessert

attr_accessor(:flavor);
def initialize(name, calories, flavor)
 
self.flavor= flavor;
super(name,calories);
end
def delicious?
  
str=''
str=flavor.downcase;
str.eql?("black licorice")?false:true
end
end
