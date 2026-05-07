area :: (Double,Double)->(Double,Double)->(Double,Double)->Double

area (x1,y1) (x2,y2) (x3,y3) = sqrt ( ((a+b+c)/2)*(((a+b+c)/2)-a)*(((a+b+c)/2)-b)*(((a+b+c)/2)-c))
	where		
          a = sqrt( (x1-x2)^2 +(y1-y2)^2) 
          b = sqrt( (x1-x3)^2 +(y1-y3)^2)
          c = sqrt( (x3-x2)^2 +(y3-y2)^2)
		  
parking :: (Int,Int)->(Int,Int)->Int            
  
parking (h1,m1) (h2,m2)
    |k<=3 && s<=0 = 8
	|k==3 && s>0 = 10
	|k<=6 && s<=0 = 8+(2*(k-3))
	|k<=6 && s>0 = 8+(2*(k-3)+1)
	|k>6 && s>0 = 9+k
	|otherwise = 8+k
	        where 
			      k = h2-h1
			      s = m2-m1