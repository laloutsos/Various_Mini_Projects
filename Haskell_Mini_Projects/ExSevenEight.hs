generating :: (Int -> Double) -> Int -> (Double -> Double)
generating f 0 z = f(0)
generating f n z = z^n * (f n) + (generating f (n-1) z) 



mapi :: [u]->(u->Int->v)->[v]
mapi [] f = [] 
mapi (h:t) f = f h 1 : mapi t (\s i -> f s (i+1))





