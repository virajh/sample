class WrongNumberOfPlayersError < StandardError ; end

class NoSuchStrategyError < StandardError ; end



def rps_result(m1, m2)
  
# YOUR CODE HERE

end



def rps_game_winner(game)
  
raise WrongNumberOfPlayersError unless game.length == 2
r1,r2,r3=game[0],game[1],[]
s1,s2='',''
m1=['R','P','S']
s1,s2=r1[1],r2[1]
s1.upcase!
s2.upcase!
raise NoSuchStrategyError unless (m1.include?s1)
raise NoSuchStrategyError unless (m1.include?s2)
if s1 == s2
r3[0],r3[1]= r1[0],r1[1] 
elsif s1 == 'P' && s2 == 'R';
r3[0],r3[1]= r1[0],r1[1]
elsif s1 == 'P' && s2 == 'S';
r3[0],r3[1]= r2[0],r2[1]
elsif s1 == 'S' && s2 == 'R';
r3[0],r3[1]= r2[0],r2[1]
elsif s1 == 'S' && s2 == 'P';
r3[0],r3[1]= r1[0],r1[1]
elsif s1 == 'R' && s2 == 'S';
r3[0],r3[1]= r1[0],r1[1]
elsif s1 == 'R' && s2 == 'P';
r3[0],r3[1]= r2[0],r2[1]
end
r3
end



def rps_tournament_winner(tournament)
  
r1,r2,r3,r4,r5=[],[],[],[],[]
r1= tournament[0]
r2= tournament[1]
if r1[0].class == String
rps_game_winner([r1,r2])
elsif r1[0].class == Array && r1[1].class == Array
r3= rps_tournament_winner r1
r4= rps_tournament_winner r2
r5= rps_tournament_winner([r3,r4])
end
end
