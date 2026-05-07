minIndex :: [Int] -> Int -> Int -> Int -> Int ->Int
minIndex (h:[]) dist idist i n = if abs(h-n)<dist then i else idist 
minIndex (h:t) dist idist i n
    |abs(h-n)<dist = minIndex (t) (abs(h-n)) i (i+1) n 
	|abs(h-n)>=dist = minIndex (t) dist idist (i+1) n 
	|otherwise = 0
	
nearest :: [Int]->Int->Int

nearest s n
    |length(s)==1 = 1
    |otherwise = minIndex (s) (44444) (1) (1) (n) 
	
	











