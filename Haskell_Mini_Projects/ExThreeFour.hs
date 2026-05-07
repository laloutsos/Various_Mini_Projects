

join :: Integer->Integer->Integer

join a b 
        |a<0 || b<0 = join (abs(a)) (abs(b))
        |a>0 || b>0  = 10*join (a `div` 10) (b `div` 10) + (13 * (ai + 5) + 19 * (bi + 3)) `mod` 10 
		|otherwise = 0
		  where 
                ai = a `mod` 10
                bi = b `mod` 10
              
			  
 
seekGCD :: Int -> Int -> Int -> Int -> Int
seekGCD m n y s
    |y==0 = 0
    |s/=1 && m `mod` y == 0 && n `mod` y == 0 =  seekGCD m n (y-1) (s-1)
	|s==1 && m `mod` y == 0 && n `mod` y == 0 = y
    |otherwise = seekGCD m n (y-1) s


kgcd :: Int->Int->Int->Int
kgcd m n k 
	
    |m<=n = seekGCD m n m k
	|otherwise = seekGCD n m n k




 