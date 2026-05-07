check(e).
check(w).
check(s).
check(n).
checkList([]).
checkList([H|T]) :- check(H),checkList(T).

move(e,sec(p(0),Y),sec(0,Y)).
move(n,sec(X,p(0)),sec(X,0)).
move(w,sec(s(0),Y),sec(0,Y)).
move(s,sec(X,s(0)),sec(X,0)).

move(e,sec(p(p(X)),Y),sec(p(X),Y)).
move(n,sec(X,p(p(Y))),sec(X,p(Y))).
move(w,sec(s(s(X)),Y),sec(s(X),Y)).
move(s,sec(X,s(s(Y))),sec(X,s(Y))).

move(e,sec(s(X),Y),sec(s(s(X)),Y)).
move(n,sec(X,s(Y)),sec(X,s(s(Y)))).
move(w,sec(p(X),Y),sec(p(p(X)),Y)).
move(s,sec(X,p(Y)),sec(X,p(p(Y)))).

move(e,sec(0,Y),sec(s(0),Y)).
move(n,sec(X,0),sec(X,s(0))).
move(w,sec(0,Y),sec(p(0),Y)).
move(s,sec(X,0),sec(X,p(0))).



robots(S1,[H1|T1],S2,[H2|T2]):- checkList([H1|T1]),checkList([H2|T2]),move(H1,S1,K1),
								move(H2,S2,K2),
								K1==K2.
robots(S1,[H1|T1],S2,[H2|T2]):- move(H1,S1,K1),
								move(H2,S2,K2),
								robots(K1,T1,K2,T2).
robots(S1,[],S2,[H2|T2]):- move(H2,S2,K2),
						   K2==S1.
robots(S1,[],S2,[H2|T2]):- move(H2,S2,K2),
						   robots(S1,[],K2,T2).


robots(S1,[H1|T1],S2,[]):- move(H1,S1,K1),
						   K1==S2.
robots(S1,[H1|T1],S2,[]):- move(H1,S1,K1),
						   robots(K1,T1,S2,[]).
robots(S1,[H1|T1],S2,[H2|T2]):- move(H1,S1,K1),
								move(H2,S2,K2),
								K1==S2,
								K2==S1.